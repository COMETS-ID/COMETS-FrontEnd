package com.comets.comets.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.prefs.AuthTokenPreferences
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authTokenPreferences: AuthTokenPreferences,
    private val repository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun logoutUser() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.logoutUser()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    authTokenPreferences.clearAuthToken()
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

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String = "",
)