package com.example.flowproject.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

enum class Difficulty {EASY, MEDIUM, HARD}

enum class Subject{MATH, HISTORY, LITERATURE}


@Entity(tableName = "question_table")
@Parcelize
@TypeConverters(Converters::class)
data class Question (val subject: Subject,
                     val difficulty: Difficulty,
                     val text: String,
                     val options: List<String>,
                     @PrimaryKey(autoGenerate = true) val id: Int = 0) : Parcelable {


}