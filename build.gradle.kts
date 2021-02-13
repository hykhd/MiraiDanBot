plugins {
    val kotlinVersion = "1.4.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.3.2" // mirai-console version
}

mirai {
    coreVersion = "2.3.2" // mirai-core version
}

group = "com.hykhd.mirai"
version = "0.1.0"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}
dependencies {
    runtimeOnly("net.mamoe:mirai-login-solver-selenium:1.0-dev-16")
}