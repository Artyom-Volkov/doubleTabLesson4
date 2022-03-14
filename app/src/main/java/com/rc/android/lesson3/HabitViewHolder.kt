package com.rc.android.lesson3

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitViewHolder( view: View) : RecyclerView.ViewHolder(view) {

    private val habit_name_tv: TextView = view.findViewById(R.id.habit_name_tv)
    private val habit_decription_tv: TextView = view.findViewById(R.id.habit_decription_tv)
    private val habit_type_tv: TextView = view.findViewById(R.id.habit_type_tv)
    private val habit_priority_tv: TextView = view.findViewById(R.id.habit_priority_tv)
    private val habit_freq_tv: TextView = view.findViewById(R.id.habit_freq_tv)

    fun bind(habit: Habit){
        habit_name_tv.text = habit.name
        habit_decription_tv.text = habit.decr
        habit_type_tv.text = when(habit.type.value){
            Habit.Type.USEFULL.value -> "полезная"
            else -> {"вредная"}
        }
        habit_priority_tv.text = habit.priority.toString()
        habit_freq_tv.text = habit.freq.executionNumber.toString() + " раз в " + habit.freq.countTimePeriod + " " + habit.freq.timePeriod.time
    }


}