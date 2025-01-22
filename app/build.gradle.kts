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
