package estyle.teabaike.adapter.viewholder

import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import estyle.base.adpter.BaseAdapter
import estyle.base.adpter.viewholder.BaseViewHolder
import estyle.base.util.ImageUtil
import estyle.teabaike.databinding.ViewHeadlineBinding
import estyle.teabaike.entity.HeadlineEntity
import kotlinx.android.synthetic.main.view_headline.view.*

// todo 讲道理，生命周期应该绑定到BannerView上，坐等ViewPager2吧
class MainHeadlineViewHolder(
    parent: ViewGroup,
    layoutId: Int,
    private val adapter: BaseAdapter<*, *>
) :
    BaseViewHolder<List<HeadlineEntity>, ViewHeadlineBinding>(
        parent,
        layoutId
    ), DefaultLifecycleObserver {

    // todo 如何优雅地使用databinding
    override fun bind(currentItem: List<HeadlineEntity>) {
        itemView.dots_view.dotCount = currentItem.size
        itemView.headline_text_view.text = currentItem[0].title
        itemView.banner_view_old.setOnBannerSelectedListener { position ->
            itemView.headline_text_view.text = currentItem[position].title
            itemView.dots_view.selectedPosition = position
        }
        itemView.banner_view_old.setOnBannerClickListener { position ->
            adapter.onItemClickListener?.invoke(
                adapterPosition,
                currentItem[position].id?.toLong() ?: 0L
            )
        }
        val imageList = arrayListOf<String>().apply { currentItem.forEach { add(it.image!!) } }
        itemView.banner_view_old.loadImages(imageList) { path, view ->
            ImageUtil.show(
                view,
                path
            )
        }
    }

    fun start() {
        itemView.banner_view_old.isCircling = true
    }

    fun stop() {
        itemView.banner_view_old.isCircling = false
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        start()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        itemView.banner_view_old.onDestroy()
    }
}