package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi

class FeedbackDataSource {

    fun feedback(title: String, content: String) =
        NetApi.feedbackService()
            .postFeedback(title, content)
            .doOnNext(ErrorMessageConsumer())
            .compose(SchedulersTransformer());
}