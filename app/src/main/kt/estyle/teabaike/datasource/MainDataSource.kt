package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.http.HttpManager
import estyle.teabaike.datasource.http.service.MainService

class MainDataSource {

    /**
     * 检查版本
     */
    fun checkVersion() =
        HttpManager.service(MainService::class.java)
            .getLatestVersion()
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())

    /**
     *  加载头条数据
     */
    fun loadHeadline() =
        HttpManager.service(MainService::class.java)
            .getHeadline()
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())

    /**
     * 加载列表
     */
    fun loadList(type: String, page: Int) =
        HttpManager.service(MainService::class.java)
            .getList(type, page)
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())
}