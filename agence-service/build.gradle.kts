plugins {
    application
}

dependencies {
    implementation(project(":common"))
    implementation(libs.picocli)
}

application {
    mainClass = "fr.samyseb.hotel.Application"
}