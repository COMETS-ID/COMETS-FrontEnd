package com.mahardika.comets.ui.viewmodels.camera

import com.mahardika.comets.data.response.FaceRecognitionPostResponse

data class PostFaceCaptureState(
    val response: FaceRecognitionPostResponse = FaceRecognitionPostResponse(),
    var isLoading: Boolean = false,
    val error: String? = null,
)
