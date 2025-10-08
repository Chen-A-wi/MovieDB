package com.awilab.moviedb.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.awilab.moviedb.R
import com.awilab.moviedb.common.compose.debounceClickable
import com.awilab.network.di.BASE_IMAGE_URL
import com.awilab.network.model.SearchResults

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    itemData: SearchResults.Result,
    onClick: () -> Unit,
) {
    val imgUrl = "${BASE_IMAGE_URL}t/p/w500/${itemData.posterPath}"

    Card(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .debounceClickable { onClick.invoke() }
            .fillMaxWidth()
            .aspectRatio(2f / 3f),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        SubcomposeAsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.BrokenImage,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
            },
            success = {
                SubcomposeAsyncImageContent(
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}
