package com.awilab.moviedb.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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