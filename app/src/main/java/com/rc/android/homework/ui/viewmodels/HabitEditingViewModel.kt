package com.rc.android.homework.ui.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rc.android.homework.ui.habitEditing.HabitEditing
import com.rc.android.homework.ui.habitEditing.HabitEditingFragment

class HabitEditingViewModel(private val inputModel: HabitEditing, public var listener: HabitEditingFragment.Listener?) : ViewModel() {

    private val mutableHabit: MutableLiveData<HabitEditing?> = MutableLiveData()

    //private val mutableHabitFreq: MutableLiveData<HabitFreq?> = MutableLiveData()

    val habit: LiveData<HabitEditing?> = mutableHabit
    //val habitFreq: LiveData<HabitFreq?> = mutableHabitFreq
    //var listener: HabitEditingFragment.Listener? = null

    init {
        mutableHabit.value = inputModel
        //mutableHabitFreq.value = inputModel.freq
    }

    override fun onCleared() {
        super.onCleared()

        listener = null
    }

}