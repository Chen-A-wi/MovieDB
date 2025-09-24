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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
            color = MaterialTheme.colorScheme.onPrimary,
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
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .onFocusChanged { isFocused = it.isFocused },
            shape = MaterialTheme.shapes.extraLarge,
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                if (!isFocused && query.isEmpty()) {
                    Text(
                        text = stringResource(hintRes),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            },
            // 左邊搜尋 icon
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
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
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onPrimary,              // 光標顏色
                focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.outline,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,         // 聚焦時文字
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,       // 未聚焦時文字
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,       // 聚焦時邊框顏色
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,       // 未聚焦時邊框顏色
            )
        )
    }
}