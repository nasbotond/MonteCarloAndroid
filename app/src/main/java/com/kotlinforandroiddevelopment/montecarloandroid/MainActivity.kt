package com.kotlinforandroiddevelopment.montecarloandroid

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val runBtn =
            findViewById<Button>(R.id.runButton)

        runBtn.setOnClickListener{
            // var fragment: Fragment? = ChartFragment()
            replaceFragment()
        }
        /*
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main, ChartFragment.newInstance())
        transaction.commit()
        */
    }

    private fun replaceFragment() {
        val transaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main, ChartFragment.newInstance())
        transaction.addToBackStack(null)
        transaction.commit()
    }



}
