package com.kotlinforandroiddevelopment.montecarloandroid

import android.os.Bundle
import android.service.autofill.Dataset
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), InputFragment.OnFragmentInteractionListener {

    lateinit var toolbar: ActionBar
    lateinit var dataset : DoubleArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setSupportActionBar(toolbar)
        // bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            val fragment = InputFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_input -> {
                toolbar.title = "Input"
                val fragment = InputFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chart -> {
                toolbar.title = "Chart"
                val fragment = ChartFragment.newInstance(dataset)
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_printedData -> {
                toolbar.title = "Printed Data"
                val fragment = PrintedDataFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onFragmentSetDataset(arr: DoubleArray) {
        dataset = arr
    }


}
