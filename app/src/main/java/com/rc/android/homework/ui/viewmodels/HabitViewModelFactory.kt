package com.rc.android.homework.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rc.android.homework.ui.habitEditing.HabitEditing
import com.rc.android.homework.ui.habitEditing.HabitEditingFragment

class HabitViewModelFactory(private val habit: HabitEditing, public var listener: HabitEditingFragment.Listener?): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(HabitEditingViewModel::class.java))
           return HabitEditingViewModel(habit, listener) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}