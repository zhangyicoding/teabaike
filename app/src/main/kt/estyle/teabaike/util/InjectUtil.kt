package estyle.teabaike.util

import estyle.teabaike.dagger.component.DaggerDataSourceComponent
import estyle.teabaike.dagger.module.DataSourceModule

object InjectUtil {

    fun dataSourceComponent() =
        DaggerDataSourceComponent.builder()
            .dataSourceModule(DataSourceModule())
            .build()
}