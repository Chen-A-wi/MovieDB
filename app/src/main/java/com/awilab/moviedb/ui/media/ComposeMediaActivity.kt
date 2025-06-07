package com.awilab.moviedb.ui.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ComposeMediaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val aa = listOf(
                ComposeMediaData(
                    dataType = MediaDataType.IMAGE,
                    imageUri = "https://res.cloudinary.com/deu7aohfe/image/upload/v1721272516/202407103204289410/dulbwb1ubuks6xrpturu.webp",
                ),
                ComposeMediaData(
                    dataType = MediaDataType.IMAGE,
                    imageUri = "https://res.cloudinary.com/deu7aohfe/image/upload/v1721981718/202407103204289410/ogevtjvpnwuubplczirg.avif",
                ),
                ComposeMediaData(
                    dataType = MediaDataType.IMAGE,
                    imageUri = "https://res.cloudinary.com/deu7aohfe/image/upload/v1721377436/202407103204289410/vcxkibtbpf9cgqrugn2s.avif",
                ),
            )

            MediaViewPager(data = aa)
        }
    }
}