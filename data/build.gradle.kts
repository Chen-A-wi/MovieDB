import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.baselibplugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.awilab.data"

    kotlinOptions {
        jvmTarget = Version.jdk.toString()
    }
}

dependencies {
    implementation(project(":network"))
    implementation(project(":domain"))
    implementation(project(":common"))
}