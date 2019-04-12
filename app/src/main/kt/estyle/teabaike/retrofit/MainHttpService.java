package estyle.teabaike.retrofit;

import estyle.teabaike.bean.MainBean;
import estyle.teabaike.constant.Url;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainHttpService {

    @GET(Url.HEADLINE_URL)
    Observable<MainBean> getHeadlineObservable(@Query(Url.PAGE) int page);

    @GET(Url.CHANNEL_URL)
    Observable<MainBean> getMainObservable(@Query(Url.TYPE) int type, @Query(Url.PAGE) int page);

}
