package com.example.develop.utils.enums

enum class RandomTypeEnum(
    val target: String,
    val weightPercentage: Double
) {
    TARGET_A("A", 10.0),
    TARGET_B("B", 0.0),
    TARGET_C("C", 50.0),
    TARGET_D("D", 20.0),
    TARGET_E("E", 20.0);
}