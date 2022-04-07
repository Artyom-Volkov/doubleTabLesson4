package com.rc.android.homework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rc.android.homework.Habit
import com.rc.android.homework.ui.habitList.HabitListFragment

class MainFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment), HabitListFragment.onClickListener {

    //private var listener: Listener? = null

    private val handler: HabitListFragmentHandler = HabitListFragmentHandler()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        val habitType: Habit.Type = when (position){
            0 -> Habit.Type.USEFULL
            1 -> Habit.Type.HARMFULL
            else -> {
                Habit.Type.USEFULL
            }
        }

        val fragment = HabitListFragment()
        fragment.arguments = Bundle().apply {
            putInt(HabitListFragment.HABIT_TYPE, habitType.ordinal)
        }
        fragment.setOnClickListener(handler)
        return fragment
    }

    override fun onHabitClick(habitType: Habit.Type?, position: Int) {
        //listener?.onHabitClick(habitType, position)
    }

    fun setListener(listener: Listener) {
        handler.setListener(listener)
    }

    fun unsetListener(){
        handler.setListener(null)
    }

    interface Listener{
        fun onHabitClick(habitType: Habit.Type?, position: Int)
    }

    class HabitListFragmentHandler(): HabitListFragment.onClickListener{

        private var listener: Listener? = null

        fun setListener(listener: Listener?) {
            this.listener = listener
        }

        override fun onHabitClick(habitType: Habit.Type?, position: Int) {
            listener?.onHabitClick(habitType, position)
        }

    }
}