package com.comets.comets.ui.screen.pyschologist

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
class PsychologistViewModel @Inject constructor(private val repository: PsychologistRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(PsychologistUiState())
    val uiState: StateFlow<PsychologistUiState> = _uiState

    fun getPsychologistsFromLocal(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = repository.getPsychologistsFromLocal(context)) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        psychologists = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                else -> {}
            }
        }
    }
}

data class PsychologistUiState(
    val psychologists: List<PsychologistResponseItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)