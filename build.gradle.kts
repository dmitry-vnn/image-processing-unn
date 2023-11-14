plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "dmitry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jfree:jfreechart:1.5.0")
    implementation(files("C:\\opencv\\build\\java\\opencv-454.jar"))

    //implementation("org.bytedeco:javacv-platform:1.5.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}