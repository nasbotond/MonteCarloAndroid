package com.kotlinforandroiddevelopment.montecarloandroid

import org.junit.Test

import org.junit.Assert.*

class ViewModelUnitTest
{

    var model :MCViewModel = MCViewModel()

    @Test
    fun addition_isCorrect()
    {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun randomNumberGeneratorReturnsCorrectValue()
    {
        assertTrue(model.uniformRandomDoubleGenerator(1.0, 2.0) < 2.0 && model.uniformRandomDoubleGenerator(1.0, 2.0) > 1.0)
    }

    @Test
    fun randomNumberGeneratorReturnsValue()
    {
        assertNotNull(model.uniformRandomDoubleGenerator(1.0, 2.0))
    }

    /*
    @Test
    suspend fun calculatePointsReturnsValue()
    {
        run{ assertNotNull(model.calculatePoints(60, 31, 1 )) }
    }

    @Test
    suspend fun calculatePointsReturnsNotEmptyArray()
    {
        run{assertTrue(model.calculatePoints(60, 31, 1).isNotEmpty())}
    }
    */


    @Test
    fun generateGridIntegerKeyReturnsAValue()
    {
        assertNotNull(model.generateGridIntegerKey(1,1,60))
    }

    @Test
    fun generateGridIntegerKeyReturnsCorrectValue()
    {
        assertEquals("correct: ", model.generateGridIntegerKey(1,1,60), 61.0, 0.0)
    }


}
