package com.krzdabrowski.airpurrr.utils

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.krzdabrowski.airpurrr.R
import com.krzdabrowski.airpurrr.helper.Conversion
import com.krzdabrowski.airpurrr.model.ApiModel
import com.krzdabrowski.airpurrr.model.BaseModel
import com.krzdabrowski.airpurrr.model.DetectorModel

@BindingAdapter(value = ["app:source", "app:date"])
fun TextView.bindDate(source: Boolean, data: BaseModel?) {
    if (source) {
        if (data == null) {
            text = resources.getString(R.string.main_data_info_api_empty)
        } else if (data is ApiModel){
            text = resources.getString(R.string.main_data_info_api)
        }
    } else {
        text = resources.getString(R.string.main_data_info_indoors)
    }
}

@BindingAdapter(value = ["app:type", "app:dataPercentage"])
fun TextView.bindDataPercentage(type: String, data: BaseModel?) {
    text = resources.getString(R.string.main_data_percentage, 0.0)

    if (data is DetectorModel) {
        if (data.values != null) {
            if (type == "PM2.5") {
                text = resources.getString(R.string.main_data_percentage, Conversion.pm25ToPercent(data.values.pm25))
            } else if (type == "PM10") {
                text = resources.getString(R.string.main_data_percentage, Conversion.pm10ToPercent(data.values.pm10))
            }
        }
    } else if (data is ApiModel) {
        if (type == "PM2.5") {
            text = resources.getString(R.string.main_data_percentage, Conversion.pm25ToPercent(data.data.first))
        } else if (type == "PM10") {
            text = resources.getString(R.string.main_data_percentage, Conversion.pm10ToPercent(data.data.second))
        }
    }
}

@BindingAdapter(value = ["app:type", "app:dataUgm3"])
fun TextView.bindDataUgm3(type: String, data: BaseModel?) {
    text = resources.getString(R.string.main_data_ugm3, 0.0)

    if (data is DetectorModel) {
        if (data.values != null) {
            if (type == "PM2.5") {
                text = resources.getString(R.string.main_data_ugm3, data.values.pm25)
            } else if (type == "PM10") {
                text = resources.getString(R.string.main_data_ugm3, data.values.pm10)
            }
        }
    } else if (data is ApiModel) {
        if (type == "PM2.5") {
            text = resources.getString(R.string.main_data_ugm3, data.data.first)
        } else if (type == "PM10") {
            text = resources.getString(R.string.main_data_ugm3, data.data.second)
        }
    }
}

@BindingAdapter(value = ["app:type", "app:dataColor"])
fun ConstraintLayout.bindBackgroundColor(type: String, data: BaseModel?) {
    var valuePerc = 0.0

    if (data is DetectorModel) {
        val values = data.values
        if (type == "PM2.5") {
            valuePerc = Conversion.pm25ToPercent(values?.pm25)
        } else if (type == "PM10"){
            valuePerc = Conversion.pm10ToPercent(values?.pm10)
        }
    } else if (data is ApiModel) {
        val values = data.data
        if (type == "PM2.5") {
            valuePerc = Conversion.pm25ToPercent(values.first)
        } else if (type == "PM10") {
            valuePerc = Conversion.pm10ToPercent(values.second)
        }
    }

    if (valuePerc == 0.0) {
        setBackgroundResource(R.drawable.data_unavailable)
    } else if (valuePerc > 0 && valuePerc <= 50) {
        setBackgroundResource(R.drawable.data_green)
    } else if (valuePerc > 50 && valuePerc <= 100) {
        setBackgroundResource(R.drawable.data_lime)
    } else if (valuePerc > 100 && valuePerc <= 200) {
        setBackgroundResource(R.drawable.data_yellow)
    } else {
        setBackgroundResource(R.drawable.data_red)
    }
}