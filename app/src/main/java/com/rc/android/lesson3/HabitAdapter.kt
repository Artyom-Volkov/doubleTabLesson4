package com.rc.android.lesson3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter (
    private val habits: List<Habit>
    ) : RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.habit_card, parent, false)

        var habitViewHolder = HabitViewHolder( v )

        v.setOnClickListener {
            val intent = Intent(it.context, HabitEditingActivity::class.java
            ).apply {
                val bundle = Bundle().apply{

                    habits.get(habitViewHolder.adapterPosition).apply {
                        putString(HabitEditingActivity.EXTRA_NAME_TO, name)
                        putString(HabitEditingActivity.EXTRA_DECR_TO, decr)
                        putInt(HabitEditingActivity.EXTRA_PRIORITY_TO, priority)
                        putInt(HabitEditingActivity.EXTRA_EXECUTION_NUMBER_TO, freq.executionNumber.toInt())
                        putInt(HabitEditingActivity.EXTRA_COUNT_TIME_PERIOD_TO, freq.countTimePeriod.toInt())
                        putString(HabitEditingActivity.EXTRA_TIME_PERIOD_TO, freq.timePeriod.time)
                        putInt(HabitEditingActivity.EXTRA_TYPE_TO, type.value)
                        putInt(HabitEditingActivity.EXTRA_HABIT_POSITION_TO, habitViewHolder.adapterPosition)
                    }
                }
                putExtras(bundle)

            }
            (it.context as MainActivity).startActivityForResult(intent, MainActivity.REQUEST_EDIT_HABIT)
        }

        return habitViewHolder
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }
}