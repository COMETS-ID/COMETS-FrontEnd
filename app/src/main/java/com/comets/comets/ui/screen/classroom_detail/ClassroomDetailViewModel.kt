package com.comets.comets.ui.screen.classroom_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.repository.RoomRepository
import com.comets.comets.data.response.AssesmentItem
import com.comets.comets.data.response.Room
import com.comets.comets.data.response.UserRoomItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassroomDetailViewModel @Inject constructor(
    private val repository: RoomRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassroomDetailUiState())
    val uiState: StateFlow<ClassroomDetailUiState> = _uiState

    fun getRoomDetailUser(uuid: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.getRoomDetailUser(uuid = uuid)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            users = result.data.userRoom,
                            roomDetail = result.data.room
                        )
                    }
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

    fun getAssessmentFromUserRoom(uuid: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.getAssessmentFromUserRoom(uuid)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            assessments = result.data.assesment
                        )
                    }
                    Log.d(
                        "roomsassessment",
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

    fun inviteUser(
        uuid: String,
        id: String,
        onSuccess: (message: String) -> Unit,
        onError: (message: String) -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.inviteUser(
                uuid,
                InviteRequest(id)
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    onSuccess("User invited")
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

data class ClassroomDetailUiState(
    val roomDetail: Room? = null,
    val assessments: List<AssesmentItem> = emptyList(),
    val users: List<UserRoomItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)

data class InviteRequest(
    val userId: String,
)