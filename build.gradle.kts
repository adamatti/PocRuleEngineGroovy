import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    application
}

application {
    mainClassName = "adamatti.Main"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    testImplementation("junit:junit:4.13-rc-2")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.amshove.kluent:kluent:1.40")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnit()
}

tasks.withType<Jar> {
    manifest {
        attributes(mapOf("Main-Class" to application.mainClassName))
    }
}