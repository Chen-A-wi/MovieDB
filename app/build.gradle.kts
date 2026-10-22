import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.appplugin")
    id("com.awilab.composeplugin")
    id("com.awilab.ktlintplugin")
    id("kotlin-parcelize")
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

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    implementation(libs.lottie.compose)

    implementation(libs.androidx.icons)
}
