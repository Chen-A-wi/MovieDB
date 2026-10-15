package com.awilab.moviedb.common.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchToDetailArgs(
    val movieId: Int,
): Parcelable
