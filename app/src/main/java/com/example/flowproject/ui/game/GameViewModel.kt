package com.example.flowproject.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.flowproject.data.Difficulty
import com.example.flowproject.data.Question
import com.example.flowproject.data.QuestionDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import com.example.flowproject.ui.gameresults.Result

@HiltViewModel
class GameViewModel @Inject constructor(
   private val state: SavedStateHandle
) : ViewModel(){
    var questionCounter = 0
    var scores = 0
    val resultsList = mutableListOf<Result>()
    var questionText = state.get<Array<Question>>("questionList")!![questionCounter].text
    var options = state.get<Array<Question>>("questionList")!![questionCounter].options
    val questionList = state.get<Array<Question>>("questionList")
    val difficulty = state.get<Difficulty>("difficulty")
    fun getOption(num: Int): String? {
        if(num < 0 || num > 3){
            return null
        }
        return state.get<Array<Question>>("questionList")!![questionCounter].options[num]
    }
    @JvmName("getQuestionText1")
    fun getQuestionText(): String {
       return questionText
    }

    fun onCorrectButtonClicked(isCorrect : Boolean) {
        questionCounter++
        if(isCorrect){
            when(difficulty){
                Difficulty.EASY -> scores += 100
                Difficulty.MEDIUM -> scores += 250
                Difficulty.HARD -> scores += 400
            }
        }
        if(questionCounter < questionList!!.size){
            questionText = questionList[questionCounter].text
            options = questionList[questionCounter].options
        }
    }

}