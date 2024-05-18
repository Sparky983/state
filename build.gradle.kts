plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.jspecify)
    testImplementation(libs.junit.jupiter)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }
    test {
        useJUnitPlatform()
    }
}
