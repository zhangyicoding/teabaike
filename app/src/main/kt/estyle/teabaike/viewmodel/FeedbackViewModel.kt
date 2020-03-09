package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.net.FeedbackService
import estyle.teabaike.util.NetworkUtil

class FeedbackViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: FeedbackDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun feedback(title: String, content: String) = NetworkUtil.service(FeedbackService::class.java)
        .postFeedback(title, content)
        .doOnNext(ErrorMessageConsumer())
        .compose(SchedulersTransformer())
}