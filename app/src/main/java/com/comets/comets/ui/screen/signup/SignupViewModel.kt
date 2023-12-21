package com.comets.comets.ui.screen.signup

import android.util.Patterns
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comets.comets.data.repository.Resource
import com.comets.comets.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState
    fun updateUsernameField(username: TextFieldValue) {
        _uiState.update {
            it.copy(
                username = username
            )
        }
    }

    fun updateEmailField(email: TextFieldValue) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePasswordField(password: TextFieldValue) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun updateConfPasswordField(confPassword: TextFieldValue) {
        _uiState.update {
            it.copy(
                confPassword = confPassword
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

    fun isSignupEnabled(): Boolean {
        return uiState.value.username.text.isNotEmpty() && uiState.value.email.text.isNotEmpty() && uiState.value.password.text.isNotEmpty() && uiState.value.confPassword.text.isNotEmpty()
    }

    fun resetError() {
        _uiState.update {
            it.copy(
                error = "",
            )
        }
    }

    fun signupUser() {
        if (uiState.value.password == uiState.value.confPassword) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                when (val result = repository.signupUser(
                    SignupRequest(
                        uiState.value.username.text,
                        uiState.value.email.text,
                        uiState.value.password.text,
                        uiState.value.confPassword.text
                    )
                )) {
                    is Resource.Success -> _uiState.update {
                        it.copy(
                            isLoading = false
                        )
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
        } else {
            _uiState.update {
                it.copy(
                    error = "Confirmed password does not match with the password inputted"
                )
            }
        }
    }

}

data class SignupUiState(
    val username: TextFieldValue = TextFieldValue(),
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val confPassword: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false,
    val error: String = "",
)

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val confPassword: String,
    val role: String = "user",
)