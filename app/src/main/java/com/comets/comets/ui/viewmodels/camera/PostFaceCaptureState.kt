package com.comets.comets.ui.viewmodels.camera

import com.comets.comets.data.response.FaceRecognitionPostResponse

data class PostFaceCaptureState(
    val response: FaceRecognitionPostResponse = FaceRecognitionPostResponse(""),
    var isLoading: Boolean = false,
    val error: String? = null,
)
