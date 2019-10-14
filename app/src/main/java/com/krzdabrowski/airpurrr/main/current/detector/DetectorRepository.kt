package com.krzdabrowski.airpurrr.main.current.detector

import android.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class DetectorRepository(private val serviceHttp: DetectorDataService, private val serviceHttps: DetectorControlService) {
    suspend fun fetchData(): DetectorModel? {
        try {
            val response = serviceHttp.getDetectorDataAsync()
            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            } else {
                Timber.e("DetectorModel error: ${response.code()}")
            }
        } catch (e: HttpException) {
            Timber.d("HTTP error: ${e.message()}")
        } catch (e: Throwable) {
            Timber.d("HTTP common error: ${e.message}")
        }
        return null
    }

    fun controlFan(turnOn: Boolean, email: String, password: String) {
        val auth = "Basic " + Base64.encodeToString("$email:$password".toByteArray(), Base64.NO_WRAP)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (turnOn) {
                    serviceHttps.controlTurningFanOnOffAsync(auth, "on")
                } else {
                    serviceHttps.controlTurningFanOnOffAsync(auth, "off")
                }
            } catch (e: HttpException) {
                Timber.d("HTTPS error: ${e.message()}")
            } catch (e: Throwable) {
                Timber.d("HTTPS common error: ${e.message}")
            }
        }
    }
}