package estyle.teabaike.api.net

import estyle.teabaike.config.Url
import estyle.teabaike.entity.FeedbackEntity
import io.reactivex.Observable
import retrofit2.http.POST

interface FeedbackService {

    // TODO post的写法
    @POST(Url.FEEDBACK_URL)
    fun postFeedback(title: String, content: String): Observable<FeedbackEntity>
}
