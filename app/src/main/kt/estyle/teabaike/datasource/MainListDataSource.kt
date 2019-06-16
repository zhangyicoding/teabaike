package estyle.teabaike.datasource

import android.text.TextUtils
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi
import estyle.teabaike.config.Url
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class MainListDataSource {

    fun loadHeadline() =
        NetApi.mainService()
            .getHeadline()
            .doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())

    fun loadList(type: String, page: Int): Observable<List<MainEntity.DataEntity>> {
        val service = NetApi.mainService()
        val observable = if (TextUtils.equals(type, Url.TITLES[0])) {
            service.getHeadlineList(page)
        } else {
            service.getList(type, page)
        }
        return observable.doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
    }
}
