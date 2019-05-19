package estyle.teabaike.dagger.module

import dagger.Module
import dagger.Provides
import estyle.teabaike.datasource.ContentDataSource

@Module
class DataSourceModule {

    @Provides
    fun provideContentDataSource(): ContentDataSource {
        return ContentDataSource()
    }
}
