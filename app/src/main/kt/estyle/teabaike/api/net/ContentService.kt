package estyle.teabaike.api.net

import estyle.teabaike.config.Url
import estyle.teabaike.entity.ContentEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentService {

    @GET(Url.CONTENT_URL)
    fun getContent(@Query(Url.ID) id: Long): Observable<ContentEntity>

}
