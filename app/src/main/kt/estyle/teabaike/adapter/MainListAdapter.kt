package estyle.teabaike.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import estyle.base.adpter.BaseAdapter
import estyle.teabaike.R
import estyle.teabaike.adapter.viewholder.MainHeadlineViewHolder
import estyle.teabaike.adapter.viewholder.MainViewHolder
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity

class MainListAdapter : BaseAdapter<MainEntity, RecyclerView.ViewHolder>() {

    var onHeadlineViewHolderCreatedCallback: ((holder: MainHeadlineViewHolder) -> Unit)? = null

    private var headerCount: Int = 0
    var headlineList: List<HeadlineEntity>? = null
        set(value) {
            value ?: return
            if (field != null) headerCount--
            field = value
            headerCount++
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = super.getItemCount() + headerCount

    override fun getItemId(position: Int): Long =
        itemList[position - headerCount].id?.toLong() ?: 0L

    override fun getItemViewType(position: Int): Int =
        if (position < headerCount)
            TYPE_HEADER_HEADLINE
        else
            super.getItemViewType(position)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_HEADER_HEADLINE -> {
                holder = MainHeadlineViewHolder(
                    parent,
                    R.layout.view_headline,
                    this
                ).apply { bind(headlineList!!) }
                onHeadlineViewHolderCreatedCallback?.invoke(holder)
            }
            else -> holder = MainViewHolder(parent, R.layout.item_main, this)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            holder.bind(itemList[position - headerCount])
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is MainHeadlineViewHolder) {
            holder.start()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is MainHeadlineViewHolder) {
            holder.stop()
        }
    }

    companion object {
        private const val TYPE_HEADER_HEADLINE = 1
    }
}