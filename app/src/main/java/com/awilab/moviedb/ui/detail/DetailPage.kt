package com.awilab.moviedb.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.awilab.moviedb.R
import com.awilab.moviedb.ui.widgets.ActorItemView
import com.awilab.moviedb.ui.widgets.AppBar
import com.awilab.moviedb.ui.widgets.LoadingItem
import com.awilab.moviedb.ui.widgets.LoadingPage
import com.awilab.network.di.BASE_IMAGE_URL
import com.awilab.network.model.MovieDetail
import com.elvishew.xlog.XLog

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
                    modifier = Modifier.verticalScroll(scrollState),
                ) {
                    DetailHeader(movieInfo)

                    movieInfo.apply {
                        MovieInfo(
                            title = title.orEmpty(),
                            overview = overview.orEmpty(),
                            releaseDate = releaseDate.orEmpty(),
                            originCountry = originCountry
                        )

                        credits?.cast?.let { casts ->
                            LazyRow {
                                items(casts) { castInfo ->
                                    ActorItemView(castInfo)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GradientBlurBanner(
    imgUrl: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. 底層：原圖
        SubcomposeAsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            loading = {
                LoadingItem()
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                )
            },
            success = {
                SubcomposeAsyncImageContent(
                    modifier = Modifier.matchParentSize(),
                )
            }
        )

        // 2. 上層：漸層 Alpha 遮罩
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithCache {
                    val w = size.width
                    val mask = Brush.horizontalGradient(
                        // 前 30% 白色不透明，之後漸變到透明
                        0.0f to Color.DarkGray,
                        0.35f to Color.DarkGray,
                        1.0f to Color.Transparent,
                        startX = 0f,
                        endX = w
                    )
                    onDrawBehind {
                        drawRect(
                            brush = mask
                        )
                    }
                }
        )
    }
}


@Composable
fun DetailHeader(movieInfo: MovieDetail) {
    val imgUrl = "${BASE_IMAGE_URL}t/p/w500${movieInfo.posterPath}"
    val backgroundUrl = "${BASE_IMAGE_URL}t/p/original${movieInfo.backdropPath}"

    XLog.d(imgUrl)
    XLog.d(backgroundUrl)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        GradientBlurBanner(
            imgUrl = backgroundUrl,
        )

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
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
                loading = {
                    LoadingItem()
                },
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

@Composable
fun MovieInfo(
    title: String,
    overview: String,
    releaseDate: String,
    originCountry: List<String?>? = emptyList()
) {
    Text(
        text = title,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(releaseDate)

            if (!originCountry.isNullOrEmpty()) {
                FlowRow {
                    originCountry.forEach { country ->
                        Text("．")
                        Text(
                            text = country.orEmpty(),
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = Color.DarkGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        Text(
            "Overview",
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
        )
        Text(
            overview,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Justify
        )
    }
}
