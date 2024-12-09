# Resume Builder Backend

### Building project
```
./gradlew clean build -x test
```

### Running in Docker
```
cd docker/resume-builder-backend

docker-compose build

docker-compose up -d
```

### Running Tests

1. Launch DB and Minio in Docker
```
cd docker/resume-builder-backend   

docker-compose -f docker-compose-test.yml up -d 
```

2. Build project:
```
./gradlew clean build -x test
```

3. Launch tests in Gradle or with IntelliJ IDE.A
```
./gradlew test
```

(by Team Gray)

