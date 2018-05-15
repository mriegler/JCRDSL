import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra
buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.41"
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))
    }
}
plugins {
    application
    kotlin("jvm") version "1.2.41"
}
apply {
    plugin("kotlin")
}

application {
    mainClassName = "de.jcrcli.MainKt"
}

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("stdlib-jdk8", kotlin_version))
    compile("com.authzee.kotlinguice4:kotlin-guice:1.0.0")
    compile("io.github.microutils:kotlin-logging:1.5.4")
    compile("ch.qos.logback:logback-classic:1.2.3")
}

repositories {
    jcenter()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
