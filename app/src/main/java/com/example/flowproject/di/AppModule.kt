package com.example.flowproject.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flowproject.data.QuestionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application,
       callback:QuestionDatabase.Callback): QuestionDatabase {

        Log.d("database", "provideDatabase: true")
        return   Room.databaseBuilder(app, QuestionDatabase::class.java, "question_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }



    @Provides
    fun provideQuestionDao(questionDatabase: QuestionDatabase) = questionDatabase.questionDao()

    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope