package vk.itmo.teamgray.backend.file.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtils {
    private static final MessageDigest MESSAGE_DIGEST = getMessageDigest();

    private HashUtils() {
        // No-op.
    }

    public static String hash(byte[] input) {
        return Base64.getEncoder().encodeToString(MESSAGE_DIGEST.digest(input));
    }

    private static MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
