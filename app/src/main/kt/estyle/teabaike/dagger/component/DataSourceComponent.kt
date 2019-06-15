package estyle.teabaike.dagger.component

import dagger.Component
import estyle.teabaike.dagger.module.DataSourceModule
import estyle.teabaike.viewmodel.*
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class])
interface DataSourceComponent {

//    fun inject(dataSource: MainListDataSource)
//    fun inject(dataSource: SearchListDataSource)

    fun inject(viewModel: MainViewModel)

    fun inject(viewModel: MainListViewModel)

    fun inject(viewModel: ContentViewModel)

    fun inject(viewModel: CollectionViewModel)

    fun inject(viewModel: FeedbackViewModel)
}
