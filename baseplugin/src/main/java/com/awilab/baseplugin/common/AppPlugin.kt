package com.awilab.baseplugin.common

import com.android.build.api.dsl.ApplicationExtension
import com.awilab.baseplugin.configs.Version
import com.awilab.baseplugin.extension.configureAndroid
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

class AppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = the<LibrariesForLibs>()

            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.kotlin.serialization.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
                apply(libs.plugins.hilt.get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    targetSdk = Version.TARGET_SDK
                    versionCode = Version.VERSION_CODE
                    versionName = Version.VERSION_NAME

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                configureAndroid()

                buildFeatures {
                    compose = true
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                    }

                    debug {

                    }
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            dependencies {
                "implementation"(libs.androidx.core.ktx)
                "implementation"(libs.androidx.lifecycle.runtime.ktx)
                "implementation"(libs.kotlinx.serialization.json)

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