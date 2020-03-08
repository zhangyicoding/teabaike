package com.estyle.httpmock;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HttpMockInterceptor implements Interceptor {

    public HttpMockInterceptor(Context context, boolean enable, Class<HttpMockGenerator> clazz) {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
