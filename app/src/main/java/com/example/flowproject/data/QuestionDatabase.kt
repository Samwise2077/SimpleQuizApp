package com.example.flowproject.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flowproject.QuizApplication
import com.example.flowproject.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Arrays.asList
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Question::class], version = 1)
abstract class QuestionDatabase : RoomDatabase(){
    abstract fun questionDao() : QuestionDao

    class Callback @Inject constructor(
        private val database: Provider<QuestionDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {

            val dao = database.get().questionDao()
            applicationScope.launch {

                  // Math Easy
                    dao.insert(Question(Subject.MATH, Difficulty.EASY, "5 + 7", listOf("9", "12", "10", "15")))
                    dao.insert(Question(Subject.MATH, Difficulty.EASY, "13 * 5", listOf("50", "75", "65", "40")))
                    dao.insert(Question(Subject.MATH, Difficulty.EASY, "63 / 9", listOf("5", "9", "7", "8")))
                    dao.insert(Question(Subject.MATH, Difficulty.EASY, "117 - 63", listOf("34", "64", "54", "74")))
                    dao.insert(Question(Subject.MATH, Difficulty.EASY, "8 ^ 2", listOf("16", "56", "75", "64")))
                    Log.d("db", "succeeded")
                  //Math Medium



                  //Math Hard


                 //History Easy

                  //History Medium

                  //History Hard


                  //Literature Easy

                  //Literature Medium

                  //Literature Hard
            }
        }
    }

}