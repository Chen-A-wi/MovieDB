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
        create("KtlintPlugin") {
            id = "com.awilab.ktlintplugin"
            implementationClass = "com.awilab.baseplugin.quality.KtlintPlugin"
        }
    }
}

dependencies {
    implementation(libs.gradle.tools.build)
    implementation(libs.kotlin.gradle.plugin)
    implementation(files((libs as Any).javaClass.superclass.protectionDomain.codeSource.location))
}
