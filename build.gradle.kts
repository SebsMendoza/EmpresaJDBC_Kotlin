import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "es.mendoza"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Base de Datos H2 Driver JDBC
    implementation("com.h2database:h2:2.1.214")
    // Para hacer el logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.2")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    // https://mvnrepository.com/artifact/org.mybatis/mybatis
    implementation("org.mybatis:mybatis:3.5.9")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}