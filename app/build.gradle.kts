import com.awilab.baseplugin.configs.Version

plugins {
    id("com.awilab.appplugin")
    alias(libs.plugins.compose.compiler)
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

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}