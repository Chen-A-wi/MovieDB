plugins {
    id("com.awilab.baselibplugin")
}

android {
    namespace = "com.awilab.common"
}

dependencies {
    implementation(libs.truth)
}