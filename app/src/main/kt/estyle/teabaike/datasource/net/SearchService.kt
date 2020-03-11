package estyle.teabaike.datasource.net

import com.estyle.httpmock.annotation.HttpMock
import estyle.base.entity.HttpEntity
import estyle.teabaike.config.Url
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @HttpMock(fileName = "list.json")
    @GET(Url.SEARCH_URL)
    fun getSearch(@Query(Url.SEARCH) keyword: String, @Query(Url.PAGE) page: Int): Observable<HttpEntity<List<MainEntity>>>
}
