package com.awilab.moviedb.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awilab.moviedb.R
import com.awilab.moviedb.ui.widgets.AppBar
import com.awilab.moviedb.ui.widgets.LoadingPage

@Composable
fun DetailPage(
    vm: DetailViewModel = hiltViewModel(),
) {
    val isLoading by vm.isLoading.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        vm.initMovieDetail()
    }

    Scaffold(
        topBar = {
            AppBar(
                titleRes = R.string.lab_detail,
                showBack = true,
                onBack = {
                    vm.navigator.goBack()
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isLoading) {
                LoadingPage()
            } else {
                Column(
                    modifier = Modifier.verticalScroll(scrollState),
                ) {
                    DetailHeader()

                    MovieInfo()

                    CastLayout()
                }
            }
        }
    }
}
