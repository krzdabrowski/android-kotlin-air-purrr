package com.krzdabrowski.airpurrr.core

import com.krzdabrowski.airpurrr.BuildConfig
import com.krzdabrowski.airpurrr.login.LoginViewModel
import com.krzdabrowski.airpurrr.main.BaseViewModel
import com.krzdabrowski.airpurrr.main.api.ApiRepository
import com.krzdabrowski.airpurrr.main.api.ApiService
import com.krzdabrowski.airpurrr.main.api.ApiViewModel
import com.krzdabrowski.airpurrr.main.detector.DetectorRepository
import com.krzdabrowski.airpurrr.main.detector.DetectorViewModel
import okhttp3.OkHttpClient
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { ApiService.create(provideOkHttpClient(30)) }
    single { provideMqttClient() }
}

val repositoryModule = module {
    single { DetectorRepository(get()) }
    single { ApiRepository(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel() }
    viewModel { BaseViewModel() }
    viewModel { DetectorViewModel(get()) }
    viewModel { ApiViewModel(get()) }
}

private fun provideOkHttpClient(timeout: Long): OkHttpClient {
    return OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()
}

private fun provideMqttClient(): MqttAsyncClient {
    return MqttAsyncClient(BuildConfig.BASE_MOSQUITTO_URL, "android", MemoryPersistence())
}