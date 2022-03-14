package com.rc.android.lesson3

enum class HabitTimePeriod (val time: String){
    MINUTE("минуту"), HOUR("час"), DAY("день"), WEEK("недель"), MONTH("месяц"), YEAR("год")
}

class HabitFreq (var executionNumber: UInt, var countTimePeriod: UInt, var timePeriod: HabitTimePeriod) {

}