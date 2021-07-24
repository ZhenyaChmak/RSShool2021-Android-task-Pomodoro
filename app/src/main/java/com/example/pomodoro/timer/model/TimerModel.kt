package com.example.pomodoro.timer.model

import com.example.pomodoro.progressbarstopwatch.ProgressBarStopwatch

data class TimerModel(
    val timerId : Int,
    var currentMs : Long,
    var isStarted : Boolean = false,
    var timerClock : Long,
    var timerProgressBarStopwatch: Long
)
