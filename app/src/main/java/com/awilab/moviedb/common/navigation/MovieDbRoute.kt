package com.awilab.moviedb.common.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Serializable
data object SearchRoute : NavKey

@Serializable
data class DetailRoute(val movieId: Int) : NavKey
