import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.baselibplugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.awilab.network"

    kotlinOptions {
        jvmTarget = Version.jdk.toString()
    }
}

dependencies {
    implementation(project(":common"))
    implementation(libs.retrofit2)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.retrofit)
}