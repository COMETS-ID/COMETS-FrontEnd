package com.comets.comets.ui.viewmodels.assessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AssessmentUiState())
    val uiState: StateFlow<AssessmentUiState> = _uiState

    fun updatePrediction(prediction: String) {
        _uiState.update {
            it.copy(
                prediction = prediction
            )
        }
    }

    fun updateScore(score: String) {
        _uiState.update {
            it.copy(
                score = score
            )
        }
    }

    fun postAssessment(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.postAssessment(
                PostAssessmentRequest(
                    type = "Gambar",
                    value = uiState.value.prediction
                )
            )) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            errorPrediction = result.message.toString()
                        )
                    }
                    onError(result.message.toString())
                }

                else -> {}
            }

            when (val result = repository.postAssessment(
                PostAssessmentRequest(
                    type = "Form",
                    value = uiState.value.score.toString()
                )
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onSuccess()
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorScore = result.message.toString()
                        )
                    }
                    onError(result.message.toString())
                }

                else -> {}
            }
        }
    }

    fun postAssessmentFromUserRoom(
        uuid: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.postAssessmentFromUserRoom(
                uuid = uuid,
                PostAssessmentRequest(
                    type = "Gambar",
                    value = uiState.value.prediction
                )
            )) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            errorPrediction = result.message.toString()
                        )
                    }
                    onError(result.message.toString())
                }

                else -> {}
            }

            when (val result = repository.postAssessmentFromUserRoom(
                uuid = uuid,
                PostAssessmentRequest(
                    type = "Form",
                    value = uiState.value.score
                )
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onSuccess()
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorScore = result.message.toString()
                        )
                    }
                    onError(result.message.toString())
                }

                else -> {}
            }
        }
    }
}

data class AssessmentUiState(
    val prediction: String = "",
    val score: String = "",
    val isLoading: Boolean = false,
    val errorPrediction: String = "",
    val errorScore: String = "",
)

data class PostAssessmentRequest(
    val type: String,
    val value: String,
)