package com.mahardika.comets.ui.viewmodels.camera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahardika.comets.data.repository.MoodRecognitionRepository
import com.mahardika.comets.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val moodRecognitionRepository: MoodRecognitionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    private val _sharedFlowFaceCapture = MutableSharedFlow<PostFaceCaptureState>()
    val sharedFlowUploadImage = _sharedFlowFaceCapture.asSharedFlow()

    fun uploadImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            _sharedFlowFaceCapture.emit(PostFaceCaptureState(isLoading = true))

            moodRecognitionRepository
                .postFaceCapture(image)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.i(
                                "upload",
                                "success"
                            )

                            result.data.let { uploadImageInfo ->
                                _sharedFlowFaceCapture.emit(
                                    PostFaceCaptureState(
                                        isLoading = false,
                                        response = uploadImageInfo
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.i(
                                "upload",
                                "fail"
                            )

                            _sharedFlowFaceCapture.emit(
                                PostFaceCaptureState(
                                    error = result.message,
                                    isLoading = false,
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _sharedFlowFaceCapture.emit(
                                PostFaceCaptureState(
                                    isLoading = result.isLoading
                                )
                            )
                        }
                    }
                }
        }
    }

    fun updatePermissionState(permissionGranted: Boolean) {
        _uiState.update {
            CameraUiState(
                cameraAllowed = permissionGranted
            )
        }
    }
}