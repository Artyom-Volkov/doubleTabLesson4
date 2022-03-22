package com.rc.android.homework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_habit_list.*

class MainActivity : AppCompatActivity(), HabitListFragment.onClickListener, HabitEditingFragment.Listener{

    val habits: MutableList<Habit> = mutableListOf<Habit>()

    var habitListFragment: HabitListFragment? = null
    var habitEditingFragment: HabitEditingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setTitle(R.string.app_name)
            //it.setDisplayHomeAsUpEnabled(true)
            //it.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val drawerToggle = ActionBarDrawerToggle(this,
            navigationDrawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        //drawerToggle.setDrawerIndicatorEnabled(true);
        navigationDrawerLayout.addDrawerListener(drawerToggle)

        navigationView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.menu_item_main_activity -> {
                    habitListFragment = HabitListFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.containerMainActivity, habitListFragment!!)
                        //.addToBackStack(null)
                        .commit()
                    habitListFragment?.setOnClickListener(this)
                }
                R.id.menu_item_about_activity -> {
                    val aboutAplicationFragment = AboutAplicationFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        //.add(R.id.containerMainActivity, aboutAplicationFragment!!)
                        .replace(R.id.containerMainActivity, aboutAplicationFragment!!)
                        //.addToBackStack(null)
                        .commit()
                }

            }
            navigationDrawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }


        if (savedInstanceState == null){
            habitListFragment = HabitListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.containerMainActivity, habitListFragment!!)
                //.addToBackStack(null)
                .commit()
            habitListFragment!!.setOnClickListener(this)
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onHabitClick(position: Int) {
        habitEditingFragment = HabitEditingFragment.newInstance(habits[position], position)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerMainActivity, habitEditingFragment!!)
            .addToBackStack(null)
            //.add(R.id.containerMainActivity, habitEditingFragment!!)
            .commit()
        habitEditingFragment?.setListener(this)
    }

    override fun onAddHabitClick() {
        habitEditingFragment = HabitEditingFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerMainActivity, habitEditingFragment!!)
            .addToBackStack(null)
            //.add(R.id.containerMainActivity, habitEditingFragment!!)
            .commit()
        habitEditingFragment?.setListener(this)
    }

    override fun addNewHabit(habit: Habit) {

        habitListFragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerMainActivity, it)
                .commit()
        }

        habit?.let { habits.add(it) }
        habitRecyclerview?.adapter?.notifyDataSetChanged()
    }

    override fun HabitEdited(habit: Habit, position: Int) {
        habitListFragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerMainActivity, it)
                .commit()
        }

        habits.set(position, habit)
        habitRecyclerview?.adapter?.notifyItemChanged(position)
    }
}