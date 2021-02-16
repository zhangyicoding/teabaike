package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.http.service.FeedbackService

class FeedbackViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: FeedbackDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun feedback(title: String, content: String) = HttpManager.service(FeedbackService::class.java)
        .postFeedback(title, content)
        .map(ErrorCodeFunction())
        .compose(SchedulersTransformer())
}