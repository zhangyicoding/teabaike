package estyle.teabaike.util

import android.content.Context
import com.estyle.httpmock.HttpMockGenerator
import com.estyle.httpmock.HttpMockInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import estyle.teabaike.config.Url
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object NetworkUtil {

    private lateinit var retrofit: Retrofit
//
    fun init(context: Context, baseUrl: String) {
        val client = OkHttpClient.Builder()
            .cache(Cache(File(context.externalCacheDir, "http_cache"), 10 * 1024 * 1024))
            .addInterceptor(HttpMockInterceptor(context, true, HttpMockGenerator::class.java))
            .addInterceptor(ChuckInterceptor(context))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    // 获取请求接口
    fun <T> service(service: Class<T>) = retrofit.create(service)

    // 创建上传文件的请求体
    fun createFileBody(filePath: String, params: Map<String, Any> = hashMapOf()): MultipartBody {
        val file = File(filePath)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Builder().apply {
            setType(MultipartBody.FORM)
            addFormDataPart("file", file.name, requestBody)
            for ((key, value) in params) {
                addFormDataPart(key, value.toString())
            }
        }.build()
    }
}
