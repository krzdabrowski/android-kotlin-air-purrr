package com.krzdabrowski.airpurrr.main.detector

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import com.krzdabrowski.airpurrr.main.BaseViewModel
import com.krzdabrowski.airpurrr.main.helper.ConversionHelper

class DetectorViewModel(private val repository: DetectorRepository) : BaseViewModel() {
    private lateinit var liveData: LiveData<DetectorCurrentModel>
    val autoModeSwitch = ObservableBoolean()
    val autoModeThreshold = ObservableInt()
    val purifierOnOffObservableState = ObservableBoolean()
    var purifierOnOffState = purifierOnOffObservableState.get()
    val purifierHighLowObservableState = ObservableBoolean()

    fun getLiveData(): LiveData<DetectorCurrentModel> {
        liveData = repository.fetchData()
        return liveData
    }

    fun controlFanOnOff(shouldTurnOn: Boolean) = repository.controlFanOnOff(shouldTurnOn)

    fun controlFanHighLow(shouldSwitchToHigh: Boolean) = repository.controlFanHighLow(shouldSwitchToHigh)

    fun checkAutoMode() {
        val data = liveData.value ?: return

        val shouldTurnOn = !purifierOnOffState && autoModeSwitch.get()
                && (autoModeThreshold.get() < ConversionHelper.pm25ToPercent(data.values.pm25)
                || autoModeThreshold.get() < ConversionHelper.pm10ToPercent(data.values.pm10))
        val shouldTurnOff = purifierOnOffState && !autoModeSwitch.get()

        if (shouldTurnOn || shouldTurnOff) {
            purifierOnOffObservableState.set(!purifierOnOffState)
        }
    }

    fun checkPerformanceMode(shouldSwitchToHigh: Boolean) {
        if (purifierOnOffState) {
            controlFanHighLow(shouldSwitchToHigh)
        }
    }

    fun disconnectMqttClient() {
        repository.disconnectMqttClient()
    }
}