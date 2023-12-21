package com.comets.comets.ui.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.comets.comets.R
import com.comets.comets.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isConfPasswordVisible by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.signup),
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = uiState.username,
                onValueChange = {
                    viewModel.updateUsernameField(it)
                },
                label = {
                    Text(
                        stringResource(R.string.username),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = {
                    viewModel.updateEmailField(it)
                },
                label = {
                    Text(
                        stringResource(id = R.string.email),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                supportingText = {
                    if (uiState.email.text.isNotEmpty() && !viewModel.isEmailValid()) Text(
                        text = stringResource(id = R.string.error_email_is_not_valid)
                    )
                },
                isError = uiState.email.text.isNotEmpty() && !viewModel.isEmailValid(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = {
                    viewModel.updatePasswordField(it)
                },
                label = {
                    Text(
                        stringResource(id = R.string.password),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                trailingIcon = {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = uiState.confPassword,
                onValueChange = {
                    viewModel.updateConfPasswordField(it)
                },
                label = {
                    Text(
                        stringResource(R.string.signup_confirm_password),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                visualTransformation = if (isConfPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                trailingIcon = {
                    IconButton(onClick = {
                        isConfPasswordVisible = !isConfPasswordVisible
                    }) {
                        Icon(
                            imageVector = if (isConfPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isConfPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.signupUser()
                },
                enabled = viewModel.isSignupEnabled(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.signup))
            }
            Spacer(modifier = Modifier.height(32.dp))
            ClickableText(
                text = AnnotatedString(stringResource(R.string.signup_already_have_an_account)),
                onClick = {
                    navController.navigate(Screen.Authentication.Login.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        if (uiState.error.isNotEmpty()) {
            AlertDialog(onDismissRequest = {
                viewModel.resetError()
            },
                confirmButton = {
                    TextButton(onClick = { viewModel.resetError() }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.error),
                        style = MaterialTheme.typography.displayMedium
                    )
                },
                text = {
                    Text(text = uiState.error)
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = null
                    )
                })
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

