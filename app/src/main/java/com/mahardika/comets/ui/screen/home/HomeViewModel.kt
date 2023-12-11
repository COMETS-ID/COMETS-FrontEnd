package com.mahardika.comets.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahardika.comets.data.repository.UserRepository
import com.mahardika.comets.utils.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel(){

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val userTodayMoods = listOf<Mood>(
        Mood.HAPPY,
        Mood.NEUTRAL,
        Mood.SAD,
        Mood.ANGER,
        Mood.FEARFUL
    )
    fun getUserData() {
        viewModelScope.launch {
            userRepository.getUser()
        }
    }

    fun setUserMood(mood: Mood) {
        _uiState.update { HomeUiState(
            todayMood = mood
        ) }
    }
}