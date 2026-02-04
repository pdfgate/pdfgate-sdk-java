plugins {
    `java-library`
    id("com.vanniktech.maven.publish") version "0.36.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.apache.pdfbox:pdfbox:2.0.30")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    withJavadocJar()
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(11)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.matching { it.name == "plainJavadocJar" }.configureEach {
    enabled = false
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates("com.pdfgate", "pdfgate", "0.1.0-SNAPSHOT")

    pom {
        name.set("PDFGate SDK")
        description.set("Java SDK for the PDFGate HTTP API.")
        inceptionYear.set("2026")
        url.set("https://github.com/pdfgate/pdfgate-sdk-java/")
        licenses {
            license {
                name.set("MIT License")
                url.set("http://www.opensource.org/licenses/mit-license.php")
                distribution.set("http://www.opensource.org/licenses/mit-license.php")
            }
        }
        developers {
            developer {
                id.set("pdfgate")
                name.set("PDFGate")
                url.set("https://github.com/pdfgate/")
            }
        }
        scm {
            url.set("https://github.com/pdfgate/pdfgate-sdk-java/")
            connection.set("scm:git:git://github.com/pdfgate/pdfgate-sdk-java.git")
            developerConnection.set("scm:git:ssh://git@github.com/pdfgate/pdfgate-sdk-java.git")
        }
    }
}
