package estyle.teabaike.datasource.http.service

import com.estyle.httpmock.annotation.HttpMock
import estyle.base.entity.HttpEntity
import estyle.teabaike.config.Url
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FeedbackService {

    @HttpMock(fileName = "feedback.json")
    @FormUrlEncoded
    @POST(Url.FEEDBACK_URL)
    fun postFeedback(
        @Field(Url.FEEDBACK_TITLE) title: String,
        @Field(Url.FEEDBACK_CONTENT) content: String
    ): Observable<HttpEntity<String>>
}
