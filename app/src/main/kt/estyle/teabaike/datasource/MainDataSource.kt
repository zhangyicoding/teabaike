package estyle.teabaike.datasource

import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi

class MainDataSource {

    fun loadHeadline() =
        NetApi.mainService()
            .getHeadline()
            .map { it.data }
            .compose(SchedulersTransformer())
}