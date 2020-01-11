package com.kotlinforandroiddevelopment.montecarloandroid.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlinforandroiddevelopment.montecarloandroid.Data
import com.kotlinforandroiddevelopment.montecarloandroid.R


class MainActivity : AppCompatActivity() //, InputFragment.OnFragmentInteractionListener
{

    // lateinit var toolbar: ActionBar
    lateinit var dataset : Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar = supportActionBar!!
        supportActionBar!!.hide()
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            val fragment =
                InputFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .commit()
        }

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
            R.id.navigation_chart
        )
            .isEnabled = false
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
            R.id.navigation_printedData
        )
            .isEnabled=false

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_input -> {
                // toolbar.title = "Input"
                val fragment = InputFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chart -> {
                // toolbar.title = "Chart"
                // val fragment = ChartFragment.newInstance(dataset)
                val fragment = ChartFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_printedData -> {
                // toolbar.title = "Printed Data"
                // val fragment = PrintedDataFragment.newInstance(dataset)
                val fragment = PrintedDataFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                // toolbar.title = "About"
                val fragment = AboutFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
