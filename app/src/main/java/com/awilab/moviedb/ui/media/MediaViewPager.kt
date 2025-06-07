package com.awilab.moviedb.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage

@Composable
fun MediaViewPager(data: List<ComposeMediaData>) {
    val pagerState = rememberPagerState(pageCount = { data.size })
    var selectedPage by remember { mutableIntStateOf(0) }
    val playersPool = rememberExoPlayersPool(data)

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedPage = page
        }
    }

    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            val exoPlayer1 = playersPool.createAndGet(page)

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
                            isPlaying = selectedPage == page,
                            exoPlayer = exoPlayer1
                        )
                    }
                }
            }
        }
    }
}

// TODO: Need to use PlayerPool to manage the ExoPlayer instances
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    url: String,
    isPlaying: Boolean
) {
    val context = LocalContext.current
    val playerView = remember {
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(url), true)
            prepare()
        }
        PlayerView(context).apply {
            player = exoPlayer
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier.fillMaxSize(),
    )
    exoPlayer.playWhenReady = isPlaying
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

@Composable
private fun rememberExoPlayersPool(
    pages: List<ComposeMediaData>
): ExoPlayersPool {
    val context = LocalContext.current
    val playersPool = remember { ExoPlayersPool(context, pages) }

    DisposableEffect(Unit) {
        onDispose {
            playersPool.releaseAll()
        }
    }

    return playersPool
}
