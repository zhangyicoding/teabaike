package estyle.teabaike.viewmodel

import android.app.Application
import estyle.teabaike.datasource.FeedbackDataSource

class FeedbackViewModel(application: Application) : BaseViewModel(application) {

    private val dataSource by lazy { FeedbackDataSource() }

    fun feedback(title: String, content: String) = dataSource.feedback(title, content)
}