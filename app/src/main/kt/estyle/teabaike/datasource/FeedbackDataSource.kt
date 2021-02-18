package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.http.HttpManager
import estyle.teabaike.datasource.http.service.FeedbackService

class FeedbackDataSource {

    /**
     * 意见反馈
     */
    fun feedback(title: String, content: String) =
        HttpManager.service(FeedbackService::class.java)
            .postFeedback(title, content)
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())
}