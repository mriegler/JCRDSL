import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.dokka.DokkaConfiguration
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
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.1.0")
    }
}
plugins {
    kotlin("jvm") version "1.2.41"
    id("idea")
    id("org.jetbrains.dokka") version "0.9.17"
}
apply {
    plugin("kotlin")
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlin_version))
    compile("com.authzee.kotlinguice4:kotlin-guice:1.0.0")
    compile("io.github.microutils:kotlin-logging:1.5.4")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("org.codehaus.groovy:groovy-all:2.4.13")

    compile("javax.jcr:jcr:2.0")
    compile("org.apache.jackrabbit:jackrabbit-core:2.17.2")

    testCompile("io.kotlintest:kotlintest-core:3.1.0")
    testCompile("io.kotlintest:kotlintest-assertions:3.1.0")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.0")
}



tasks {
    // Use the native JUnit support of Gradle.
    "test"(Test::class) {
        useJUnitPlatform()
        //testLogging.showStandardStreams = true
        testLogging {
            events("passed", "skipped", "failed", "standardOut", "standardError")
        }
    }
    task("wrapper", Wrapper::class) {
        gradleVersion = "4.6"
    }
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

configure<IdeaModel> {
    module {
        isDownloadSources = true
    }
}
