package estyle.teabaike.util

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object HttpUtil {

    /**
     * 创建上传文件的请求体
     */
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
