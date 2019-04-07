package com.example.trubul.airpurrr.model

data class Detector(val workstate: String, val values: Values) : BaseModel() {
    data class Values(val pm25: Double, val pm10: Double)
}