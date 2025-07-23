package com.awilab.moviedb.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awilab.moviedb.R
import com.awilab.moviedb.ui.widgets.AppBar
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder

@Composable
fun HomePage(
    vm: HomeViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setArrowPosition(0.5f)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(12)
        setCornerRadius(8f)
        setBackgroundColorResource(R.color.teal_700)
//        setBalloonAnimation(BalloonAnimation.ELASTIC)
        setIsVisibleOverlay(true)
//        setBalloonHighlightAnimation(BalloonHighlightAnimation.SHAKE)
//        setDismissWhenClicked(true)
    }

    Scaffold(
        topBar = {
            AppBar(R.string.app_name)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .clickable {
//                },
//        ) {
//            Text(text = "Hello Home Screen")
//        }

        LazyColumn {
            items(100) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    Balloon(
                        modifier = Modifier.align(Alignment.Center),
                        builder = builder,
                        balloonContent = {
                            Column {
                                Text(
                                    modifier = Modifier.clickable {
                                        println("============== click Balloon1 ====================")
                                    },
                                    text = "Item 1",
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )

                                Text(
                                    modifier = Modifier.clickable {
                                        println("============== click Balloon2 ====================")
                                    },
                                    text = "Item 2",
                                    textAlign = TextAlign.Center,
                                    color = Color.Red
                                )
                            }

                        }
                    ) { balloonWindow ->
                        Button(
                            modifier = Modifier.size(60.dp, 60.dp),
                            onClick = { balloonWindow.showAlignBottom() }
                        ) {
                            Text(text = "B")
                        }
                    }
                }
            }
        }
    }
}
