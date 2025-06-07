package com.awilab.moviedb.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage

@Composable
fun MediaViewPager(data: List<ComposeMediaData>) {
    val pagerState = rememberPagerState(pageCount = { data.size })

    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            when (data[page].dataType) {
                MediaDataType.IMAGE -> {
                    AsyncImage(
                        model = data[page].imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                MediaDataType.VIDEO -> {
                    data[page].videoUri?.let { url ->
                        VideoPlayer(
                            url = url,
                            isPlaying = pagerState.currentPage == page,
                        )
                    }
                }
            }
        }
    }
}

// TODO: Need to use PlayerPool to manage the ExoPlayer instances
@Composable
fun VideoPlayer(url: String, isPlaying: Boolean) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) exoPlayer.play() else exoPlayer.pause()
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply { player = exoPlayer }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun TestVideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}