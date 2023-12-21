package com.comets.comets.ui.screen.classroom

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.repository.RoomRepository
import com.comets.comets.data.response.RoomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(
    private val repository: RoomRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassroomUiState())
    val uiState: StateFlow<ClassroomUiState> = _uiState

    fun getRooms() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.getRooms()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            rooms = result.data
                        )
                    }
                    Log.d(
                        "rooms",
                        result.data.toString()
                    )
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

data class ClassroomUiState(
    val rooms: List<RoomResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)