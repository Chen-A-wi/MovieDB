package com.awilab.moviedb.ui.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMediaActivity : ComponentActivity() {
    private val vm by viewModels<ComposeMediaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MediaViewPager(data = vm.getFakeMediaList())
        }
    }
}