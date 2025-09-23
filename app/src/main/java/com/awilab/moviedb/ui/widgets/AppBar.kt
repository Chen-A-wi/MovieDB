package com.awilab.moviedb.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awilab.moviedb.R

@Composable
fun AppBar(
    @StringRes titleRes: Int,
    showBack: Boolean = false,
    showClose: Boolean = false,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .padding(
                horizontal = if (showBack) {
                    4.dp
                } else {
                    24.dp
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showBack) {
            IconButton(onClick = { onBack?.invoke() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }

        Text(
            modifier = Modifier,
            text = stringResource(id = titleRes),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        if (showClose) {
            Spacer(Modifier.weight(1f))

            IconButton(onClick = { onClose?.invoke() }) {
                Icon(Icons.Filled.Close, contentDescription = "Back")
            }
        }
    }
}

@Composable
fun SearchFieldBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    hintRes: Int = R.string.lab_search,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = MaterialTheme.shapes.extraLarge,
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = stringResource(hintRes),
                    style = TextStyle(fontSize = 14.sp)
                )
            },
//            label = {
//                Text(text = stringResource(id = R.string.lab_search))
//            },
            // 左邊搜尋 icon
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    tint = MaterialTheme.colorScheme.outline,
                    contentDescription = stringResource(id = R.string.lab_search),
                )
            },
            // 右邊清除 icon
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onClear() }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(id = R.string.lab_clear)
                        )
                    }
                }
            },
            textStyle = TextStyle(fontSize = 14.sp),
        )
    }
}