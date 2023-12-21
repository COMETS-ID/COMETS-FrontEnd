package com.comets.comets.ui.screen.add_post

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.CommunityForumRepository
import com.comets.comets.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val repository: CommunityForumRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPostUiState())
    val uiState: StateFlow<AddPostUiState> = _uiState

    fun updateTextField(text: TextFieldValue) {
        _uiState.update {
            it.copy(
                text = text
            )
        }
    }

    fun postCommunityForum() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isSuccess = false
                )
            }

            when (repository.postCommunityForum(
                AddPostRequest(uiState.value.text.text)
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }

                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

data class AddPostUiState(
    val text: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = "",
)

data class AddPostRequest(
    val posting: String,
)