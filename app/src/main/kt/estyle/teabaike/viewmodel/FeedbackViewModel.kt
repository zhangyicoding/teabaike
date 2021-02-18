package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.FeedbackDataSource

class FeedbackViewModel(application: Application) : BaseViewModel(application) {

    //    @Inject
    lateinit var dataSource: FeedbackDataSource

    init {
        dataSource = FeedbackDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    /**
     * 意见反馈
     */
    fun feedback(title: String, content: String) = dataSource.feedback(title, content)
}