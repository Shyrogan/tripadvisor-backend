allprojects {
    group   = "fr.samyseb"
    version = "0.1.0"
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        val implementation by configurations

        implementation(rootProject.libs.slf4j)
        implementation(rootProject.libs.javalin.core)
    }
}