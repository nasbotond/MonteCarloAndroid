package com.kotlinforandroiddevelopment.montecarloandroid

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rectangle_code.CoordinateWithPoints
import rectangle_code.RecCounter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class MCViewModel : ViewModel() {

    suspend fun calculatePoints(reps: Int, quota: Int, iterations: Int) : DoubleArray {

        // progressBar.max = reps * iterations // TODO
        // progressBar.progress = 0

        return withContext(Dispatchers.Default){

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
                    // progressBar.progress += 1
                }
                //
                // setInput(setValueForChart(grid, numIterations, averageTotalPivotalsPerIteration));
                selected.clear()
            }
            // val end = System.currentTimeMillis()

            val returns = DoubleArray(reps)

            var index = 0
            for ((_, value) in grid)  // update Banzhaf indices all coordinates in the grid, and update dataset
            {
                val coordinateTotalPivotals = value?.points
                if (coordinateTotalPivotals != null) {
                    value.value = coordinateTotalPivotals.toDouble() / numIterations / averageTotalPivotalsPerIteration
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
            return@withContext returns

        }

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
}