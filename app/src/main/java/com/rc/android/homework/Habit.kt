package com.rc.android.homework

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Habit (var name: String,
                  var decr: String,
                  var type: @RawValue Type,
                  var priority: Int,
                  var freq:  @RawValue HabitFreq,
                  var color: Int) : Parcelable {

    enum class Type(val value: Int){
        USEFULL(0),
        HARMFULL(1)
    }
}