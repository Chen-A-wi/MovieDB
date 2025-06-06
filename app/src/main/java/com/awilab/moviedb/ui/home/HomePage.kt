package com.awilab.moviedb.ui.home

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.awilab.moviedb.R
import com.awilab.moviedb.ui.media.ComposeMediaActivity
import com.awilab.moviedb.ui.widgets.AppBar

@Composable
fun HomePage(
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(R.string.app_name)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .clickable {
                },
        ) {
            Text(
                modifier = Modifier.clickable {
                    vm.navigator.launchActivity(
                        context = context,
                        activity = ComposeMediaActivity::class.java
                    )
                },
                text = "Hello Home Screen"
            )
        }
    }
}
