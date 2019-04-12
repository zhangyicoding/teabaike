package estyle.teabaike.databinding;

import android.databinding.BindingAdapter;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class TeaBaikeBindingAdapter {

    @BindingAdapter("imgUrl")// 布局中使用的属性名
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @BindingAdapter("webData")
    public static void loadWeb(WebView webView, String data) {
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
    }

}
