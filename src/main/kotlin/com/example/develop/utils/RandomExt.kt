package com.example.develop.utils

import kotlin.math.ln
import kotlin.random.Random

/**
 * 가중치를 통한 랜덤 필드
 */
inline fun <reified T : Enum<T>> getRandomByWeightsPercentage(weights: List<Double>): String {

    val w = enumValues<T>().filter { weights[it.ordinal] != 0.0 }.associate {
        it.name to weights[it.ordinal]
    }

    var result = ""
    var bestValue = Double.MAX_VALUE

    for (element in w.keys) {
        val value = -ln(Random.nextDouble()) / w[element]!!
        if (value < bestValue) {
            bestValue = value
            result = element
        }
    }

    return result
}