import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.gradleIntelliJPlugin) // Gradle IntelliJ Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.17.2")
}

kotlin {
    @Suppress("UnstableApiUsage")
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(17)
        vendor = JvmVendorSpec.JETBRAINS
    }
}

intellij {
    pluginName = properties("pluginName")
    type = properties("platformType")
    version = properties("platformVersion")
    plugins = properties("platformPlugins").map { it.split(',').map(String::trim).filter(String::isNotEmpty) }
    updateSinceUntilBuild = false
}

changelog {
    version = properties("pluginVersion")
    path = file("CHANGELOG.md").canonicalPath
    header = "${properties("pluginVersion").get()}/${date()}"
    keepUnreleasedSection = true
    groups = mutableListOf("Changed")
    itemPrefix = "#"
}

tasks {
    properties("javaVersion").get().let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = it
        }
    }
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }

    patchPluginXml {
        version = properties("pluginVersion")
        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }
        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = properties("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                        (getOrNull(pluginVersion) ?: getUnreleased())
                                .withHeader(false)
                                .withEmptySections(false),
                        Changelog.OutputType.HTML,
                )
            }
        }
    }

    signPlugin {
        certificateChainFile = project.file("profiles/chain.crt")
        privateKeyFile = project.file("profiles/private.pem")
        password = environment("PRIVATE_KEY_PASSWORD")
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token = environment("PUBLISH_TOKEN")
        channels = properties("pluginVersion").map { listOf(it.split('-').getOrElse(1) { "default" }.split('.').first()) }
    }

    runIde {
        jbrVersion = "11_0_10b1145.115"
        ideDir = file(properties("idePath"))
    }
}
