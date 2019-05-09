package estyle.teabaike.util

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule

object ImageUtil {

    fun show(view: ImageView, path: String?) {
        Glide.with(view.context)
            .load(path)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context))
        }
    }
}