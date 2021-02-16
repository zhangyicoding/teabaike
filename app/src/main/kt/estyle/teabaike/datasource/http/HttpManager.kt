package estyle.teabaike.datasource.http

import android.content.Context
import com.estyle.httpmock.HttpMockInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import estyle.teabaike.config.Url
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpManager {

    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        val client = OkHttpClient.Builder()
//            .cache(Cache(File(context.externalCacheDir, "http_cache"), 10 * 1024 * 1024))
            .addInterceptor(ChuckInterceptor(context))
            .addInterceptor(HttpMockInterceptor(context, true, HttpMockGenerator::class.java))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     * 获取http请求接口
     */
    fun <T> service(service: Class<T>): T = retrofit.create(service)
}