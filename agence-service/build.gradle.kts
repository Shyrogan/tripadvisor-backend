plugins {
    application
}

dependencies {
    implementation(project(":common"))

    implementation(libs.javalin.oapi.plugin)
    implementation(libs.javalin.oapi.swagger)
    annotationProcessor(libs.javalin.oapi.annotation.processor)
}

application {
    mainClass = "fr.samyseb.hotel.Application"
}