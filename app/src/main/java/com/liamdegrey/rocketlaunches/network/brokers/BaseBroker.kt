package com.liamdegrey.rocketlaunches.network.brokers

import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

abstract class BaseBroker<Service>(
    baseUrl: String,
    httpClient: OkHttpClient,
    objectMapper: ObjectMapper,
    serviceClass: Class<Service>
) {
    protected val service: Service

    init {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
        service = retrofit.create(serviceClass)
    }
}