package estyle.teabaike.api

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import estyle.teabaike.api.net.ContentService
import estyle.teabaike.api.net.FeedbackService
import estyle.teabaike.api.net.MainService
import estyle.teabaike.api.net.SearchService
import estyle.teabaike.config.Url
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object NetApi {

    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        val client = OkHttpClient.Builder()
            .cache(Cache(File(context.externalCacheDir, "http_cache"), 10 * 1024 * 1024))
            .addInterceptor(ChuckInterceptor(context))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun contentService() = retrofit.create(ContentService::class.java)
    fun feedbackService() = retrofit.create(FeedbackService::class.java)
    fun mainService() = retrofit.create(MainService::class.java)
    fun searchService() = retrofit.create(SearchService::class.java)
}
