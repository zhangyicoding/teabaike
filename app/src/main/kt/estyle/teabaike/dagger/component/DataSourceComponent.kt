package estyle.teabaike.dagger.component

import dagger.Component
import estyle.teabaike.dagger.module.DataSourceModule
import estyle.teabaike.viewmodel.ContentViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class])
interface DataSourceComponent {

//    fun inject(dataSource: MainListDataSource)
//    fun inject(viewModel: MainViewModel)

    fun inject(viewModel: ContentViewModel)

//    fun inject(dataSource: SearchListDataSource)

//    fun inject(viewModel: CollectionViewModel)

//    fun inject(activity: SplashActivity)

//    fun inject(viewModel: FeedbackViewModel)
}
