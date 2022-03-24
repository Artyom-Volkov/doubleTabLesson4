package com.rc.android.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_habit_editing.*

class HabitEditingFragment : Fragment() {

    companion object {

        private const val HABIT_POSITION = "HABIT_POSITION"

        @JvmStatic
        fun newInstance() =
            HabitEditingFragment().apply {

            }

        fun newInstance(habit: Habit, position: Int): HabitEditingFragment {
            val fragment = HabitEditingFragment()
            val bundle = Bundle()
            bundle.putParcelable(Habit::class.simpleName, habit)
            bundle.putInt(HABIT_POSITION, position)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var habit: Habit? = null
    private var position: Int? = null

    private var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habit = it.getParcelable(Habit::class.simpleName)
            position = it.getInt(HABIT_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit_editing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habit?.let {
            nameEditText.text.append(it.name)
            prioritySpinner.setSelection(it.priority - 1)
            executionNumberEditText.text.append(it.freq.executionNumber.toString())
            countTimePeriodEditText.text.append(it.freq.countTimePeriod.toString())

            val position = it.freq.timePeriod.ordinal
            timePeriodSpinner.setSelection(position)

            habitDecrEditText.text.append(it.decr)

            when(it.type){
                Habit.Type.USEFULL -> usefulHabitRadioButton.isChecked = true
                Habit.Type.HARMFULL -> harmfulHabitRadioButton.isChecked = true
            }
        }

        saveHabitButton.setOnClickListener{ saveHabitButtonClicked() }
    }

    private fun saveHabitButtonClicked(){

        if (nameEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите название привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (habitTypeRadioGroup.checkedRadioButtonId == -1){
            Toast.makeText(context, "Укажите тип привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (executionNumberEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите кол-во выполнения привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (countTimePeriodEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите периодичность привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        val freqHabit = HabitFreq(executionNumberEditText.text.toString().toUInt(),
            countTimePeriodEditText.text.toString().toUInt(),
            HabitTimePeriod.values().get(timePeriodSpinner.selectedItemPosition)
        )

        val habit = Habit(
            name = nameEditText.text.toString(),
            decr = habitDecrEditText.text.toString(),
            type = when(habitTypeRadioGroup.checkedRadioButtonId){
                usefulHabitRadioButton.id -> Habit.Type.USEFULL
                else -> Habit.Type.HARMFULL
            },
            priority = prioritySpinner.selectedItem.toString().toInt(),
            freq = freqHabit,
            -1
        )

        listener?.let {
            if (this.habit != null){
                if (position!= null)
                    it.HabitEdited(habit, position!!, habit.type != this.habit!!.type)
            }
            else
                it.addNewHabit(habit)
        }
    }

    fun setListener(listener: Listener){
        this.listener = listener
    }

    fun unsetListener(){
        this.listener = null
    }

    interface Listener{
        fun addNewHabit(habit: Habit);
        fun HabitEdited(habit: Habit, position: Int, isNewHabitType: Boolean);
    }
}