import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    id("org.jetbrains.intellij.platform") version "2.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
}

group = "com.kebastos.kulibin"
version = project.findProperty("version")?.toString() ?: "0.0.0"

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
        gradlePluginPortal()
    }
}

dependencies {
    intellijPlatform {
        // local("C:\\Program Files\\JetBrains\\JetBrains Rider 2024.2.5")
        rider("2024.2.5")
        instrumentationTools()
    }
}

tasks {
    compileJava {
        options.release.set(17)
    }

    compileKotlin {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }

    compileTestJava {
        options.release.set(17)
    }

    compileTestKotlin {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }

    patchPluginXml {
        version = project.version.toString()
        sinceBuild.set("223")
        untilBuild.set("243.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
