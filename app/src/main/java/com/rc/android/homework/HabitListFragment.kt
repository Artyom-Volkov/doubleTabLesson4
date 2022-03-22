package com.rc.android.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitListFragment : Fragment(), HabitAdapter.onClickListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            HabitListFragment().apply {

            }
    }

    private var clickListener: onClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            adapter = HabitAdapter((this@HabitListFragment.activity as MainActivity).habits)
                .apply { setOnClickListener(this@HabitListFragment) }
        }

        addHabitFAB.setOnClickListener { addHabitFABClicked() }
    }

    override fun onStop() {
        super.onStop()

        habitRecyclerview.apply {
            (adapter as HabitAdapter).unsetOnClickListener()
        }
    }

    private fun addHabitFABClicked(){
        clickListener?.onAddHabitClick()
    }

    override fun onItemClick(position: Int) {
        clickListener?.onHabitClick(position)
    }

    fun setOnClickListener(clickListener: onClickListener){
        this.clickListener = clickListener
    }

    fun unsetOnClickListener(){
        this.clickListener
    }

    interface onClickListener{
        fun onHabitClick(position: Int)
        fun onAddHabitClick()
    }
}