package com.rc.android.homework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitsDatabase
import com.rc.android.homework.R
import com.rc.android.homework.ui.habitEditing.HabitEditingFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), HabitEditingFragment.Listener, MainFragmentAdapter.Listener {

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private var habitEditingFragment: HabitEditingFragment? = null

    private lateinit var mainFragmentAdapter: MainFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFragmentAdapter = MainFragmentAdapter(this)
        pager.adapter = mainFragmentAdapter
        mainFragmentAdapter.setListener(this)

        val tabNames: Array<String> = arrayOf(
            getString(R.string.usefull_habit),
            getString(R.string.harmfull_habit),
        )

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        addHabitFAB.setOnClickListener { addHabitFABClicked() }
    }

    private fun addHabitFABClicked(){
        //clickListener?.onAddHabitClick()
        habitEditingFragment = HabitEditingFragment.newInstance()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.containerMainActivity, habitEditingFragment!!)
            .addToBackStack(null)
            //.add(R.id.containerMainActivity, habitEditingFragment!!)
            .commit()
        habitEditingFragment?.setListener(this)
    }

    override fun addNewHabit(habit: Habit) {

        this.let {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.containerMainActivity, it)
                .commit()
        }

        habit.let {
            val habits = when (it.type) {
                Habit.Type.HARMFULL -> HabitsDatabase.harmfullHabits
                else -> {HabitsDatabase.usefullHabits}
            }
            habits.add(it)
        }
        //habitRecyclerview?.adapter?.notifyDataSetChanged()
    }

    override fun HabitEdited(habit: Habit, position: Int, isNewHabitType: Boolean) {
        this.let {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.containerMainActivity, it)
                .commit()
        }

        if (!isNewHabitType){
            val habitType = habit.type
            val habits = when(habitType) {
                Habit.Type.USEFULL -> HabitsDatabase.usefullHabits
                Habit.Type.HARMFULL -> HabitsDatabase.harmfullHabits
            }
            habits.set(position, habit)
        }
        else{
            val habitNewType = habit.type
            val habitsNewType = when(habitNewType) {
                Habit.Type.USEFULL -> HabitsDatabase.usefullHabits
                Habit.Type.HARMFULL -> HabitsDatabase.harmfullHabits
            }
            val habitsOldType = when(habitNewType) {
                Habit.Type.USEFULL -> HabitsDatabase.harmfullHabits
                Habit.Type.HARMFULL -> HabitsDatabase.usefullHabits
            }
            habitsOldType.removeAt(position)
            habitsNewType.add(habit)
        }
    }

    override fun onHabitClick(habitType: Habit.Type?, position: Int) {

        habitType?.let {
            val habits = when(it) {
                Habit.Type.USEFULL -> HabitsDatabase.usefullHabits
                Habit.Type.HARMFULL -> HabitsDatabase.harmfullHabits
            }

            val habit = habits.get(position)

            habitEditingFragment = HabitEditingFragment.newInstance(habit, position)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.containerMainActivity, habitEditingFragment!!)
                .addToBackStack(null)
                //.add(R.id.containerMainActivity, habitEditingFragment!!)
                .commit()
            habitEditingFragment?.setListener(this)
        }


    }
}