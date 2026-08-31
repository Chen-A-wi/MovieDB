package com.awilab.data.local.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchToDetailArgs(
    val movieId: Int,
): Parcelable
