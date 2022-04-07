package com.rc.android.homework.ui.habitList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rc.android.homework.Habit
import com.rc.android.homework.R
import com.rc.android.homework.databinding.HabitCardBinding

class HabitAdapter (
    private val habits: List<Habit>,
    private val typeOfFilteringHabits: Habit.Type?
    ) : RecyclerView.Adapter<HabitViewHolder>() {

    var clickListener: onClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: HabitCardBinding = DataBindingUtil.inflate(inflater, R.layout.habit_card, parent, false)


        var habitViewHolder = HabitViewHolder( binding )

        binding.root.setOnClickListener {
            clickListener?.onItemClick(habitViewHolder.adapterPosition)
        }

        return habitViewHolder
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    fun setOnClickListener(clickListener: onClickListener) {
        this.clickListener = clickListener
    }

    fun unsetOnClickListener() {
        this.clickListener = null
    }

    interface onClickListener{
        fun onItemClick(position: Int)
    }
}

