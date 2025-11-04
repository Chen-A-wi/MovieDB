package com.awilab.moviedb.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.awilab.network.di.BASE_IMAGE_URL
import com.awilab.network.model.MovieDetail

@Composable
fun ActorItemView(
    castInfo: MovieDetail.Credits.Cast?,
) {
    val actorUrl = "${BASE_IMAGE_URL}t/p/w500${castInfo?.profilePath.orEmpty()}"

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(260.dp)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth(),
                model = actorUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
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

            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(color = Color.White),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = castInfo?.name.orEmpty(),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight(700)
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = castInfo?.character.orEmpty(),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        style = TextStyle(
                            color = Color.LightGray,
                            fontSize = 12.sp
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

}