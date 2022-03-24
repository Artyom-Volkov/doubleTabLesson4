package com.rc.android.homework

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment), HabitListFragment.onClickListener {

    private var listener: Listener? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        val habitType: Habit.Type = when (position){
            0 -> Habit.Type.USEFULL
            1 -> Habit.Type.HARMFULL
            else -> {Habit.Type.USEFULL}
        }

        val fragment = HabitListFragment()
        fragment.arguments = Bundle().apply {
            putInt(HabitListFragment.HABIT_TYPE, habitType.ordinal)
        }
        fragment.setOnClickListener(this)
        return fragment
    }

    override fun onHabitClick(habitType: Habit.Type?, position: Int) {
        listener?.onHabitClick(habitType, position)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun unsetListener(){
        this.listener = null
    }

    interface Listener{
        fun onHabitClick(habitType: Habit.Type?, position: Int)
    }
}