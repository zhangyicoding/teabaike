package estyle.base

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import estyle.base.rxjava.BaseObserver
import io.reactivex.Observable
import java.util.*

abstract class BasePageKeyedDataSource<Value> : PageKeyedDataSource<Int, Value>() {
    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, Value>
    ) {
        loadData(1, callback, null)
    }

    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Value>
    ) {
    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Value>
    ) {
        loadData(params.key, null, callback)
    }

    private fun loadData(
        page: Int,
        initialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Value>?,
        afterCallback: PageKeyedDataSource.LoadCallback<Int, Value>?
    ) {
        // todo 需要自动管理生命周期
        val subscribe = getObservable(page)
            .subscribe(object : BaseObserver<List<Value>>() {
                override fun onNext(it: List<Value>) {
                    super.onNext(it)
                    initialCallback?.onResult(it, null, 2)
                    afterCallback?.onResult(it, page + 1)
                }
            })


    }

    protected abstract fun getObservable(page: Int): Observable<List<Value>>

    abstract class Factory<Value> : DataSource.Factory<Int, Value>()
}
