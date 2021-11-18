package com.example.flowproject.ui.gameresults

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.flowproject.data.PreferencesManager
import com.example.flowproject.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameResultsViewModel @Inject constructor(
    private val state: SavedStateHandle,
    @ApplicationScope val applicationScope: CoroutineScope,
    val preferencesManager: PreferencesManager
) : ViewModel() {
    val highScore = state.get<Int>("highScore") ?: 0
    val results = state.get<Array<Result>>("resultList")?.asList()
    val preferencesFlow = preferencesManager.preferencesFlow
    fun onHighScoreChanged(highScore: Int, context: Context){
        applicationScope.launch {
              preferencesManager.updateHighScore(highScore, context)
        }
    }
}