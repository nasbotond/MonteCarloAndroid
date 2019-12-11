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

        val r = arguments!!.getDoubleArray(ARG_DATASET)

        // val r = calculatePoints(numReps, quota, iterations) // array of doubles with the values in it

        val yVals = ArrayList<Entry>()
        var i = 0
        if (r != null) {
            while (i < r.size) {
                yVals.add(Entry(i.toFloat(), r[i].toFloat(), i.toString()))
                i += 1
            }
        }

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

    companion object {

        const val ARG_DATASET = "dataset"
        // TODO: Rename and change types and number of parameters
        fun newInstance(dataset : DoubleArray): ChartFragment {
            val fragment = ChartFragment()
            val bundle = Bundle().apply {
                // putInt(ARG_REPS, numReps)
                // putInt(ARG_QUOTA, quota)
                // putInt(ARG_ITERATIONS, iterations)
                putDoubleArray(ARG_DATASET, dataset)
            }

            fragment.arguments = bundle

            return fragment
        }
    }
}