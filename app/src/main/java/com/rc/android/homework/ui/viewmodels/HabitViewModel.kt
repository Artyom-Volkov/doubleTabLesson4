package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.Habit

class HabitViewModel(private val inputModel: Habit) : ViewModel()  {

    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()

    val habit: LiveData<Habit?> = mutableHabit

    init {
        mutableHabit.value = inputModel
    }
}