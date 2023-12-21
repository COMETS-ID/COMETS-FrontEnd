package com.comets.comets.ui.screen.camera_result

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.MoodRecognitionRepository
import com.comets.comets.data.repository.Resource
import com.comets.comets.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraResultViewModel @Inject constructor(
    private val repository: MoodRecognitionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraResultUiState())
    val uiState: StateFlow<CameraResultUiState> = _uiState
    fun postFaceCapture(
        uri: Uri,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val predictRequest = Utils.createMultipartBody(
                uri,
                "file"
            )

            when (val result = repository.postFaceCapture(predictRequest)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    onSuccess(result.data)
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    }
                    Log.d(
                        "error",
                        result.message.toString()
                    )
                    onError(result.message.toString())
                }

                else -> {}
            }
        }
    }
}

data class CameraResultUiState(
    val isLoading: Boolean = false,
    val error: String = "",
)