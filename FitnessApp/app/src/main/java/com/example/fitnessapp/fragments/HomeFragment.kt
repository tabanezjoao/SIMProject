package com.example.fitnessapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fitnessapp.R
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import com.example.fitnessapp.database.Water
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var userMain: User? = null
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_home, container, false)

        var helloUser = v.findViewById<TextView>(R.id.textViewGreet)
        helloUser.setText("Hi " + userMain?.username + "!")

        val rightNow = Calendar.getInstance()
        val month_date = SimpleDateFormat("EEE dd MMM")
        val month_name: String = month_date.format(rightNow.getTime())
        var date = v.findViewById<TextView>(R.id.textViewDate)
        date.text = month_name

        var myDatabase: MyDatabase = MyDatabase.build(v.context)

        var waters: List<Water>? = userMain?.userId?.let { myDatabase.DAO().getWatersWithUserId(it) }

        var waterValue: Long = 0

        if(waters != null)
        {
            // queremos mostrar a informaçao caso ja tenhamos uma quantidade de agua adicionada
            var rightNow = Calendar.getInstance()
            var day_date = SimpleDateFormat("dd")
            var day_now: Int = day_date.format(rightNow.getTime()).toInt()

            var day_water: Int

            waters.forEach {
                day_water = day_date.format(Calendar.getInstance().getTime()).toInt()
                if(day_water == day_now)
                {
                    waterValue += it.water!!
                }
            }

            var waterValueText = v.findViewById<TextView>(R.id.textViewWaterValue)

            waterValueText.text = waterValue.toString()
        }

        return v
    }

    override fun onResume() {
        var myDatabase: MyDatabase? = view?.let { MyDatabase.build(it.context) }

        var waters: List<Water>? = userMain?.userId?.let { myDatabase?.DAO()?.getWatersWithUserId(it) }

        var waterValue: Long = 0

        if(waters != null)
        {
            // queremos mostrar a informaçao caso ja tenhamos uma quantidade de agua adicionada
            var rightNow = Calendar.getInstance()
            var day_date = SimpleDateFormat("dd")
            var day_now: Int = day_date.format(rightNow.getTime()).toInt()

            var day_water: Int

            waters.forEach {
                day_water = day_date.format(Calendar.getInstance().getTime()).toInt()
                if(day_water == day_now)
                {
                    waterValue += it.water!!
                }
            }

            var waterValueText = view?.findViewById<TextView>(R.id.textViewWaterValue)

            if (waterValueText != null) {
                waterValueText.text = waterValue.toString()
            }
        }
        super.onResume()
    }

    public fun setUser(userFromActivity: User)
    {
        userMain = userFromActivity
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}