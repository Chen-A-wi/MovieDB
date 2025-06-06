package com.awilab.moviedb.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MediaViewPager(data: List<String>) {
    val pagerState = rememberPagerState(pageCount = { data.size })

    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (page % 2 == 0) Color.Blue else Color.Green),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Page: $page", color = Color.White, fontSize = 24.sp)
        }
    }
}