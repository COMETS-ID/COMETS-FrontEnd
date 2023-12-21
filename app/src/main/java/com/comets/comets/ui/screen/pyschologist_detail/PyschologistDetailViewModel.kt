package com.comets.comets.ui.screen.pyschologist_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.PsychologistRepository
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.response.PsychologistResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PsychologistDetailViewModel @Inject constructor(
    private val repository: PsychologistRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PsychologistDetailUiState())
    val uiState: StateFlow<PsychologistDetailUiState> = _uiState

    fun getPsychologistByIdFromLocal(
        context: Context,
        id: Int,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = repository.getPsychologistByIdFromLocal(
                context,
                id
            )) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        psychologist = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

data class PsychologistDetailUiState(
    val psychologist: PsychologistResponseItem? = null,
    val isLoading: Boolean? = null,
    val error: String? = null,
)