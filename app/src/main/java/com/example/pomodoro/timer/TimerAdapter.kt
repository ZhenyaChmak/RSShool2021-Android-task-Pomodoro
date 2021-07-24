package com.example.pomodoro.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.pomodoro.databinding.TimerBinding
import com.example.pomodoro.timer.`interface`.TimerListener
import com.example.pomodoro.timer.model.TimerModel

class TimerAdapter(
    private val listener: TimerListener
) : ListAdapter<TimerModel,TimerViewHolder> (itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TimerBinding.inflate(layoutInflater, parent, false)
        return TimerViewHolder(binding,listener/*,binding.root.context.resources*/)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    private companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<TimerModel>() {

            override fun areItemsTheSame(oldItem: TimerModel, newItem: TimerModel): Boolean {
                return oldItem.timerId == newItem.timerId
            }

            override fun areContentsTheSame(oldItem: TimerModel, newItem: TimerModel): Boolean {
                return oldItem.currentMs == newItem.currentMs &&
                        oldItem.isStarted == newItem.isStarted
            }

            override fun getChangePayload(oldItem: TimerModel, newItem: TimerModel) = Any()
        }
    }
}