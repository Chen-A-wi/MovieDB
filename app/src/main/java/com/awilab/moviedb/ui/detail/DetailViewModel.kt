package com.awilab.moviedb.ui.detail

import androidx.lifecycle.ViewModel
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    val navigator: MovieDbNavigator
) : ViewModel() {
}