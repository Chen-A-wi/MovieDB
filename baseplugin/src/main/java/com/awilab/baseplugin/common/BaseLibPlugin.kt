package com.awilab.baseplugin.common

import com.android.build.gradle.LibraryExtension
import com.awilab.baseplugin.extension.configureAndroid
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.the
import java.util.Properties
import kotlin.properties.Delegates

class BaseLibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = the<LibrariesForLibs>()

            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
                apply(libs.plugins.hilt.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                flavorDimensions += listOf("default")

                configureAndroid()

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                    }

                    debug {

                    }
                }

                productFlavors {
                    var apiKey by Delegates.notNull<String>()
                    var apiToken by Delegates.notNull<String>()

                    Properties().apply {
                        load(project.rootProject.file("local.properties").inputStream())
                        apiKey = getProperty("API_KEY")
                        apiToken = getProperty("API_TOKEN")
                    }

                    create("dev") {
                        resValue("string", "app_name", "(DEV)MovieDB")
                        buildConfigField("String", "API_KEY", apiKey)
                        buildConfigField("String", "API_TOKEN", apiToken)
                    }
                    create("prod") {
                        resValue("string", "app_name", "MovieDB")
                        buildConfigField("String", "API_KEY", apiKey)
                        buildConfigField("String", "API_TOKEN", apiToken)
                    }
                }
            }

            dependencies {
                "implementation"(libs.androidx.core.ktx)
                "implementation"(libs.androidx.appcompat)
                "implementation"(libs.material)

                //region Hilt
                "implementation"(libs.androidx.hilt.navigation.compose)
                "implementation"(libs.hilt.android)
                "ksp"(libs.hilt.compiler)
                //endregion

                "implementation"(libs.xlog)

                "testImplementation"(libs.junit)
                "androidTestImplementation"(libs.androidx.junit)
                "androidTestImplementation"(libs.androidx.espresso.core)
            }
        }
    }
}