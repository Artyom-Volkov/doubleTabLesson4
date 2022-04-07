package com.rc.android.homework.ui.habitEditing

import com.rc.android.homework.Habit
import com.rc.android.homework.HabitFreq

class HabitEditing (habit: Habit?) {

    var name: String? = null
    var decr: String? = null
    var type: Habit.Type? = null
    var priority: Int? = null
    var freq: HabitFreq? = null
    var color: Int? = null

    init {
        habit?.let {
            name = it.name
            decr = it.decr
            type = it.type
            priority = it.priority
            freq = it.freq
            color = it.color
        }
    }

    fun getHabit(): Habit? {

        var habit: Habit? = null

        if (name != null && type != null && priority!= null && freq != null && color != null)
            habit = Habit(name!!, decr!!, type!!, priority!!, freq!!, color!! )

        return habit
    }

}