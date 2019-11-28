package com.kotlinforandroiddevelopment.montecarloandroid

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import rectangle_code.CoordinateWithPoints
import rectangle_code.RecCounter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChartFragment : Fragment() {
    var line: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chart, container, false)

        line = view.findViewById<View>(R.id.line) as LineChart

        val numReps = arguments!!.getInt(ARG_REPS)
        val quota = arguments!!.getInt(ARG_QUOTA)
        val iterations = arguments!!.getInt(ARG_ITERATIONS)

        val r = calculatePoints(numReps, quota, iterations) // array of doubles with the values in it

        val yVals = ArrayList<Entry>()
        var i = 0
        while (i < r.size) {
            yVals.add(Entry(i.toFloat(), r[i].toFloat(), i.toString()))
            i += 1
        }
        /*
        yVals.add(Entry(0f, 30f, "0"))
        yVals.add(Entry(1f, 2f, "1"))
        yVals.add(Entry(2f, 4f, "2"))
        yVals.add(Entry(3f, 6f, "3"))
        yVals.add(Entry(4f, 8f, "4"))
        yVals.add(Entry(5f, 10f, "5"))
        yVals.add(Entry(6f, 22f, "6"))
        yVals.add(Entry(7f, 12.5f, "7"))
        yVals.add(Entry(8f, 22f, "8"))
        yVals.add(Entry(9f, 32f, "9"))
        yVals.add(Entry(10f, 54f, "10"))
        yVals.add(Entry(11f, 28f, "11"))
         */

        val set1: LineDataSet
        set1 = LineDataSet(yVals, "DataSet 1")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data
        line!!.data = data
        line!!.description.isEnabled = false
        line!!.legend.isEnabled = false
        line!!.setPinchZoom(true)
        line!!.xAxis.enableGridDashedLine(5f, 5f, 0f)
        line!!.axisRight.enableGridDashedLine(5f, 5f, 0f)
        line!!.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
        line!!.xAxis.labelCount = 11
        line!!.xAxis.position = XAxis.XAxisPosition.BOTTOM

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
        const val ARG_REPS = "numReps"
        const val ARG_QUOTA = "quota"
        const val ARG_ITERATIONS = "iterations"
        // TODO: Rename and change types and number of parameters
        fun newInstance(numReps: Int, quota : Int, iterations : Int): ChartFragment {
            val fragment = ChartFragment()
            val bundle = Bundle().apply {
                putInt(ARG_REPS, numReps)
                putInt(ARG_QUOTA, quota)
                putInt(ARG_ITERATIONS, iterations)
            }

            fragment.arguments = bundle

            return fragment
        }
    }
}