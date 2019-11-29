package com.kotlinforandroiddevelopment.montecarloandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setSupportActionBar(toolbar)
        // bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        /*
        if (savedInstanceState == null) {
            val fragment = PrintedDataFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .commit()
        }
        */

        /*
        val runBtn =
            findViewById<Button>(R.id.runButton)

        runBtn.setOnClickListener{
            // var fragment: Fragment? = ChartFragment()
            /*
            val bundle = Bundle()
            bundle.putInt("NumberOfReps", Integer.parseInt(findViewById<EditText>(R.id.numReps).text.toString()))
            bundle.putInt("Quota", Integer.parseInt(findViewById<EditText>(R.id.quota).text.toString()))
            bundle.putInt("Iterations", Integer.parseInt(findViewById<EditText>(R.id.iterations).text.toString()))

            val fragment = ChartFragment()
            fragment.arguments = bundle

             */
            insertFragment(Integer.parseInt(findViewById<EditText>(R.id.numReps).text.toString()), Integer.parseInt(findViewById<EditText>(R.id.quota).text.toString()), Integer.parseInt(findViewById<EditText>(R.id.iterations).text.toString()))
        }

         */
        /*
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main, ChartFragment.newInstance())
        transaction.commit()
        */
    }

    /*
    private fun insertFragment(numReps : Int, quota : Int, iterations : Int) {
        val transaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        val view = findViewById<ProgressBar>(R.id.loadingPanel)
        view.visibility = View.GONE
        transaction.replace(R.id.activity_main, ChartFragment.newInstance(numReps, quota, iterations))
        transaction.addToBackStack(null)
        transaction.commit()
    }

     */

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_input -> {
                toolbar.title = "Input"
                val fragment = InputFragment.newInstance("", "")
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_chart -> {
                toolbar.title = "Chart"
                val fragment = ChartFragment.newInstance(0, 0, 0)
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_printedData -> {
                toolbar.title = "Printed Data"
                val fragment = PrintedDataFragment.newInstance("", "")
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



}
