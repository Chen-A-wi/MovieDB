package com.awilab.moviedb.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.awilab.moviedb.R

@Composable
fun SearchDefaultPage() {
    val searchDefAnimation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.searching_default)
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = searchDefAnimation,
            iterations = LottieConstants.IterateForever,
        )
    }
}