plugins {
    val kotlinVersion: String by System.getProperties()

    kotlin("jvm") version kotlinVersion
    id("fabric-loom") version "0.10.+"
    `java-library`
}

group = "dev.isxander"
version = "1.0.1"

repositories {
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

fun DependencyHandlerScope.includeImplementation(dependency: Any) {
    include(dependency)
    implementation(dependency)
}

fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)
    api(dependency)
}

dependencies {
    val kotlinVersion: String by System.getProperties()
    val minecraftVersion: String by project
    val yarnVersion: String by project
    val loaderVersion: String by project
    val fabricVersion: String by project
    val fabricKotlinVersion: String by project

    implementation(kotlin("stdlib-jdk8", kotlinVersion))

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnVersion:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion+kotlin.$kotlinVersion")

    includeImplementation("com.labymedia:ultralight-java-base:0.4.6")
    includeImplementation("com.labymedia:ultralight-java-databind:0.4.6")
    includeImplementation("com.labymedia:ultralight-java-gpu:0.4.6")

    includeApi("io.ktor:ktor-client-core:2.0-eap-289")
    includeApi("io.ktor:ktor-client-apache:2.0.1-eap-385")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.version
                )
            )
        }
    }
}
