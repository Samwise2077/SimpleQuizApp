package com.example.flowproject.ui.gameresults

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowproject.data.PreferencesManager
import com.example.flowproject.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameResultsViewModel @Inject constructor(
    private val state: SavedStateHandle,
    @ApplicationScope val applicationScope: CoroutineScope,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val scores = state.get<Int>("highScore") ?: 0
    val results = state.get<Array<Result>>("resultList")?.asList()
    val preferencesFlow = preferencesManager.preferencesFlow
    private val gameResultsEventChannel = Channel<GameResultsEvent>()
    val gameResultsEvent = gameResultsEventChannel.receiveAsFlow()


    fun onHighScoreChanged(highScore: Int) {
        applicationScope.launch {
            preferencesManager.updateHighScore(highScore)
        }
    }

    fun onBackButtonClick(){
        viewModelScope.launch {
            gameResultsEventChannel.send(GameResultsEvent.NavigateBackToMainMenu)
        }
    }

    sealed class GameResultsEvent{
        object NavigateBackToMainMenu : GameResultsEvent()
    }
}