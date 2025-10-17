package com.awilab.moviedb.ui.detail

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.awilab.moviedb.R
import com.awilab.moviedb.common.compose.debounceClickable
import com.awilab.moviedb.ui.widgets.AppBar
import com.awilab.moviedb.ui.widgets.LoadingItem
import com.awilab.moviedb.ui.widgets.LoadingPage
import com.awilab.network.di.BASE_IMAGE_URL
import com.awilab.network.model.MovieDetail

@Composable
fun DetailPage(
    vm: DetailViewModel = hiltViewModel(),
) {
    val isLoading by vm.isLoading.collectAsStateWithLifecycle()
    val movieInfo by vm.movieInfo.collectAsStateWithLifecycle()
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
//                    modifier = Modifier.verticalScroll(scrollState),
                ) {
                    DetailHeader(movieInfo)
                }
            }
        }
    }
}

@Composable
fun DetailHeader(movieInfo: MovieDetail) {
    val imgUrl = "${BASE_IMAGE_URL}t/p/w500/${movieInfo.posterPath}"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(color = MaterialTheme.colorScheme.outline)
    ) {
        Card(
            modifier = Modifier
//                .align(Alignment.CenterStart)
                .padding(8.dp)
                .aspectRatio(2f / 3f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            SubcomposeAsyncImage(
                model = imgUrl,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                loading = {},
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.BrokenImage,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                success = {
                    SubcomposeAsyncImageContent()
                }
            )
        }
    }
}
