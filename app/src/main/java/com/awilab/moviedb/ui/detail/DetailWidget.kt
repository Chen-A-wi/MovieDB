package com.awilab.moviedb.ui.detail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.awilab.moviedb.ui.widgets.ActorItemView
import com.awilab.moviedb.ui.widgets.GradientBlurBanner
import com.awilab.moviedb.ui.widgets.LoadingItem
import com.awilab.network.di.BASE_IMAGE_URL

@Composable
fun DetailHeader(
    vm: DetailViewModel = hiltViewModel()
) {
    val movieInfo by vm.movieInfo.collectAsStateWithLifecycle()

    val imgUrl = "${BASE_IMAGE_URL}t/p/w500${movieInfo.posterPath}"
    val backgroundUrl = "${BASE_IMAGE_URL}t/p/original${movieInfo.backdropPath}"

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
    vm: DetailViewModel = hiltViewModel()
) {
    val movieInfo by vm.movieInfo.collectAsStateWithLifecycle()

    Text(
        text = movieInfo.title.orEmpty(),
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
            Text(movieInfo.releaseDate.orEmpty())

            if (!movieInfo.originCountry.isNullOrEmpty()) {
                FlowRow {
                    movieInfo.originCountry?.forEach { country ->
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

        movieInfo.overview?.let { overview ->
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
}

@Composable
fun CastLayout(
    vm: DetailViewModel = hiltViewModel()
) {
    val movieInfo by vm.movieInfo.collectAsStateWithLifecycle()

    Text(
        "Cast",
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
    )

    movieInfo.credits?.cast?.let { casts ->
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(casts) { castInfo ->
                ActorItemView(castInfo)
            }
        }
    }
}