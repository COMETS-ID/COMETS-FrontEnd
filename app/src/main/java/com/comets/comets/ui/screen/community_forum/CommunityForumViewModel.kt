package com.comets.comets.ui.screen.community_forum

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.CommunityForumRepository
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.response.CommentItem
import com.comets.comets.data.response.GetPostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityForumViewModel @Inject constructor(
    private val repository: CommunityForumRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityForumUiState())
    val uiState: StateFlow<CommunityForumUiState> = _uiState

    fun getCommunityForumPosts() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                when (val result = repository.getCommunityForumPosts()) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                posts = result.data
                            )
                        }
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
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getCommunityPostCommentById() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.getCommunityForumPostCommentById(
                uiState.value.currentPostUuid
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            comments = result.data.comment
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                else -> {

                }
            }
        }
    }

    fun postCommunityForumPostComment(
        onSuccess: () -> Unit,
        onError: (errorMessage: String) -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.postCommunityForumPostComment(
                uiState.value.currentPostUuid,
                CommentRequest(uiState.value.currentPostComment.text)
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
                            isLoading = false
                        )
                    }
                    onError(result.message.toString())
                }

                else -> {}
            }
        }
    }

    fun updateCurrentPostUuid(uuid: String) {
        _uiState.update {
            it.copy(
                currentPostUuid = uuid
            )
        }
    }

    fun updateCommentField(comment: TextFieldValue) {
        _uiState.update {
            it.copy(
                currentPostComment = comment
            )
        }
    }

}

data class CommunityForumUiState(
    val posts: List<GetPostResponse> = emptyList(),
    val comments: List<CommentItem> = emptyList(),
    val currentPostUuid: String = "",
    val currentPostComment: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class CommentRequest(
    val comment: String,
)