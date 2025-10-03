package com.awilab.moviedb.ui.search

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.awilab.moviedb.common.compose.debounceClickable
import com.awilab.network.di.BASE_IMAGE_URL
import com.awilab.network.model.SearchResults

@Composable
fun SearchResultItem(
    itemData: SearchResults.Result,
    onClick: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${BASE_IMAGE_URL}t/p/w500/${itemData.posterPath}")
            .crossfade(true)
            .build(),
        contentDescription = itemData.title.orEmpty(),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f) // 寬高比 2:3
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .debounceClickable(onClick = {
                onClick.invoke()
            })
    )
}