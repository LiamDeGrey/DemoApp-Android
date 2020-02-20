package com.liamdegrey.demoapp

import android.app.Application
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.liamdegrey.demoapp.network.brokers.DataBroker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class App : Application() {
    val dataBroker by lazy { DataBroker(BuildConfig.SERVICE_BASEURL, okHttpClient, objectMapper) }

    private val okHttpClient by lazy { createOkHttpClient() }
    private val objectMapper by lazy { createObjectMapper() }


    private fun createOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    )
            )
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    private fun createObjectMapper() =
        ObjectMapper().apply {
            registerModule(SimpleModule())
            registerModule(KotlinModule())
            registerModule(JodaModule())
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
        }

    companion object {
        private const val NETWORK_TIMEOUT_SECONDS = 10L
    }
}