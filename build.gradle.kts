import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    alias(libs.plugins.spotless)
    alias(libs.plugins.maven.publish)
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

mavenPublishing {
    configure(JavaLibrary(
        javadocJar = JavadocJar.Javadoc(),
        sourcesJar = true,
    ))
    signAllPublications()
    if (!version.toString().endsWith("-SNAPSHOT")) {
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    }

    pom {
        description.set(project.description ?: throw IllegalStateException("Add a project description"))
        url.set("https://github.com/Sparky983/state/")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://www.opensource.org/licenses/mit-license")
            }
        }
        developers {
            developer {
                id.set("Sparky983")
            }
        }
        scm {
            url.set("https://github.com/Sparky983/state/")
            connection.set("scm:git:git://github.com/Sparky983/state.git")
            developerConnection.set("scm:git:ssh://git@github.com/Sparky983/state.git")
        }
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
