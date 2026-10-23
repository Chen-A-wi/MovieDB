plugins {
    id("com.awilab.baselibplugin")
}

android {
    namespace = "com.awilab.data"
}

dependencies {
    implementation(project(":network"))
    implementation(project(":domain"))
    implementation(project(":common"))

    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.retrofit2)
    testImplementation(libs.kotlinx.serialization.json)
}