package com.awilab.moviedb.ui.media

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class ExoPlayersPool(
  private val context: Context,
  pages: List<ComposeMediaData>,
) {
  private val players = hashMapOf<Int, ExoPlayer>()

  init {
    prepareExoPlayers(pages)
  }

  private fun prepareExoPlayers(pages: List<ComposeMediaData>) {
    pages.onEachIndexed { index, _ ->
      createAndGet(index)
    }
  }

  fun createAndGet(index: Int): ExoPlayer {
    val id = index % PLAYERS_COUNT
    if (players.contains(id)) {
      return players[id]!!
    }

    val exoPlayer = ExoPlayer.Builder(context)
      .build()
      .apply {
        repeatMode = Player.REPEAT_MODE_ALL
        volume = 0f
      }
    players[id] = exoPlayer
    return exoPlayer
  }

  fun releaseAll() {
    players.forEach {
      it.value.stop()
      it.value.release()
    }
    players.clear()
  }

  companion object {
    private const val PLAYERS_COUNT = 3
  }
}