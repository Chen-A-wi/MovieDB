plugins {
    id("com.awilab.baselibplugin")
}

android {
    namespace = "com.awilab.domain"
}

dependencies {
    implementation(project(":common"))
}