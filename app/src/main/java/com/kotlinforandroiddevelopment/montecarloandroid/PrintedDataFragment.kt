package com.kotlinforandroiddevelopment.montecarloandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class PrintedDataFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_printed_data, container, false)

        val r = arguments!!.getDoubleArray(ARG_DATASET)

        val strings = datasetToStrings(r!!)

        val adapter = ArrayAdapter<String>(this.context!!, android.R.layout.simple_list_item_1, strings)

        val listView: ListView = view.findViewById(R.id.list)

        listView.adapter = adapter

        return view
    }

    private fun datasetToStrings(dataset: DoubleArray) : Array<String>
    {
        return Array(dataset!!.size) { i -> "X: " + i.toString() + " Y: " + dataset[i].toString()}
    }

    companion object {

        const val ARG_DATASET = "dataset"
        @JvmStatic
        fun newInstance(dataset : DoubleArray) : PrintedDataFragment{
            val fragment = PrintedDataFragment()
            val bundle = Bundle().apply {

                putDoubleArray(ARG_DATASET, dataset)
            }

            fragment.arguments = bundle

            return fragment
        }

    }
}
