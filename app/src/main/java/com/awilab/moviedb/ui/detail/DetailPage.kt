package com.awilab.moviedb.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awilab.moviedb.R
import com.awilab.moviedb.ui.widgets.LoadingPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(
    vm: DetailViewModel = hiltViewModel(),
) {
    val isLoading by vm.isLoading.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    LaunchedEffect(Unit) {
        vm.initMovieDetail()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { stringResource(R.string.lab_empty) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { vm.navigator.goBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
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
