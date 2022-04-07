package com.rc.android.homework.ui.habitEditing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitFreq
import com.rc.android.homework.HabitTimePeriod
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitEditingBinding
import com.rc.android.homework.ui.viewmodels.HabitEditingViewModel

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
    private lateinit var habitEditing: HabitEditing
    private var position: Int? = null

    private var listener: Listener? = null

    private lateinit var viewModel: HabitEditingViewModel

    private lateinit var binding: FragmentHabitEditingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habit = it.getParcelable(Habit::class.simpleName)
            position = it.getInt(HABIT_POSITION)
        }
        habitEditing = HabitEditing(habit)

        //viewModel = ViewModelProvider(this, HabitViewModelFactory(habit) ).get(HabitViewModel::class.java)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitEditingViewModel(habitEditing, listener) as T
            }
        }).get(HabitEditingViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentHabitEditingBinding>(inflater, R.layout.fragment_habit_editing, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        //listener = viewModel.listener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habit?.let {
            /*nameEditText.text.append(it.name)
            prioritySpinner.setSelection(it.priority - 1)
            executionNumberEditText.text.append(it.freq.executionNumber.toString())
            countTimePeriodEditText.text.append(it.freq.countTimePeriod.toString())

            val position = it.freq.timePeriod.ordinal
            timePeriodSpinner.setSelection(position)

            habitDecrEditText.text.append(it.decr)

            when(it.type){
                Habit.Type.USEFULL -> usefulHabitRadioButton.isChecked = true
                Habit.Type.HARMFULL -> harmfulHabitRadioButton.isChecked = true
            }*/
        }

        binding.saveHabitButton.setOnClickListener{ saveHabitButtonClicked() }
    }

    private fun saveHabitButtonClicked(){

        if (binding.nameEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите название привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (binding.habitTypeRadioGroup.checkedRadioButtonId == -1){
            Toast.makeText(context, "Укажите тип привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (binding.executionNumberEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите кол-во выполнения привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        if (binding.countTimePeriodEditText.text.isEmpty()){
            Toast.makeText(context, "Укажите периодичность привычки", Toast.LENGTH_SHORT)
                .apply { show() }
            return
        }

        val freqHabit = HabitFreq(binding.executionNumberEditText.text.toString().toInt(),
            binding.countTimePeriodEditText.text.toString().toInt(),
            HabitTimePeriod.values().get(binding.timePeriodSpinner.selectedItemPosition)
        )

        val habit = Habit(
            name = binding.nameEditText.text.toString(),
            decr = binding.habitDecrEditText.text.toString(),
            type = when(binding.habitTypeRadioGroup.checkedRadioButtonId){
                binding.usefulHabitRadioButton.id -> Habit.Type.USEFULL
                else -> Habit.Type.HARMFULL
            },
            priority = binding.prioritySpinner.selectedItem.toString().toInt(),
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