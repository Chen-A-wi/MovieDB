package com.awilab.moviedb.ui.widgets

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
fun LoadingPage() {
    val catLoading by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.cat_loading)
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = catLoading,
            iterations = LottieConstants.IterateForever,
        )
    }
}