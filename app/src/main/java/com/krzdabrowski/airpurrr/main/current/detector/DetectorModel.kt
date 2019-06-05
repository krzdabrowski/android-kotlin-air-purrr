package com.krzdabrowski.airpurrr.main.current.detector

import android.content.Context
import com.krzdabrowski.airpurrr.R
import com.krzdabrowski.airpurrr.common.Conversion
import com.krzdabrowski.airpurrr.main.current.BaseModel

data class DetectorModel(val workstate: String, val values: Values?) : BaseModel() {
    data class Values(val pm25: Double, val pm10: Double)

    override fun getDataPercentage(context: Context, type: String): String {
        if (values != null) {
            if (type == "PM2.5") {
                return context.getString(R.string.main_data_percentage, Conversion.pm25ToPercent(values.pm25))
            } else if (type == "PM10") {
                return context.getString(R.string.main_data_percentage, Conversion.pm10ToPercent(values.pm10))
            }
        }
        return ""
    }
}