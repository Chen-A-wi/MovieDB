package com.awilab.moviedb.ui.media.xml

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.awilab.moviedb.R

class XmlMediaAdapter(
    private val videoUrls: List<String>,
    private val context: Context
) : RecyclerView.Adapter<XmlMediaAdapter.VideoViewHolder>() {
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerView: PlayerView = itemView.findViewById(R.id.playerView)
        var exoPlayerHolder: XmlMediaHolder? = null

        fun bind(videoUrl: String) {
            exoPlayerHolder = XmlMediaHolder(context).apply {
                setMediaSource(videoUrl)
                playerView.player = exoPlayer
            }
        }

        fun releasePlayer() {
            exoPlayerHolder?.stop()
            exoPlayerHolder?.release()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoUrls[position])
    }

    override fun getItemCount(): Int = videoUrls.size

    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        holder.releasePlayer()
    }
}