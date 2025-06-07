package com.awilab.moviedb.ui.media

enum class MediaDataType {
    IMAGE, VIDEO
}

data class ComposeMediaData(
    val dataType: MediaDataType,
    val imageUri: String,
    val videoUri: String? = null,
)
