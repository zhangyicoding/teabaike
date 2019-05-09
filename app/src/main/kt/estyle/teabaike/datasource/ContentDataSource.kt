package estyle.teabaike.datasource

import estyle.teabaike.api.DatabaseApi
import estyle.teabaike.api.NetApi
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.rxjava.SchedulersTransformer

class ContentDataSource {

    fun loadContent(id: Long) =
        NetApi.contentService()
            .getContent(id)
            .map { it.data }
            .compose(SchedulersTransformer())

    fun collect(content: ContentEntity.DataEntity) =
        DatabaseApi.collectionDao()
            .insert(content)
            .toObservable<Long>()
            .compose(SchedulersTransformer())
}
