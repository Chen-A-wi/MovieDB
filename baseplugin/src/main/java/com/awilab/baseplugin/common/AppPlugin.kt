package com.awilab.baseplugin.common

import com.android.build.api.dsl.ApplicationExtension
import com.awilab.baseplugin.extension.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<ApplicationExtension> {
                configureAndroid()
            }
            
        }
    }
}