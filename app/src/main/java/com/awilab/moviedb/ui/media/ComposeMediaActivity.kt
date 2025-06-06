package com.awilab.moviedb.ui.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ComposeMediaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val aa = listOf("1", "2", "3")

            MediaViewPager(data = aa)
        }
    }
}