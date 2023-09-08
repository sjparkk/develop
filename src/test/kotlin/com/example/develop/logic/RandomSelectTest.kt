package com.example.develop.logic

import com.example.develop.utils.enums.RandomTypeEnum
import com.example.develop.utils.getRandomByWeightsPercentage
import org.junit.jupiter.api.Test

class RandomSelectTest {

    @Test
    fun `타입별 가중치를 통한 뽑기`() {

        var x = 0f
        var y = 0f
        var z = 0f
        var a = 0f
        var b = 0f

        for (i in 1..100) {
            when(getRandomByWeightsPercentage<RandomTypeEnum>(RandomTypeEnum.values().map { it.weightPercentage })) {
                RandomTypeEnum.TARGET_A.name -> x++
                RandomTypeEnum.TARGET_B.name -> y++
                RandomTypeEnum.TARGET_C.name -> z++
                RandomTypeEnum.TARGET_D.name -> a++
                RandomTypeEnum.TARGET_E.name -> b++
            }
        }

        val xAvg = x.div(x+y+z+a+b).times(100).toInt()
        val yAvg = y.div(x+y+z+a+b).times(100).toInt()
        val zAvg = z.div(x+y+z+a+b).times(100).toInt()
        val aAvg = a.div(x+y+z+a+b).times(100).toInt()
        val bAvg = b.div(x+y+z+a+b).times(100).toInt()

        println("$xAvg , $yAvg, $zAvg, $aAvg, $bAvg")

    }
}