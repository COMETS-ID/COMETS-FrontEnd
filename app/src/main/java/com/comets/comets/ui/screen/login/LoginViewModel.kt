package com.comets.comets.ui.screen.login

import android.util.Patterns
import androidx.compose.ui.text.input.TextFieldValue
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
class LoginViewModel @Inject constructor(
    private val authTokenPreferences: AuthTokenPreferences,
    private val repository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun updateEmailField(email: TextFieldValue) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(
                uiState.value.email.text
            )
            .matches()
    }

    fun updatePasswordField(password: TextFieldValue) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            when (val result = repository.loginUser(
                LoginRequest(
                    uiState.value.email.text,
                    uiState.value.password.text,
                )
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    authTokenPreferences.saveAuthToken(result.data.accessToken)
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                else -> {}
            }
        }
    }

    fun resetError() {
        _uiState.update {
            it.copy(
                error = ""
            )
        }
    }

}

data class LoginUiState(
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val error: String = "",
)

data class LoginRequest(
    val email: String,
    val password: String,
)