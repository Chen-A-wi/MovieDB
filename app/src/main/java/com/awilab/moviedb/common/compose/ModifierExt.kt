package com.awilab.moviedb.common.compose

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
fun Modifier.debounceClickable(
    debounceMillis: Long = 300L,
    onClick: () -> Unit
): Modifier = composed {
    // extraBufferCapacity 緩存 1 筆emit
    val clickFlow = remember { MutableSharedFlow<Unit>(extraBufferCapacity = 1) }

    LaunchedEffect(clickFlow) {
        clickFlow.debounce(debounceMillis)
            .collect { onClick.invoke() }
    }

    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = LocalIndication.current
    ) {
        clickFlow.tryEmit(Unit)
    }
}