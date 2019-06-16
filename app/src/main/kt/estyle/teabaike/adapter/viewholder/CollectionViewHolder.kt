package estyle.teabaike.adapter.viewholder

import android.view.View
import android.view.ViewGroup
import estyle.base.adpter.viewholder.BaseViewHolder
import estyle.teabaike.adapter.CollectionAdapter
import estyle.teabaike.databinding.ItemCollectionBinding
import estyle.teabaike.entity.ContentEntity
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionViewHolder(
    parent: ViewGroup,
    layoutId: Int,
    private val adapter: CollectionAdapter
) :
    BaseViewHolder<ContentEntity.DataEntity, ItemCollectionBinding>(parent, layoutId) {

    override fun create() {
        super.create()
        itemView.setOnClickListener {
            adapter.onItemClickListener?.invoke(adapterPosition, itemId)
        }
        itemView.setOnLongClickListener {
            adapter.onItemLongClickListener?.invoke(adapterPosition, itemId)
            true
        }
        itemView.delete_box.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.collection!!.isChecked = isChecked
            if (isChecked) {
                adapter.deleteMap[adapterPosition] = binding.collection!!
            } else {
                adapter.deleteMap.remove(adapterPosition)
            }

            // 监听是否全选/取消全选，修改Activity中按钮状态
            var count = 0
            adapter.currentList!!.forEach { if (it.isChecked) count++ }
            if (count == 0) {
                adapter.deleteAllCallback?.invoke(false)
            } else if (count == adapter.currentList!!.size) {
                adapter.deleteAllCallback?.invoke(true)
            }
        }
    }

    override fun bind(currentItem: ContentEntity.DataEntity) {
        binding.collection = currentItem
        itemView.delete_box.visibility =
            if (adapter.isDeleteBoxVisible) View.VISIBLE else View.INVISIBLE
        itemView.delete_box.isChecked = currentItem.isChecked
    }
}