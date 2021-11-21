package com.example.flowproject.ui.choosingstage

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

import com.example.flowproject.data.*
import com.example.flowproject.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoosingStageViewModel @Inject constructor(
    private val questionDao: QuestionDao,
    @ApplicationScope val applicationScope: CoroutineScope,
    private val preferencesManager: PreferencesManager,
    private val state: SavedStateHandle
) : ViewModel() {
    private val TAG = "ChoosingStageViewModel"
    private val listLiveData: LiveData<List<Question>>
    val preferencesFlow = preferencesManager.preferencesFlow
    var lastSubject: Subject? = state.get<Subject>("subject")
    var lastDifficulty: Difficulty? = state.get<Difficulty>("difficulty")

    init {
        listLiveData = questionDao.getQuestions(Subject.MATH, Difficulty.EASY)
    }


    fun getQuestions(subject: Subject, difficulty: Difficulty): LiveData<List<Question>> {
        return questionDao.getQuestions(subject, difficulty)

    }


       fun onListFilled(){
           applicationScope.launch {
               questionDao.deleteAll()

               questionDao.insert(Question(Subject.MATH, Difficulty.EASY, "5 + 7", listOf("12", "9", "10", "15")))
               questionDao.insert(Question(Subject.MATH, Difficulty.EASY, "13 * 5", listOf("65", "50", "75",  "40")))
               questionDao.insert(Question(Subject.MATH, Difficulty.EASY, "63 / 9", listOf("7", "5", "9",  "8")))
               questionDao.insert(Question(Subject.MATH, Difficulty.EASY, "117 - 63", listOf("54", "34", "64",  "74")))
               questionDao.insert(Question(Subject.MATH, Difficulty.EASY, "8 ^ 2", listOf("64", "16", "56", "75")))

               questionDao.insert(Question(Subject.HISTORY, Difficulty.MEDIUM, "When did World War 2 break out?", listOf("1939", "1941", "1956", "1918")))
               questionDao.insert(Question(Subject.HISTORY, Difficulty.MEDIUM, "When did the Patriotic War start?", listOf("1812", "1786", "1300", "1939")))
               questionDao.insert(Question(Subject.HISTORY, Difficulty.MEDIUM, "When did the USSR collapse?", listOf("1991", "1976", "1987", "1997")))
               questionDao.insert(Question(Subject.HISTORY, Difficulty.MEDIUM, "Who was the first president of the US?", listOf("G. Washington", "A. Lincoln", "T. Jefferson", "J. Kennedy")))
               questionDao.insert(Question(Subject.HISTORY, Difficulty.MEDIUM, "What is the first Russian ruling dynasty?", listOf("Rurik", "Romanov", "Sheremetev", "Tolstoy")))

               Log.d(TAG, "onListChanged: ok")
           }
       }

       fun onLastSubjectSelected(subject: Subject){
           applicationScope.launch {
               Log.d(TAG, "onLastSubjectSelected: $subject")
               preferencesManager.updateLastSubject(subject)
           }
       }

        fun onLastDifficultySelected(difficulty: Difficulty){
          applicationScope.launch {
            preferencesManager.updateLastDifficulty(difficulty)

          }
    }
}