package estyle.teabaike.datasource

import android.text.TextUtils
import androidx.paging.DataSource
import estyle.base.BasePageKeyedDataSource
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi
import estyle.teabaike.config.Url
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class MainListDataSource(private val type: String) :
    BasePageKeyedDataSource<MainEntity.DataEntity>() {

    override fun getObservable(page: Int): Observable<List<MainEntity.DataEntity>> {
        val service = NetApi.mainService()
        val observable = if (TextUtils.equals(type, Url.TITLES[0])) {
            service.getMainHeadline(page)
        } else {
            service.getMain(type, page)
        }
        return observable.doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
    }

    class Factory(val type: String) : BasePageKeyedDataSource.Factory<MainEntity.DataEntity>() {
        override fun create(): DataSource<Int, MainEntity.DataEntity> =
            MainListDataSource(type)
    }

    fun loadHeadline() =
        NetApi.mainService()
            .getHeadline()
            .doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
}
