package estyle.teabaike.datasource

import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.DatabaseApi
import estyle.teabaike.api.NetApi
import estyle.teabaike.entity.ContentEntity

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
