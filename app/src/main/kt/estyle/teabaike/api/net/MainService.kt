package estyle.teabaike.api.net

import com.estyle.httpmock.annotation.HttpMock
import estyle.teabaike.config.Url
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    @HttpMock(fileName = "list.json")
    @GET(Url.HEADLINE_URL)
    fun getMainHeadline(@Query(Url.PAGE) page: Int): Observable<MainEntity>

    @HttpMock(fileName = "list.json")
    @GET(Url.CHANNEL_URL)
    fun getMain(@Query(Url.TYPE) type: String, @Query(Url.PAGE) page: Int): Observable<MainEntity>

    @HttpMock(fileName = "headline.json")
    @GET(Url.HEADLINE_HEADER_URL)
    fun getHeadline(): Observable<HeadlineEntity>
}
