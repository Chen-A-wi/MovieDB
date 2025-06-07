package com.awilab.moviedb.ui.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMediaActivity : ComponentActivity() {
    private val vm by viewModels<ComposeMediaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MediaViewPager(data = vm.getFakeMediaList())

//            TestVideoPlayer("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")
//            TestVideoPlayer("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4")
//            TestVideoPlayer("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4")
        }
    }
}