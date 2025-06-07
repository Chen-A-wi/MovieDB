package com.awilab.moviedb.ui.media.xml

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

// 定義ExoPlayer function event
class XmlMediaHolder(context: Context) {
    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    fun setMediaSource(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun play() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun stop() {
        exoPlayer.stop()
    }

    fun release() {
        exoPlayer.release()
    }
}