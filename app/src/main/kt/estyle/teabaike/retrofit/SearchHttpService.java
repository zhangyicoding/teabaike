package estyle.teabaike.retrofit;

import estyle.teabaike.bean.MainBean;
import estyle.teabaike.constant.Url;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchHttpService {

    @GET(Url.SEARCH_URL)
    Observable<MainBean> getObservable(@Query(Url.SEARCH) String keyword, @Query(Url.PAGE) int page);

}
