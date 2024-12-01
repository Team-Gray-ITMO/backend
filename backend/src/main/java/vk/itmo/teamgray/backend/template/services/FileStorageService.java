package vk.itmo.teamgray.backend.template.services;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import vk.itmo.teamgray.backend.template.dto.FileDto;
import vk.itmo.teamgray.backend.template.exception.FileStorageServiceException;

@Slf4j
@Service
public class FileStorageService {
    private final S3Client s3Client;
    private final String bucketName;

    public FileStorageService(
        @Value("${s3.endpoint}")
        String endpoint,

        @Value("${s3.region}")
        String region,

        @Value("${s3.accessKey}")
        String accessKey,

        @Value("${s3.secretKey}")
        String secretKey,

        @Value("${s3.bucketName}")
        String bucketName
    ) {
        this.s3Client = S3Client.builder()
            .endpointOverride(URI.create(endpoint))
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
                ))
            .build();

        this.bucketName = bucketName;
    }


    @PostConstruct
    public void initializeBucket() {
        try {
            boolean success;

            try {
                success = s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build()).sdkHttpResponse().isSuccessful();
            } catch (NoSuchBucketException e) {
                success = false;
            }

            if (success) {
                log.info("Bucket '{}' already exists.", bucketName);
            } else {
                var response = s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());

                if (response.sdkHttpResponse().isSuccessful()) {
                    log.info("Bucket '{}' created successfully.", bucketName);
                } else {
                    throw new RuntimeException("Could not create bucket. Response: " + response.sdkHttpResponse());
                }
            }
        } catch (S3Exception e) {
            log.info("Error while checking/creating the bucket: {}", e.getMessage());

            throw new FileStorageServiceException("Error while checking/creating the bucket", e);
        }
    }

    public String uploadFile(FileDto file) {
        String fileName = UUID.randomUUID() + "-" + file.getFilename();

        try {
            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build(),
                RequestBody.fromInputStream(new ByteArrayInputStream(file.getContent()), file.getContent().length)
            );
        } catch (Exception e) {
            throw new FileStorageServiceException("Could not upload file", e);
        }

        return fileName;
    }

    public FileDto getFile(String filePath) {
        var head = getHead(filePath);

        if (head == null) {
            throw new FileStorageServiceException("File does not exist");
        }

        FileDto fileDto = new FileDto();

        try {
            var inputStream = s3Client.getObject(
                GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build()
            );

            fileDto.setFilename(filePath);
            fileDto.setContent(inputStream.readAllBytes());
            fileDto.setContentType(head.contentType());

            return fileDto;
        } catch (Exception e) {
            throw new FileStorageServiceException("Could not get file", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(
                DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build()
            );
        } catch (Exception e) {
            throw new FileStorageServiceException("Could not delete file", e);
        }
    }

    public boolean fileExists(String filePath) {
        try {
            return getHead(filePath) != null;
        } catch (Exception e) {
            throw new FileStorageServiceException("Could not check if file exists", e);
        }
    }

    private HeadObjectResponse getHead(String filePath) {
        return s3Client.headObject(
            HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build()
        );
    }
}
