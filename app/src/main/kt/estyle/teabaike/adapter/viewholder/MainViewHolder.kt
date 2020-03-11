package estyle.teabaike.adapter.viewholder

import android.view.ViewGroup
import estyle.base.adpter.BaseAdapter
import estyle.base.adpter.viewholder.BaseViewHolder
import estyle.teabaike.databinding.ItemMainBinding
import estyle.teabaike.entity.MainEntity

class MainViewHolder(parent: ViewGroup, layoutId: Int, private val adapter: BaseAdapter<*, *>) :
    BaseViewHolder<MainEntity, ItemMainBinding>(parent, layoutId) {

    override fun create() {
        super.create()
        itemView.setOnClickListener { adapter.onItemClickListener?.invoke(adapterPosition, itemId) }
    }

    override fun bind(currentItem: MainEntity) {
        binding.main = currentItem
    }
}