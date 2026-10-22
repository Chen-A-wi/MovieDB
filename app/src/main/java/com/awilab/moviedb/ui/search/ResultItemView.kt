package com.awilab.moviedb.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.awilab.domain.model.Movie
import com.awilab.moviedb.common.compose.debounceClickable
import com.awilab.moviedb.ui.widgets.LoadingItem

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    itemData: Movie,
    onClick: () -> Unit,
) {
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
            model = itemData.posterUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
            loading = {
                LoadingItem()
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemData.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
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
