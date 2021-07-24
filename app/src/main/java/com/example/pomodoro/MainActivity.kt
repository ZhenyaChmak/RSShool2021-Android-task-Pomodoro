package com.example.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomodoro.databinding.ActivityMainBinding
import com.example.pomodoro.databinding.CreatingTimerBinding
import com.example.pomodoro.timer.StartFragmentTimer
import com.example.pomodoro.timer.TimerAdapter
import com.example.pomodoro.timer.model.TimerModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val fragment : StartFragmentTimer = StartFragmentTimer.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(binding.container.id, fragment )
                .commit()
        }
    }
}