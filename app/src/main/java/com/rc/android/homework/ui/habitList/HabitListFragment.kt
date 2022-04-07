package com.rc.android.homework.ui.habitList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rc.android.homework.Habit
import com.rc.android.homework.HabitsDatabase
import com.rc.android.homework.R
import com.rc.android.homework.ui.habitEditing.HabitEditingFragment
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitListFragment : Fragment(), HabitAdapter.onClickListener {

    companion object {

        const val HABIT_TYPE = "HABIT_TYPE"

        @JvmStatic
        /*fun newInstance() =
            HabitListFragment().apply {

            }*/

        fun newInstance(habitType: Habit.Type) {
            val bundle = Bundle()
            bundle.putInt(HABIT_TYPE, habitType.ordinal)
        }

    }

    //private var clickListener: onClickListener? = null
    private val handler: HabitAdapterHandler = HabitAdapterHandler()

    private var habitType: Habit.Type? = null

    private var habitEditingFragment: HabitEditingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            habitType = Habit.Type.values().get( it.getInt(HABIT_TYPE))
        }
        handler.habitType = habitType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_habit_list, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HabitListFragment.context)

            val habits = when (habitType) {
                Habit.Type.HARMFULL -> HabitsDatabase.harmfullHabits
                else -> HabitsDatabase.usefullHabits
            }

            adapter = HabitAdapter(habits, habitType)
                .apply { setOnClickListener(this@HabitListFragment.handler) }
        }
    }

    override fun onStop() {
        super.onStop()

        habitRecyclerview.apply {
            (adapter as HabitAdapter).unsetOnClickListener()
        }
    }

    override fun onItemClick(position: Int) {

        //clickListener?.onHabitClick(habitType, position)

    }

    fun setOnClickListener(clickListener: onClickListener){
        //this.clickListener = clickListener
        handler.setOnClickListener(clickListener)
    }

    fun unsetOnClickListener(){
        //this.clickListener = null
        handler.setOnClickListener(null)
    }

    interface onClickListener{
        fun onHabitClick(habitType: Habit.Type?, position: Int)
    }

    class HabitAdapterHandler(): HabitAdapter.onClickListener{

        private var clickListener: onClickListener? = null

        fun setOnClickListener(clickListener: onClickListener?){
            this.clickListener = clickListener
        }

        var habitType: Habit.Type? = null

        override fun onItemClick(position: Int) {
            clickListener?.onHabitClick(habitType, position)
        }

    }
}