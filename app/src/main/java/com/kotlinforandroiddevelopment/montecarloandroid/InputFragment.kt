package com.kotlinforandroiddevelopment.montecarloandroid

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import rectangle_code.CoordinateWithPoints
import rectangle_code.RecCounter
import java.lang.ClassCastException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class InputFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentSetDataset(arr : DoubleArray)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try{
            listener = this.activity as OnFragmentInteractionListener
        } catch (e : ClassCastException){
            throw ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    private lateinit var listener : OnFragmentInteractionListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_input, container, false)
        // view.findViewById<ProgressBar>(R.id.loadingPanel).visibility = View.INVISIBLE
        val runBtn =
            view.findViewById<Button>(R.id.runButton)

        runBtn.setOnClickListener{

            view.findViewById<ProgressBar>(R.id.loadingPanel).visibility = View.VISIBLE
            val dataset = calculatePoints(Integer.parseInt(view.findViewById<EditText>(R.id.numReps).text.toString()), Integer.parseInt(view.findViewById<EditText>(R.id.quota).text.toString()), Integer.parseInt(view.findViewById<EditText>(R.id.iterations).text.toString()))
            view.findViewById<ProgressBar>(R.id.loadingPanel).visibility = View.INVISIBLE
            listener.onFragmentSetDataset(dataset)
            // insertFragment(Integer.parseInt(findViewById<EditText>(R.id.numReps).text.toString()), Integer.parseInt(findViewById<EditText>(R.id.quota).text.toString()), Integer.parseInt(findViewById<EditText>(R.id.iterations).text.toString()))
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun calculatePoints(reps: Int, quota: Int, iterations: Int) : DoubleArray {
        val numCandidates: Int = reps
        val quota: Int = quota

        val grid: MutableMap<Double, CoordinateWithPoints?> =
            LinkedHashMap(numCandidates*numCandidates)
        var averageTotalPivotalsPerIteration =
            0.0 // [total pivotals] = averageTotalPivotalsPerIteration * [number of iterations]
        // val start = System.currentTimeMillis()
        val selected: MutableList<CoordinateWithPoints> =
            ArrayList(numCandidates)
        var numIterations = iterations
        while (numIterations >= 0) {
            numIterations -= 1
            run {
                var i = 0
                while (i < numCandidates) {
                    /*double randomX = normalRandomDoubleGenerator(mean, standardDeviation);
                        double randomY = normalRandomDoubleGenerator(mean, standardDeviation);*/
                    val randomX: Double = uniformRandomDoubleGenerator(0.0, 1.0)
                    val randomY: Double = uniformRandomDoubleGenerator(0.0, 1.0)
                    selected.add(CoordinateWithPoints(randomX, randomY))
                    i += 1
                }
            }
            val sortedByY: List<CoordinateWithPoints> =
                ArrayList(selected)
            RecCounter.sortByX(selected)
            RecCounter.sortByY(sortedByY)
            try {
                val iterationTotalPivotals = RecCounter.count(
                    selected,
                    true,
                    quota
                ) // adds points to those on the edge of non-reducible rectangles: (NOTE) for example, if a non-reducible rectangle has eight on the edge, two on each of the four sides, points will be given even if the rectangle quota is one over the acceptable quota
                averageTotalPivotalsPerIteration =
                    averageTotalPivotalsPerIteration - averageTotalPivotalsPerIteration / numIterations + iterationTotalPivotals / numIterations // (averageTotalPivotalsPerIteration * (numIterations - 1) + iterationTotalPivotals) / numIterations;
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            // map selected coordinates to grid
//
            val mockGrid: MutableMap<CoordinateWithPoints, Int> =
                HashMap(numCandidates)
            run {
                var i = 0
                while (i < selected.size) {
                    val key = selected[i]
                    mockGrid[key] = i
                    i += 1
                }
            }
            var i = 0
            while (i < sortedByY.size) {
                val x = mockGrid[sortedByY[i]]!!
                val y = i
                val points = sortedByY[i].points
                val key: Double = generateGridIntegerKey(x, y, numCandidates)
                if (!grid.containsKey(key)) {
                    grid[key] = CoordinateWithPoints(x.toDouble(), y.toDouble(), points)
                } else {
                    grid[key]!!.incrementPoints(points)
                }
                i += 1
            }
            //
            // setInput(setValueForChart(grid, numIterations, averageTotalPivotalsPerIteration));
            selected.clear()
        }
        // val end = System.currentTimeMillis()

        // val arrayLength: Int = grid.size
        // val xs = DoubleArray(arrayLength)
        // val ys = DoubleArray(arrayLength)
        // val points = DoubleArray(arrayLength)
        val returns = DoubleArray(reps)

        var index = 0
        for ((_, value) in grid)  // update Banzhaf indices all coordinates in the grid, and update dataset
        {
            val coordinateTotalPivotals = value?.points
            if (coordinateTotalPivotals != null) {
                value?.value = coordinateTotalPivotals.toDouble() / numIterations / averageTotalPivotalsPerIteration
            }
            /*
            if (value != null) {
                xs[index] = value.x
            }
            if (value != null) {
                ys[index] = value.y
            }

             */
            if (value != null) {
                // points[index] = value.value
                returns[value.x.toInt()] = returns[value.x.toInt()].plus(value.value)
            }
            index += 1
        }

        var i = 0
        while (i < returns.size) {
            returns[i] = returns[i] / returns.size
            i += 1
        }

        return returns
    }

    private fun uniformRandomDoubleGenerator(
        min: Double,
        max: Double
    ): Double {
        val random = Random()
        return min + (max - min) * random.nextDouble()
    }

    private fun generateGridIntegerKey(x: Int, y: Int, gridLength: Int): Double {
        return (x + y * gridLength).toDouble()
    }

    companion object {

        @JvmStatic
        fun newInstance() : InputFragment{
            return InputFragment()
        }

    }
}