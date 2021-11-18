package com.example.flowproject.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface QuestionDao {


    @Query("SELECT * FROM question_table WHERE subject = :subject AND difficulty = :difficulty")
    fun getQuestions(subject: Subject, difficulty: Difficulty) : LiveData<List<Question>>

    @Update
    suspend fun update(question: Question)

    @Insert
    suspend fun insert(question: Question)

    @Delete
    suspend fun delete(question: Question)

    @Query("Delete FROM question_table")
    fun deleteAll()
}