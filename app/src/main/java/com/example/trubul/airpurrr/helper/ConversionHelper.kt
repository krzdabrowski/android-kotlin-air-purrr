package com.example.trubul.airpurrr.helper

object ConversionHelper {
    fun pm25ToPercent(pm25: Double?): Double {
        return pm25?.times(4) ?: 0.0
    }

    fun pm10ToPercent(pm10: Double?): Double {
        return pm10?.times(2) ?: 0.0
    }
}