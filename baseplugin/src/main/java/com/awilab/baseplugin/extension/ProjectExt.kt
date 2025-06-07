package com.awilab.baseplugin.extension

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

internal fun Project.configureCompose(commonExtension: BaseExtension){
    commonExtension.apply {
        val libs = the<LibrariesForLibs>()

        dependencies {
            "implementation"(libs.androidx.activity.compose)
            "implementation"(platform(libs.androidx.compose.bom))
            "implementation"(libs.androidx.ui)
            "implementation"(libs.androidx.ui.graphics)
            "implementation"(libs.androidx.ui.tooling.preview)
            "implementation"(libs.androidx.material3)
            "implementation"(libs.coil.compose)

            "androidTestImplementation"(platform(libs.androidx.compose.bom))
            "androidTestImplementation"(libs.androidx.ui.test.junit4)
            "debugImplementation"(libs.androidx.ui.tooling)
            "debugImplementation"(libs.androidx.ui.test.manifest)
        }
    }
}