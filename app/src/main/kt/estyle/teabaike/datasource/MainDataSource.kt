package estyle.teabaike.datasource

import estyle.teabaike.api.NetApi
import estyle.teabaike.rxjava.SchedulersTransformer

class MainDataSource {

    fun loadHeadline() =
        NetApi.mainService()
            .getHeadline()
            .map { it.data }
            .compose(SchedulersTransformer())
}