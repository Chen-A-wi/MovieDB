plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("AppPlugin") {
            id = "com.awilab.appplugin"
            implementationClass = "com.awilab.baseplugin.common.AppPlugin"
        }
        create("ComposePlugin") {
            id = "com.awilab.composeplugin"
            implementationClass = "com.awilab.baseplugin.common.ComposePlugin"
        }
    }
}

dependencies {
    implementation(libs.gradle.tools.build)
    implementation(libs.kotlin.gradle.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
