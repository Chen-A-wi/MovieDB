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

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    implementation(libs.androidx.icons)
}
