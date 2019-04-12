package estyle.teabaike.retrofit;

import estyle.teabaike.bean.HeadlineBean;
import estyle.teabaike.constant.Url;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HeadlineHttpService {

    @GET(Url.HEADLINE_HEADER_URL)
    Observable<HeadlineBean> getObservable();

}
