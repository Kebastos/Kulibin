import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    id("org.jetbrains.intellij.platform") version "2.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

detekt {
    toolVersion = "1.23.7"
    config.setFrom("$projectDir/config/detekt.yml")
    buildUponDefaultConfig = true
}

group = "com.kebastos.kulibin"
version = "251.0.1-rc1"

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
        gradlePluginPortal()
    }
}

dependencies {
    intellijPlatform {
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
        sinceBuild.set("232")
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
