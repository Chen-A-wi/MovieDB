package com.awilab.moviedb.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
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
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Composable
fun SearchFieldBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    hintRes: Int = R.string.lab_search,
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.lab_search),
                tint = if (isFocused) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .onFocusChanged { isFocused = it.isFocused },
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()                          // 自動收鍵盤
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.padding(start = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // 2. 當字串為空且未聚焦時顯示 hint
                        if (query.isEmpty() && !isFocused) {
                            Text(
                                text = stringResource(hintRes),
                                style = TextStyle(fontSize = 16.sp),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        // 3. 真正的輸入框
                        innerTextField()
                    }
                }
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = { onClear() }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(id = R.string.lab_clear),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
