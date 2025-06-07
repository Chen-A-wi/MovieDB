import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.appplugin")
    id("com.awilab.composeplugin")
    id("com.awilab.ktlintplugin")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.awilab.moviedb"

    defaultConfig {
        applicationId = "com.awilab.moviedb"
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}
