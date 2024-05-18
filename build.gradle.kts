plugins {
    `java-library`
    alias(libs.plugins.spotless)
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

spotless {
    java {
        palantirJavaFormat().style("GOOGLE")
        formatAnnotations()
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
