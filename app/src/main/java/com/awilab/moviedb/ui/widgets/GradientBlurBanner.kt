package com.awilab.moviedb.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent

@Composable
fun GradientBlurBanner(
    imgUrl: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. 底層：原圖
        SubcomposeAsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            loading = {
                LoadingItem()
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                )
            },
            success = {
                SubcomposeAsyncImageContent(
                    modifier = Modifier.matchParentSize(),
                )
            }
        )

        // 2. 上層：漸層 Alpha 遮罩
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithCache {
                    val w = size.width
                    val mask = Brush.horizontalGradient(
                        // 前 30% 白色不透明，之後漸變到透明
                        0.0f to Color.DarkGray,
                        0.35f to Color.DarkGray,
                        1.0f to Color.Transparent,
                        startX = 0f,
                        endX = w
                    )
                    onDrawBehind {
                        drawRect(
                            brush = mask
                        )
                    }
                }
        )
    }
}