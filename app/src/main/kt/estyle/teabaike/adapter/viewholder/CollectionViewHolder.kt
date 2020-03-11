package estyle.teabaike.adapter.viewholder

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import estyle.base.adpter.viewholder.BaseViewHolder
import estyle.teabaike.activity.CollectionActivity.Companion.ACTION_DELETE_ITEM
import estyle.teabaike.activity.CollectionActivity.Companion.EXTRA_DELETE_ALL
import estyle.teabaike.adapter.CollectionAdapter
import estyle.teabaike.databinding.ItemCollectionBinding
import estyle.teabaike.entity.ContentEntity
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionViewHolder(
    parent: ViewGroup,
    layoutId: Int,
    private val adapter: CollectionAdapter
) :
    BaseViewHolder<ContentEntity, ItemCollectionBinding>(parent, layoutId) {

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
                notifyDeleteState(false)
            } else if (count == adapter.currentList!!.size) {
                notifyDeleteState(true)
            }
        }
    }

    private fun notifyDeleteState(isDeleteAll: Boolean) {
        LocalBroadcastManager.getInstance(itemView.context)
            .sendBroadcast(Intent(ACTION_DELETE_ITEM).apply {
                putExtra(EXTRA_DELETE_ALL, isDeleteAll)
            })
    }

    override fun bind(currentItem: ContentEntity) {
        binding.collection = currentItem
        itemView.delete_box.visibility =
            if (adapter.isDeleteBoxVisible) View.VISIBLE else View.INVISIBLE
        itemView.delete_box.isChecked = currentItem.isChecked
    }
}