package com.awilab.baseplugin.extension

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.BuildConfigField
import com.awilab.baseplugin.configs.Version
import org.gradle.api.Project
import java.util.Properties

@Suppress("UnstableApiUsage")
internal fun CommonExtension.configureAndroid() {
    compileSdk = Version.COMPILE_SDK

    defaultConfig.apply {
        minSdk = Version.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions.apply {
        sourceCompatibility = Version.jdk
        targetCompatibility = Version.jdk
    }

    testOptions.apply {
        unitTests.isIncludeAndroidResources = true
    }

    buildFeatures.apply {
        buildConfig = true
        resValues = true
    }
}

/**
 * Adds API_KEY and API_TOKEN from local.properties to BuildConfig of every variant.
 * The values in local.properties already include the surrounding quotes.
 */
internal fun Project.configureApiBuildConfigFields() {
    val properties = Properties().apply {
        rootProject.file("local.properties").inputStream().use { load(it) }
    }
    val apiKey = requireNotNull(properties.getProperty("API_KEY")) { "API_KEY is missing in local.properties" }
    val apiToken = requireNotNull(properties.getProperty("API_TOKEN")) { "API_TOKEN is missing in local.properties" }

    extensions.getByType(AndroidComponentsExtension::class.java).onVariants { variant ->
        variant.buildConfigFields?.put("API_KEY", BuildConfigField(type = "String", value = apiKey, comment = null))
        variant.buildConfigFields?.put("API_TOKEN", BuildConfigField(type = "String", value = apiToken, comment = null))
    }
}
