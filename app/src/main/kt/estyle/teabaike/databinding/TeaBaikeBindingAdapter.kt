package estyle.teabaike.databinding

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import estyle.teabaike.util.ImageUtil

object TeaBaikeBindingAdapter {

    @JvmStatic
    @BindingAdapter("imgPath")// 布局中使用的属性名
    fun loadImage(imageView: ImageView, path: String) {
        ImageUtil.show(imageView, path)
    }

    @JvmStatic
    @BindingAdapter("webData")
    fun loadWeb(webView: WebView, data: String?) {
        // TODO 当前执行两次，第一次data总为null，有待改进
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
    }
}
