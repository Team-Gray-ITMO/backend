plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "vk.itmo.teamgray"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

openApi {
    outputDir.set(file("$rootDir/swagger"))
    apiDocsUrl.set("http://localhost:9090/v3/api-docs.yaml")
    outputFileName.set("swagger.yml")
    waitTimeInSeconds.set(15)

    //Launching on test environment.
    customBootRun {
        systemProperties.put("server.port", "9090")
        systemProperties.put("spring.datasource.url", "jdbc:postgresql://localhost:5433/resumebuilder")
        systemProperties.put("s3.endpoint", "http://127.0.0.1:9001/")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("software.amazon.awssdk:s3:2.25.27")
    implementation("org.apache.velocity:velocity-engine-core:2.4.1")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    implementation("com.itextpdf:html2pdf:6.0.0")

    implementation("org.apache.poi:poi-ooxml:5.3.0")
    implementation("org.jsoup:jsoup:1.18.3")

    implementation("org.xhtmlrenderer:flying-saucer-core:9.11.2")
    implementation("org.xhtmlrenderer:flying-saucer-pdf-itext5:9.7.2")

    implementation("io.swagger.core.v3:swagger-annotations:2.2.26")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // Lombok should always be before mapstruct, otherwise the latter won't see accessors generated by it
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
