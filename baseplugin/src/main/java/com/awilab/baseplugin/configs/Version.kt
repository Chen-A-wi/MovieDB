package com.awilab.baseplugin.configs

import org.gradle.api.JavaVersion

object Version {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 24
    const val TARGET_SDK = 34
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    val jdk = JavaVersion.VERSION_17
}