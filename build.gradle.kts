plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.diffplug.spotless") version "6.12.1"
}

group = "com.homework"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("com.h2database:h2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperties["spring.profiles.active"] = "test"
}

val testAll by tasks.registering {
    group = "verification"
    description = "Runs all tests"
    dependsOn(":spotlessCheck", ":test")
    tasks["test"].mustRunAfter(tasks["spotlessCheck"])
}


spotless {
    java {
        targetExclude("build/**/*.java")
        replaceRegex("Remove Wildcard Import", "import\\s+[^\\*\\s]+\\*;(\\r\\n|\\r|\\n)", "$1")
        // import 순서 정의
        importOrder(
            "java",
            "javax",
            "lombok",
            "org.springframework",
            "",
            "\\#",
            "org.junit",
            "\\#org.junit",
            "",
            "com.homework",
            "\\#com.homework"
        )
        // 사용하지 않는 import 제거
        removeUnusedImports()
        // 구글 자바 포맷 적용
        googleJavaFormat()

        indentWithTabs(2)
        indentWithSpaces(4)
        // 공백 제거
        trimTrailingWhitespace()
        // 끝부분 New Line 처리
        endWithNewline()
    }
}

