package com.rc.android.lesson3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_NEW_HABIT = 15
        const val REQUEST_EDIT_HABIT = 16
    }

    private lateinit var habitRecyclerview: RecyclerView

    val habits: MutableList<Habit> = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        habitRecyclerview = findViewById<RecyclerView?>(R.id.habitRecyclerview)
            .apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = HabitAdapter(habits)
            }

        val addHabitFAB: FloatingActionButton = findViewById(R.id.addHabitFAB)
        addHabitFAB.setOnClickListener {
            addHabitFABClicked()
        }
    }

    private fun addHabitFABClicked(){

        val intent = Intent(this@MainActivity, HabitEditingActivity::class.java
        ).apply {  }

        startActivityForResult(intent, REQUEST_NEW_HABIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_NEW_HABIT){
            if(resultCode == RESULT_OK){

                val habit: Habit? = data?.run{
                    val name = getStringExtra(HabitEditingActivity.EXTRA_NAME_FROM)
                    val decr = getStringExtra(HabitEditingActivity.EXTRA_DECR_FROM)
                    val type: Int = getIntExtra(HabitEditingActivity.EXTRA_TYPE_FROM, Habit.Type.USEFULL.value)
                    val priority: Int = getIntExtra(HabitEditingActivity.EXTRA_PRIORITY_FROM, 1)

                    val executionNumber: UInt = getIntExtra(HabitEditingActivity.EXTRA_EXECUTION_NUMBER_FROM, 0).toUInt()
                    val countTimePeriod: UInt = getIntExtra(HabitEditingActivity.EXTRA_COUNT_TIME_PERIOD_FROM, 0).toUInt()
                    val habitTimePeriod: Int = getIntExtra(HabitEditingActivity.EXTRA_TIME_PERIOD_FROM, 1)

                    val freq = HabitFreq(executionNumber, countTimePeriod, HabitTimePeriod.values().get(habitTimePeriod))

                    Habit(name!!, decr!!, Habit.Type.values().get(type), priority, freq, -1)
                }
                habit?.let { habits.add(it) }
                habitRecyclerview.adapter!!.notifyDataSetChanged()

            }
        } else if (requestCode == REQUEST_EDIT_HABIT){

            val habit: Habit? = data?.run{
                val name = getStringExtra(HabitEditingActivity.EXTRA_NAME_FROM)
                val decr = getStringExtra(HabitEditingActivity.EXTRA_DECR_FROM)
                val type: Int = getIntExtra(HabitEditingActivity.EXTRA_TYPE_FROM, Habit.Type.USEFULL.value)
                val priority: Int = getIntExtra(HabitEditingActivity.EXTRA_PRIORITY_FROM, 1)

                val executionNumber: UInt = getIntExtra(HabitEditingActivity.EXTRA_EXECUTION_NUMBER_FROM, 0).toUInt()
                val countTimePeriod: UInt = getIntExtra(HabitEditingActivity.EXTRA_COUNT_TIME_PERIOD_FROM, 0).toUInt()
                val habitTimePeriod: Int = getIntExtra(HabitEditingActivity.EXTRA_TIME_PERIOD_FROM, 1)

                val freq = HabitFreq(executionNumber, countTimePeriod, HabitTimePeriod.values().get(habitTimePeriod))

                Habit(name!!, decr!!, Habit.Type.values().get(type), priority, freq, -1)
            }
            val position: Int? = data?.run{
                getIntExtra(HabitEditingActivity.EXTRA_HABIT_POSITION_FROM, 0)
            }
            habits.set(position!!, habit!!)
            habitRecyclerview.adapter!!.notifyItemChanged(position)
        }
    }
}