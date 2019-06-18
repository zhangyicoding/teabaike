package com.estyle.httpmock

import android.content.Context
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addHttpMockInterceptor(
    context: Context,
    enable: Boolean,
    generatorClass: Class<HttpMockGenerator>
) = this