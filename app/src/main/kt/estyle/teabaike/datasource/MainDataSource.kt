package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi

class MainDataSource {

    fun checkVersion() =
        NetApi.mainService()
            .getLatestVersion()
            .doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
}