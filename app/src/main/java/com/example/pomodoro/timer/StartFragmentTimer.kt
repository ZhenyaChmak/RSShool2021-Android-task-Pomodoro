package com.example.pomodoro.timer

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomodoro.R
import com.example.pomodoro.databinding.CreatingTimerBinding
import com.example.pomodoro.timer.`interface`.TimerListener
import com.example.pomodoro.timer.model.TimerModel

class StartFragmentTimer : Fragment(R.layout.creating_timer), TimerListener{

    private var _binding: CreatingTimerBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val timers = mutableListOf<TimerModel>()
    private val timerAdapter = TimerAdapter(this)
    private var nextTimerId = 0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreatingTimerBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timerAdapter
        }


        binding.addTimer.setOnClickListener {

            var munytes = binding.addMinutes.text.toString().toLongOrNull()?.let {
                binding.addMinutes.text.toString().toLong() * 1000L * 60L
            }?:0L

            when {
                munytes in 1..86400000 -> {
                    timers.add(TimerModel(nextTimerId++, munytes, false, munytes,munytes))
                    timerAdapter.submitList(timers.toList())
                }
                munytes >= 86460000 -> {
                    Toast.makeText(
                        context,
                        R.string.time_is_incorrect,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        context,
                        R.string.time_not_set,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        return binding.root
    }


    override fun delete(id: Int) {
        timers.remove(timers.find { it.timerId == id })
        timerAdapter.submitList(timers.toList())
    }

    override fun start(id: Int) {
        changeTimerModel(id, null, true)
    }

    override fun stop(id: Int, currentMs: Long) {
        changeTimerModel(id, currentMs, false)
    }

    private fun changeTimerModel(id: Int, currentMs: Long?, isStarted: Boolean) {
        val newTimers = mutableListOf<TimerModel>()
        timers.forEach {
            if (it.timerId == id) {
                newTimers.add(TimerModel(it.timerId, currentMs ?: it.currentMs,
                    isStarted, it.timerClock, it.timerProgressBarStopwatch))
            } else {
                newTimers.add(it)
            }
        }
        timerAdapter.submitList(newTimers)
        timers.clear()
        timers.addAll(newTimers)
    }


    companion object{
        @JvmStatic
            fun newInstance() : StartFragmentTimer{
                return StartFragmentTimer()
            }


    }
}