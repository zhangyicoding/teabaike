package estyle.teabaike.dagger.component

import dagger.Component
import estyle.teabaike.dagger.module.DataModule
import estyle.teabaike.viewmodel.CollectionViewModel
import estyle.teabaike.viewmodel.ContentViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface TeaBaikeComponent {

//    fun inject(dataSource: MainListDataSource)
//    fun inject(viewModel: MainViewModel)

    fun inject(viewModel: ContentViewModel)

//    fun inject(dataSource: SearchListDataSource)

    fun inject(viewModel: CollectionViewModel)

//    fun inject(activity: SplashActivity)

//    fun inject(viewModel: FeedbackViewModel)
}
