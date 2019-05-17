package estyle.teabaike.datasource

import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.DatabaseApi
import estyle.teabaike.entity.ContentEntity

class CollectionDataSource {

    fun queryAll() =
        DatabaseApi.collectionDao()
            .queryAll()

    fun delete(items: List<ContentEntity.DataEntity>) =
        DatabaseApi.collectionDao()
            .delete(items)
            .toObservable()
            .compose(SchedulersTransformer())
}