package com.awilab.moviedb.ui.home

import androidx.lifecycle.ViewModel
import com.awilab.common.coroutine.CommonDispatcherProvider
import com.awilab.moviedb.common.navigation.MovieDbNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val  navigator: MovieDbNavigator,
    private val dispatcher: CommonDispatcherProvider,
) : ViewModel() {

}