package com.kotlinforandroiddevelopment.montecarloandroid.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlinforandroiddevelopment.montecarloandroid.Data
import com.kotlinforandroiddevelopment.montecarloandroid.MCViewModel
import com.kotlinforandroiddevelopment.montecarloandroid.R
import kotlinx.coroutines.*

class InputFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_input, container, false)
        val runBtn =
            view.findViewById<Button>(R.id.runButton)

        progressBar = view.findViewById(R.id.loadingPanel)

        progressBar.visibility = View.INVISIBLE

        runBtn.setOnClickListener{

            launch{

                progressBar.visibility = View.VISIBLE
                disableEditTextBoxes(view)

                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                    R.id.navigation_chart
                ).isEnabled = false
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                    R.id.navigation_printedData
                ).isEnabled = false
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                    R.id.navigation_about
                ).isEnabled = false

                val model = activity?.run {ViewModelProviders.of(this)[MCViewModel::class.java] } ?: throw Exception("Invalid Activity")

                model.dataset =
                    Data(
                        model.calculatePoints(
                            Integer.parseInt(view.findViewById<EditText>(R.id.numReps).text.toString()),
                            Integer.parseInt(view.findViewById<EditText>(R.id.quota).text.toString()),
                            Integer.parseInt(view.findViewById<EditText>(R.id.iterations).text.toString())
                        )
                    )


                progressBar.visibility = View.INVISIBLE
                enableEditTextBoxes(view)


                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                    R.id.navigation_chart
                ).isEnabled = true
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                    R.id.navigation_printedData
                ).isEnabled = true
                activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                    R.id.navigation_about
                ).isEnabled = true

            }
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun disableEditTextBoxes(view : View)
    {
        view.findViewById<EditText>(R.id.numReps).isFocusableInTouchMode = false
        view.findViewById<EditText>(R.id.numReps).isCursorVisible = false

        view.findViewById<EditText>(R.id.quota).isFocusableInTouchMode = false
        view.findViewById<EditText>(R.id.quota).isCursorVisible = false

        view.findViewById<EditText>(R.id.iterations).isFocusableInTouchMode = false
        view.findViewById<EditText>(R.id.iterations).isCursorVisible = false
    }

    private fun enableEditTextBoxes(view : View)
    {
        view.findViewById<EditText>(R.id.numReps).isFocusableInTouchMode = true
        view.findViewById<EditText>(R.id.numReps).isCursorVisible = true

        view.findViewById<EditText>(R.id.quota).isFocusableInTouchMode = true
        view.findViewById<EditText>(R.id.quota).isCursorVisible = true

        view.findViewById<EditText>(R.id.iterations).isFocusableInTouchMode = true
        view.findViewById<EditText>(R.id.iterations).isCursorVisible = true
    }

    companion object {

        @JvmStatic
        fun newInstance() : InputFragment {
            return InputFragment()
        }

    }
}
