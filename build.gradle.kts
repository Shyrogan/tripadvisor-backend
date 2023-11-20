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
        val compileOnly by configurations
        val annotationProcessor by configurations

        compileOnly(rootProject.libs.lombok)

        implementation(rootProject.libs.slf4j)
        implementation(rootProject.libs.javalin.core)

        implementation(rootProject.libs.javalin.oapi.plugin)
        implementation(rootProject.libs.javalin.oapi.swagger)
        annotationProcessor(rootProject.libs.javalin.oapi.annotation.processor)
    }
}