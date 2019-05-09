package estyle.teabaike.api.net

import estyle.teabaike.config.Url
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET(Url.SEARCH_URL)
    fun getSearch(@Query(Url.SEARCH) keyword: String, @Query(Url.PAGE) page: Int): Observable<MainEntity>
}
