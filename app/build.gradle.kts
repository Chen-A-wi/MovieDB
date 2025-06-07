import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.appplugin")
    id("com.awilab.composeplugin")
    id("com.awilab.ktlintplugin")
}

android {
    namespace = "com.awilab.moviedb"

    defaultConfig {
        applicationId = "com.awilab.moviedb"
    }

    kotlinOptions {
        jvmTarget = Version.jdk.toString()
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":network"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.bundles.media3)
}
