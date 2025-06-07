package com.awilab.moviedb.ui.media

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComposeMediaViewModel @Inject constructor() : ViewModel() {
    fun getFakeMediaList() = listOf(
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
        ComposeMediaData(
            dataType = MediaDataType.VIDEO,
            imageUri = "https://res.cloudinary.com/deu7aohfe/image/upload/v1721377436/202407103204289410/vcxkibtbpf9cgqrugn2s.avif",
            videoUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
        ),
        ComposeMediaData(
            dataType = MediaDataType.VIDEO,
            imageUri = "https://res.cloudinary.com/deu7aohfe/image/upload/v1721377436/202407103204289410/vcxkibtbpf9cgqrugn2s.avif",
            videoUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"
        ),
        ComposeMediaData(
            dataType = MediaDataType.VIDEO,
            imageUri = "https://res.cloudinary.com/deu7aohfe/image/upload/v1721377436/202407103204289410/vcxkibtbpf9cgqrugn2s.avif",
            videoUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
        ),
    )
}