package estyle.teabaike.datasource

import android.content.Context
import com.estyle.httpmock.HttpMockGenerator
import com.estyle.httpmock.addHttpMockInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import estyle.teabaike.datasource.net.ContentService
import estyle.teabaike.datasource.net.FeedbackService
import estyle.teabaike.datasource.net.MainService
import estyle.teabaike.datasource.net.SearchService
import estyle.teabaike.config.Url
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object NetDataSource {

    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        val client = OkHttpClient.Builder()
            .cache(Cache(File(context.externalCacheDir, "http_cache"), 10 * 1024 * 1024))
            .addInterceptor(ChuckInterceptor(context))
            .addHttpMockInterceptor(context, true, HttpMockGenerator::class.java)
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
