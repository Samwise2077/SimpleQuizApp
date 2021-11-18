package com.example.flowproject.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

private const val TAG = "PreferencesManager"

data class FilterPreferences(val highScore: Int, val lastSubject: Subject, val lastDifficulty: Difficulty){

}

@Singleton
class PreferencesManager  @Inject constructor(@ApplicationContext context: Context) {
     val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    val preferencesFlow: Flow<Unit> = context.dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.e(TAG, "Error reading preferences, $exception" )
                emit(emptyPreferences())
            }
            else{
                throw(exception)
            }

        }
        .map { preferences  ->
            // No type safety.
           // preferences[] ?: 0
            val highScore = preferences[PreferencesKeys.HIGH_SCORE] ?: 0
            val lastSubject = Subject.valueOf(
                preferences[PreferencesKeys.LAST_SUBJECT] ?: Subject.MATH.name
            )

            val lastDifficulty = Difficulty.valueOf (
                    preferences[PreferencesKeys.LAST_DIFFICULTY] ?: Difficulty.EASY.name)
            FilterPreferences(highScore, lastSubject, lastDifficulty)
        }


        suspend fun updateHighScore(highScore: Int, context: Context){
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.HIGH_SCORE] = highScore

            }
        }
    suspend fun updateLastSubject(subject: Subject, context: Context){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_SUBJECT] = subject.name

        }
    }
    suspend fun updateLastDifficulty(difficulty: Difficulty, context: Context){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_DIFFICULTY] = difficulty.name

        }
    }
        private object PreferencesKeys{
           val HIGH_SCORE = intPreferencesKey("high_score")
           val LAST_SUBJECT = stringPreferencesKey("last_subject")
           val LAST_DIFFICULTY = stringPreferencesKey("last_difficulty")

        }

}