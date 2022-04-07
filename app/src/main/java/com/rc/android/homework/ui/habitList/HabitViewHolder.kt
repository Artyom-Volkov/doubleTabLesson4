package com.rc.android.homework.ui.habitList

import androidx.recyclerview.widget.RecyclerView
import com.rc.android.homework.Habit
import com.rc.android.homework.databinding.HabitCardBinding
import com.rc.android.homework.ui.viewmodels.HabitViewModel

class HabitViewHolder(private val binding: HabitCardBinding) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var viewModel: HabitViewModel

    fun bind(habit: Habit){

        binding.viewModel = HabitViewModel(habit)

        /*binding.habitNameTv.text = habit.name
        binding.habitDecriptionTv.text = habit.decr
        binding.habitTypeTv.text = when(habit.type.value){
            Habit.Type.USEFULL.value -> itemView.context.getString(R.string.usefull_habit)
            else -> {itemView.context.getString(R.string.harmfull_habit)}
        }
        binding.habitPriorityTv.text = habit.priority.toString()
        binding.habitFreqTv.text = habit.freq.executionNumber.toString() + " раз в " + habit.freq.countTimePeriod + " " + habit.freq.timePeriod.time*/
    }


}