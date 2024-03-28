plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "pw.mintea"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.skriptlang.org/releases")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    implementation("commons-io:commons-io:2.6")
    implementation("fr.mrmicky:fastboard:2.1.0")
    implementation("com.github.SkriptLang:Skript:2.8.3") { isTransitive = false }
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    shadowJar {
        relocate("fr.mrmicky.fastboard", "pw.mintea.solboard.fastboard")
    }
}

tasks.processResources {
    expand("version" to project.version)
}

tasks.register<Jar>("uberJar") {

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier = "uber"

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter {
            it.name.endsWith("jar")
                    && !it.name.contains("Skript")
        }.map {
            zipTree(it)
        }
    })
}

kotlin {
    jvmToolchain(17)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}