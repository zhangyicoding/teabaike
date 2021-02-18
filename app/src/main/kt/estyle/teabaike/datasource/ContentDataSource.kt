package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.database.DatabaseManager
import estyle.teabaike.datasource.http.HttpManager
import estyle.teabaike.datasource.http.service.ContentService
import estyle.teabaike.entity.ContentEntity

class ContentDataSource {

    /**
     * 刷新
     */
    fun refresh(id: Long) =
        HttpManager.service(ContentService::class.java)
            .getContent(id)
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())

    /**
     * 收藏
     */
    fun collect(content: ContentEntity) =
        DatabaseManager.INSTANCE
            .collectionDao()
            .insert(content)
            .toObservable()
            .compose(SchedulersTransformer())
}