package com.example.pomodoro.timer.`interface`

interface TimerListener {

    fun delete (id: Int)

    fun start(id: Int)

    fun stop(id: Int, currentMs: Long)
}