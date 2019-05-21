package estyle.teabaike.dagger.module

import dagger.Module
import dagger.Provides
import estyle.teabaike.datasource.ContentDataSource
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideContentDataSource(): ContentDataSource {
        return ContentDataSource()
    }
}
