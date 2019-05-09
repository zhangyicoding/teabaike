package estyle.teabaike.datasource

import estyle.teabaike.api.NetApi
import estyle.teabaike.rxjava.SchedulersTransformer

class FeedbackDataSource {

    fun feedback(title: String, content: String) =
            NetApi.feedbackService()
            .postFeedback(title, content)
            .compose(SchedulersTransformer());
}