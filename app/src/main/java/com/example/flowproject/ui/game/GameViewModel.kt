package com.example.flowproject.ui.game

import androidx.lifecycle.*
import com.example.flowproject.data.Difficulty
import com.example.flowproject.data.Question
import com.example.flowproject.data.QuestionDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import com.example.flowproject.ui.gameresults.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GameViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {
    var questionCounter = 0
    var scores = 0
    val resultsList = mutableListOf<Result>()
    var questionText = state.get<Array<Question>>("questionList")!![questionCounter].text
    var options = state.get<Array<Question>>("questionList")!![questionCounter].options
    val questionList = state.get<Array<Question>>("questionList")
    val difficulty = state.get<Difficulty>("difficulty")

    private val gameEventChannel = Channel<GameEvent>()
    val gameEvent = gameEventChannel.receiveAsFlow()
    fun getOption(num: Int): String? {
        if (num < 0 || num > 3) {
            return null
        }
        return state.get<Array<Question>>("questionList")!![questionCounter].options[num]
    }

    fun onCorrectButtonClicked(isCorrect: Boolean) {
        questionCounter++
        if (isCorrect) {
            when (difficulty) {
                Difficulty.EASY -> scores += 100
                Difficulty.MEDIUM -> scores += 250
                Difficulty.HARD -> scores += 400
            }
        }
        if (questionCounter < questionList!!.size) {
            questionText = questionList[questionCounter].text
            options = questionList[questionCounter].options
        }
    }

    fun onQuestionsUsedUp(scores: Int, resultList: List<Result>){
        viewModelScope.launch {
            gameEventChannel.send(GameEvent.NavigateToGameResults(scores, resultList))
        }
    }
    fun onNewQuestionShow(){
        viewModelScope.launch {
            gameEventChannel.send(GameEvent.ShowNewQuestion)
        }
    }

    sealed class GameEvent{
        data class NavigateToGameResults(val scores: Int, val resultList: List<Result>) : GameEvent()
        object ShowNewQuestion : GameEvent()
    }

}