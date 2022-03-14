package com.rc.android.lesson3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HabitEditingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA_TO_HABIT_EDITING = "EXTRA_DATA_TO_HABIT_EDITING_ACTIVITY"

        const val EXTRA_NAME_TO = "EXTRA_NAME_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_PRIORITY_TO = "EXTRA_PRIORITY_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_EXECUTION_NUMBER_TO = "EXTRA_EXECUTION_NUMBER_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_COUNT_TIME_PERIOD_TO = "EXTRA_COUNT_TIME_PERIOD_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_TIME_PERIOD_TO = "EXTRA_TIME_PERIOD_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_DECR_TO = "EXTRA_DECR_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_TYPE_TO = "EXTRA_TYPE_TO_HABIT_EDITING_ACTIVITY"
        const val EXTRA_HABIT_POSITION_TO = "EXTRA_HABIT_POSITION_TO_EDITING_ACTIVITY"

        const val EXTRA_NAME_FROM = "EXTRA_NAME_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_PRIORITY_FROM = "EXTRA_PRIORITY_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_EXECUTION_NUMBER_FROM = "EXTRA_EXECUTION_NUMBER_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_COUNT_TIME_PERIOD_FROM = "EXTRA_COUNT_TIME_PERIOD_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_TIME_PERIOD_FROM = "EXTRA_TIME_PERIOD_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_DECR_FROM = "EXTRA_DECR_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_TYPE_FROM = "EXTRA_TYPE_FROM_HABIT_EDITING_ACTIVITY"
        const val EXTRA_HABIT_POSITION_FROM = "EXTRA_HABIT_POSITION_FROM_EDITING_ACTIVITY"
    }

    private lateinit var nameEditText: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var executionNumberEditText: EditText
    private lateinit var countTimePeriodEditText: EditText
    private lateinit var timePeriodSpinner: Spinner
    private lateinit var habitDecrEditText: EditText
    private lateinit var habitTypeRadioGroup: RadioGroup
    private lateinit var usefulHabitRadioButton: RadioButton
    private lateinit var harmfulHabitRadioButton: RadioButton
    private lateinit var saveHabitButton: Button

    private var habitPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_editing)

        nameEditText = findViewById(R.id.nameEditText)
        prioritySpinner = findViewById(R.id.prioritySpinner)
        executionNumberEditText = findViewById(R.id.executionNumberEditText)
        countTimePeriodEditText = findViewById(R.id.countTimePeriodEditText)
        timePeriodSpinner = findViewById(R.id.timePeriodSpinner)
        habitDecrEditText = findViewById(R.id.habitDecrEditText)
        habitTypeRadioGroup = findViewById(R.id.habitTypeRadioGroup)
        usefulHabitRadioButton = findViewById(R.id.usefulHabitRadioButton)
        harmfulHabitRadioButton = findViewById(R.id.harmfulHabitRadioButton)
        saveHabitButton = findViewById(R.id.saveHabitButton)

        saveHabitButton.setOnClickListener { saveHabitButtonClicked()}

        intent.extras?.apply {
            getString(EXTRA_NAME_TO)?.let { nameEditText.text.append(it)  }
            getInt(EXTRA_PRIORITY_TO)?.let { prioritySpinner.setSelection(it-1) }
            getInt(EXTRA_EXECUTION_NUMBER_TO)?.let { executionNumberEditText.text.append(it.toString()) }
            getInt(EXTRA_COUNT_TIME_PERIOD_TO)?.let { countTimePeriodEditText.text.append(it.toString()) }
            getString(EXTRA_TIME_PERIOD_TO)?.let {
                val position = when(it){
                    HabitTimePeriod.DAY.time -> HabitTimePeriod.DAY.ordinal
                    HabitTimePeriod.WEEK.time -> HabitTimePeriod.WEEK.ordinal
                    HabitTimePeriod.MONTH.time -> HabitTimePeriod.MONTH.ordinal
                    HabitTimePeriod.YEAR.time -> HabitTimePeriod.YEAR.ordinal
                    HabitTimePeriod.MINUTE.time -> HabitTimePeriod.MINUTE.ordinal
                    HabitTimePeriod.HOUR.time -> HabitTimePeriod.HOUR.ordinal
                    else -> {HabitTimePeriod.MINUTE.ordinal}
                }
                timePeriodSpinner.setSelection(position)
            }
            getString(EXTRA_DECR_TO)?.let { habitDecrEditText.text.append(it) }
            getInt(EXTRA_TYPE_TO)?.let {
                when(it){
                    Habit.Type.USEFULL.value -> usefulHabitRadioButton.isChecked = true
                    Habit.Type.HARMFULL.value -> harmfulHabitRadioButton.isChecked = true
                }
            }
            getInt(EXTRA_HABIT_POSITION_TO)?.let { habitPosition = it }

        }
    }

    private fun saveHabitButtonClicked(){

        if (nameEditText.text.isEmpty()){
            Toast.makeText(applicationContext, "Укажите название привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (habitTypeRadioGroup.checkedRadioButtonId == -1){
            Toast.makeText(applicationContext, "Укажите тип привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (executionNumberEditText.text.isEmpty()){
            Toast.makeText(applicationContext, "Укажите кол-во выполнения привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (countTimePeriodEditText.text.isEmpty()){
            Toast.makeText(applicationContext, "Укажите периодичность привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        val answerIntent = Intent().apply {
            putExtra(EXTRA_NAME_FROM, nameEditText.text.toString())
            putExtra(EXTRA_PRIORITY_FROM, prioritySpinner.selectedItem.toString().toInt())
            putExtra(EXTRA_EXECUTION_NUMBER_FROM, executionNumberEditText.text.toString().toInt() )
            putExtra(EXTRA_COUNT_TIME_PERIOD_FROM, countTimePeriodEditText.text.toString().toInt())
            putExtra(EXTRA_TIME_PERIOD_FROM, timePeriodSpinner.selectedItem.toString())
            putExtra(EXTRA_DECR_FROM, habitDecrEditText.text.toString())
            putExtra(EXTRA_TYPE_FROM,
                when(habitTypeRadioGroup.checkedRadioButtonId){
                    usefulHabitRadioButton.id -> Habit.Type.USEFULL.value
                    else -> Habit.Type.HARMFULL.value
                }
            )
            putExtra(EXTRA_HABIT_POSITION_FROM, habitPosition)
        }
        setResult(RESULT_OK, answerIntent)
        finish()
    }

}