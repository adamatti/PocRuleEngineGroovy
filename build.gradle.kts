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

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.sparkjava:spark-kotlin:1.0.0-alpha")
    implementation("com.sparkjava:spark-template-handlebars:2.7.1")

    //implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.beust:klaxon:5.0.1")
    implementation("org.codehaus.groovy:groovy-templates:3.0.4")

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