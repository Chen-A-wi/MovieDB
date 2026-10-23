package com.awilab.moviedb.ui.home

import androidx.lifecycle.ViewModel
import com.awilab.common.coroutine.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
) : ViewModel() {

}