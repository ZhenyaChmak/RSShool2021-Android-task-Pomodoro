package com.example.pomodoro.timer

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import android.util.TypedValue
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pomodoro.R
import com.example.pomodoro.R.color.*
import com.example.pomodoro.databinding.TimerBinding
import com.example.pomodoro.timer.`interface`.TimerListener
import com.example.pomodoro.timer.model.TimerModel
import java.util.*
import java.util.concurrent.CountDownLatch


class TimerViewHolder (
    private val binding : TimerBinding,
    private val listener: TimerListener,
//    private val resources: Resources
    ) : RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null
    private lateinit var timerStopClock : String
    private var startTime = 0L

    fun bind(timerModel: TimerModel) {
        binding.root.setCardBackgroundColor(Color.WHITE)
        binding.stopwatch.text = timerModel.currentMs.displayTime()

        binding.progressStopwatch.setPeriod(timerModel.timerProgressBarStopwatch)

        startTime = System.currentTimeMillis()

        if(timerModel.isStarted){
            startTimer(timerModel)
        } else{
            stopTimer()
        }

        initButtonsListeners(timerModel)

    }


    private fun initButtonsListeners(timerModel: TimerModel) {
        binding.startButton.setOnClickListener{
            if(timerModel.isStarted){
                listener.stop(timerModel.timerId, timerModel.currentMs)
            } else {
                listener.start(timerModel.timerId)
                timerStopClock = getTimerStopClock(timerModel.timerClock)
            }
        }

        binding.deleteButton.setOnClickListener {
            listener.delete(timerModel.timerId)
            binding.progressStopwatch.setCurrent(0L)
            timer?.cancel()
        }
    }


    private fun startTimer(timerModel: TimerModel){
        binding.startButton.text = "stop"

        timer?.cancel()
        timer = getCountDownTimer(timerModel)
        timer?.start()

        binding.blinkingIndicator.isInvisible = false
        (binding.blinkingIndicator.background as? AnimationDrawable)?.start()
    }

    private fun stopTimer() {
        binding.startButton.text = "start"

        timer?.cancel()

        binding.blinkingIndicator.isInvisible = true
        (binding.blinkingIndicator.background as? AnimationDrawable)?.stop()

    }


    private fun getCountDownTimer(timerModel: TimerModel): CountDownTimer {
        return object : CountDownTimer(PERIOD, UNIT_TEN_MS) {
            val interval = UNIT_TEN_MS

            override fun onTick(millisUntilFinished: Long) {
                timerModel.currentMs -= interval
                binding.stopwatch.text = timerModel.currentMs.displayTime()
                binding.progressStopwatch.setCurrent(timerModel.currentMs)

            }

            override fun onFinish() {
                binding.stopwatch.text = timerModel.currentMs.displayTime()
            }
        }
    }


    private fun getTimerStopClock(currentMs:Long) : String {

        val h = currentMs / 1000 / 3600
        val m = currentMs / 1000 % 3600 / 60
        val s = currentMs / 1000 % 60
        return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
    }

    private fun Long.displayTime(): String {
        if (this <= 0L) {
            binding.root.setCardBackgroundColor(Color.rgb(103,3,3))

            binding.blinkingIndicator.isInvisible = true
            (binding.blinkingIndicator.background as? AnimationDrawable)?.stop()

            binding.startButton.text = "start"
            binding.startButton.setClickable(false)

            binding.progressStopwatch.setPeriod(0L)

            return timerStopClock
        }

        val h = this / 1000 / 3600
        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60

        return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
    }


    private fun displaySlot(count: Long): String {
        return if (count / 10L > 0) {
            "$count"
        } else {
            "0$count"
        }
    }

    private companion object {
        private const val INTERVAL = 100L
        private const val PERIOD2 = 1000L * 30 // 30 sec
        private const val REPEAT = 10 // 10 times

        private const val PERIOD  = 1000L* 60L * 60L * 24L // Day
        private const val UNIT_TEN_MS = 1000L
    }
}