package com.mahardika.comets.ui.screen.community_forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahardika.comets.data.repository.CommunityForumRepository
import com.mahardika.comets.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityForumViewModel @Inject constructor(
    private val repository: CommunityForumRepository
) : ViewModel() {

    private val _communityForumUiState = MutableStateFlow<CommunityForumUiState>(CommunityForumUiState())
    val communityForumUiState: StateFlow<CommunityForumUiState> get() = _communityForumUiState

    fun getCommunityForumPosts() {
        viewModelScope.launch {
            try {
                _communityForumUiState.update { CommunityForumUiState(isLoading = true) }

                when (val result = repository.getCommunityForumPosts()) {
                    is Resource.Success -> {
                        _communityForumUiState.update { CommunityForumUiState(posts = result
                            .data, isLoading = false) }
                    }
                    is Resource.Error -> {
                        _communityForumUiState.update { CommunityForumUiState(errorMessage =
                        result.message, isLoading = false) }
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                _communityForumUiState.update { CommunityForumUiState(errorMessage = e.message,
                    isLoading = false) }
            }
        }
    }
}

data class CommunityForumUiState(
    val posts: List<CommunityForumPostItemContent> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)