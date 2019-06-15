package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.FeedbackDataSource
import estyle.teabaike.util.InjectUtil
import javax.inject.Inject

class FeedbackViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var dataSource: FeedbackDataSource

    init {
        InjectUtil.dataSourceComponent()
            .inject(this)
    }

    fun feedback(title: String, content: String) = dataSource.feedback(title, content)
}