package com.rc.android.homework.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.rc.android.homework.R
import com.rc.android.homework.ui.habitEditing.HabitEditingFragment
import com.rc.android.homework.ui.habitList.HabitListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()/*, HabitListFragment.onClickListener, HabitEditingFragment.Listener*/{

    var habitListFragment: HabitListFragment? = null
    var habitEditingFragment: HabitEditingFragment? = null
    var mainFragment: MainFragment? = null

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
                    mainFragment = MainFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.containerMainActivity, mainFragment!!)
                        //.addToBackStack(null)
                        .commit()
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
            mainFragment = MainFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.containerMainActivity, mainFragment!!)
                //.addToBackStack(null)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}