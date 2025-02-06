package com.awilab.baseplugin.common

import com.android.build.gradle.LibraryExtension
import com.awilab.baseplugin.extension.configureAndroid
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.the

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
                configureAndroid()

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                    }

                    debug {

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

                "testImplementation"(libs.junit)
                "androidTestImplementation"(libs.androidx.junit)
                "androidTestImplementation"(libs.androidx.espresso.core)
            }
        }
    }
}