package com.estyle.httpmock

import android.content.Context

import com.estyle.httpmock.common.AbstractHttpMockGenerator
import com.estyle.httpmock.common.MockEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import okhttp3.OkHttpClient

object HttpMock {

    @JvmStatic
    fun addHttpMockInterceptor(
        builder: OkHttpClient.Builder,
        context: Context,
        enable: Boolean,
        delayMillis: Long,
        generatorClass: Class<out AbstractHttpMockGenerator>
    ): OkHttpClient.Builder {
        var generator: AbstractHttpMockGenerator? = null
        try {
            generator = generatorClass.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val json = generator!!.jsonString
        val type = object : TypeToken<List<MockEntity>>(){}.type
        val mockList = Gson().fromJson<List<MockEntity>>(json, type)
        return builder.addInterceptor(HttpMockInterceptor(context, mockList, enable, delayMillis))
    }
}

// for kotlin
fun OkHttpClient.Builder.addHttpMockInterceptor(
    context: Context,
    enable: Boolean,
    delayMillis: Long,
    generatorClass: Class<out AbstractHttpMockGenerator>
): OkHttpClient.Builder {
    var generator: AbstractHttpMockGenerator? = null
    try {
        generator = generatorClass.newInstance()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val json = generator!!.jsonString
    val type = object : TypeToken<List<MockEntity>>(){}.type
    val mockList = Gson().fromJson<List<MockEntity>>(json, type)
    return this.addInterceptor(HttpMockInterceptor(context, mockList, enable, delayMillis))
}