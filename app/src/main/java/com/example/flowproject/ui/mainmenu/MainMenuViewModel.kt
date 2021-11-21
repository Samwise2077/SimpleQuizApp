package com.example.flowproject.ui.mainmenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowproject.data.Difficulty
import com.example.flowproject.data.PreferencesManager
import com.example.flowproject.data.Subject
import com.example.flowproject.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainMenuViewModel"

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    @ApplicationScope applicationScope: CoroutineScope
) : ViewModel() {
    val preferencesFlow = preferencesManager.preferencesFlow
    var highScore: Int = 0
    var lastSubject: Subject = Subject.MATH
    var lastDifficulty: Difficulty = Difficulty.EASY
    private val mainMenuEventChannel = Channel<MainMenuEvent>()
    val mainMenuEvent = mainMenuEventChannel.receiveAsFlow()
    fun onChoosingStageClick(subject: Subject, difficulty: Difficulty){
        viewModelScope.launch{
            mainMenuEventChannel.send(MainMenuEvent.NavigateToChoosingStageScreen(subject, difficulty))
        }
    }
    init {
        applicationScope.launch {
            highScore = preferencesFlow.first().highScore
            lastSubject = preferencesFlow.first().lastSubject
            lastDifficulty = preferencesFlow.first().lastDifficulty
            Log.d(TAG, "just fine, ${lastSubject}")
        }
    }
    sealed class MainMenuEvent{
        data class NavigateToChoosingStageScreen(val subject: Subject, val difficulty: Difficulty) : MainMenuEvent()
    }
}