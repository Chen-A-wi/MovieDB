package com.awilab.baseplugin.common

import com.android.build.api.dsl.LibraryExtension
import com.awilab.baseplugin.extension.configureAndroid
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

/**
 * Android library holding shared unit test helpers.
 * Kept separate from BaseLibPlugin, which adds this module to every module's tests.
 */
class TestingLibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = the<LibrariesForLibs>()

            pluginManager.apply(libs.plugins.android.library.get().pluginId)

            extensions.configure<LibraryExtension> {
                configureAndroid()
            }

            dependencies {
                "api"(libs.junit)
                "api"(libs.androidx.lifecycle.livedata.core)
            }
        }
    }
}
