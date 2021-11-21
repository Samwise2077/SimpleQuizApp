package com.example.flowproject.ui.gameresults

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(val questionText: String?, val yourAnswer: String?, val correctAnswer: String) :
    Parcelable
