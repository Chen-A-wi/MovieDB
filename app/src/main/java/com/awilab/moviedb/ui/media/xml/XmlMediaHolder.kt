package com.awilab.moviedb.ui.media.xml

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class XmlMediaHolder(context: Context) {
    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    fun setMediaSource(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun release() {
        exoPlayer.release()
    }
}