package estyle.teabaike.adapter

import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import estyle.teabaike.R
import estyle.teabaike.adapter.viewholder.BaseViewHolder
import estyle.teabaike.databinding.ItemMainBinding
import estyle.teabaike.databinding.ViewHeadlineBinding
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.util.ImageUtil
import kotlinx.android.synthetic.main.view_headline.view.*

class MainAdapter :
    BasePagedListAdapter<MainEntity.DataEntity, RecyclerView.ViewHolder>(diffCallback) {

    var itemClickCallback: ((id: Long) -> Unit)? = null
    private var headerCount: Int = 0

    lateinit var onHeadlineViewHolderCreatedCallback: ((holder: MainAdapter.HeadlineViewHolder) -> Unit)

    var headlineList: List<HeadlineEntity.DataEntity>? = null
        set(value) {
            value ?: return
            if (field != null) headerCount--
            field = value
            headerCount++
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = super.getItemCount() + headerCount

    override fun getItemId(position: Int): Long =
        if (position < headerCount)
            super.getItemId(position)
        else
            getItem(position - headerCount)!!.id!!.toLong()

    override fun getItemViewType(position: Int): Int {
        var viewType = super.getItemViewType(position)
        if (position < headerCount) {
            if (position == 0) viewType = TYPE_HEADER_HEADLINE
        }
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_HEADER_HEADLINE -> {
                holder = HeadlineViewHolder(parent, R.layout.view_headline).apply { bind(headlineList!!) }
                onHeadlineViewHolderCreatedCallback(holder)
            }
            else -> holder = ViewHolder(parent, R.layout.item_main)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(getItem(position - headerCount)!!)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is HeadlineViewHolder) {
            holder.start()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is HeadlineViewHolder) {
            holder.stop()
        }
    }

    override fun onCurrentListChanged(
        previousList: PagedList<MainEntity.DataEntity>?,
        currentList: PagedList<MainEntity.DataEntity>?
    ) {
        super.onCurrentListChanged(previousList, currentList)
        // todo 首页itemcount总是0
//        ZYLog.e("pre : " + previousList?.size + ", curr: " + currentList?.size + ", itemcount: " + itemCount + ", empty visible: " + emptyView?.visibility)
    }

    // todo 先出现item数据，头视图数据后出现，使用notify，位置向下滑
    // todo 讲道理，生命周期应该绑定到BannerView上，坐等ViewPager2吧
     inner class HeadlineViewHolder(parent: ViewGroup, layoutId: Int) :
        BaseViewHolder<List<HeadlineEntity.DataEntity>, ViewHeadlineBinding>(
            parent,
            layoutId
        ), DefaultLifecycleObserver {

        // todo 如何优雅地使用databinding
        override fun bind(currentItem: List<HeadlineEntity.DataEntity>) {
            itemView.dots_view.dotCount = currentItem.size
            itemView.headline_text_view.text = currentItem[0].title
            itemView.banner_view_old.setOnBannerSelectedListener { position ->
                itemView.headline_text_view.text = currentItem[position].title
                itemView.dots_view.selectedPosition = position
            }
            itemView.banner_view_old.setOnBannerClickListener { position ->
                this@MainAdapter.itemClickCallback?.invoke(currentItem[position].id!!.toLong())// todo  id is nonnull
            }
            val imageList = arrayListOf<String>().apply { currentItem.forEach { add(it.image!!) } }
            itemView.banner_view_old.loadImages(imageList) { path, view -> ImageUtil.show(view, path) }
        }

        fun start() {
            itemView.banner_view_old.isCircling = true
        }

        fun stop() {
            itemView.banner_view_old.isCircling = false
        }

        override fun onStart(owner: LifecycleOwner) {
            start()
        }

        override fun onStop(owner: LifecycleOwner) {
            stop()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            itemView.banner_view_old.onDestroy()
        }
    }

    private inner class ViewHolder(parent: ViewGroup, layoutId: Int) :
        BaseViewHolder<MainEntity.DataEntity, ItemMainBinding>(parent, layoutId) {

        override fun create() {
            super.create()
            itemView.setOnClickListener {
                this@MainAdapter.itemClickCallback?.invoke(getItemId(adapterPosition))
            }
        }

        override fun bind(currentItem: MainEntity.DataEntity) {
            binding.main = currentItem
        }
    }

    // RecyclerView附属的比较新旧item差异的回调
    private class ItemCallback : DiffUtil.ItemCallback<MainEntity.DataEntity>() {
        override fun areItemsTheSame(
            oldItem: MainEntity.DataEntity,
            newItem: MainEntity.DataEntity
        ): Boolean = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: MainEntity.DataEntity,
            newItem: MainEntity.DataEntity
        ): Boolean = oldItem == newItem
    }

    companion object {
        private const val TYPE_HEADER_HEADLINE = 1
        private val diffCallback = ItemCallback()
    }
}