package com.comets.comets.ui.screen.add_room

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val repository: RoomRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddRoomUiState())
    val uiState: StateFlow<AddRoomUiState> = _uiState

    fun updateNameField(name: TextFieldValue) {
        _uiState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateCapacityField(capacity: TextFieldValue) {
        _uiState.update {
            it.copy(
                capacity = capacity
            )
        }
    }

    fun createRoom(
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.createRoom(
                CreatePostRequest(
                    name = uiState.value.name.text,
                    capacity = uiState.value.capacity.text
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
                            error = result.message.toString()
                        )
                    }
                    onError(result.message.toString())
                }

                else -> {}
            }
        }
    }
}

data class AddRoomUiState(
    val name: TextFieldValue = TextFieldValue(),
    val capacity: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val error: String = "",
)

data class CreatePostRequest(
    val name: String,
    val capacity: String,
)