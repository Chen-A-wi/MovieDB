package com.awilab.common.extension

import androidx.annotation.VisibleForTesting
import com.google.common.truth.Truth

@VisibleForTesting
infix fun <T> T.shouldBe(that: T) = Truth.assertThat(that).isEqualTo(this)