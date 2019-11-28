package com.kotlinforandroiddevelopment.montecarloandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        /*
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main, ChartFragment.newInstance())
        transaction.commit()
        */
    }

    private fun insertFragment(numReps : Int, quota : Int, iterations : Int) {
        val transaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main, ChartFragment.newInstance(numReps, quota, iterations))
        transaction.addToBackStack(null)
        transaction.commit()
    }



}
