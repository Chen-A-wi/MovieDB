import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.baselibplugin")
}

android {
    namespace = "com.awilab.network"

    kotlinOptions {
        jvmTarget = Version.jdk.toString()
    }
}

dependencies {
    implementation(libs.retrofit2)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
}