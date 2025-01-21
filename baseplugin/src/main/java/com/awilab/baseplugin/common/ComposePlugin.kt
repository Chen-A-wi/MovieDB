package com.awilab.baseplugin.common

import com.android.build.gradle.BaseExtension
import com.awilab.baseplugin.extension.configureCompose
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the

class ComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = the<LibrariesForLibs>()

            with(pluginManager) {
                apply(libs.plugins.compose.compiler.get().pluginId)
            }

            configureCompose(extensions.getByType<BaseExtension>())
        }
    }
}