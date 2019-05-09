package estyle.teabaike.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import estyle.teabaike.R
import estyle.teabaike.adapter.viewholder.BaseViewHolder
import estyle.teabaike.databinding.ItemCollectionBinding
import estyle.teabaike.entity.ContentEntity
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionAdapter :
    BasePagedListAdapter<ContentEntity.DataEntity, CollectionAdapter.ViewHolder>(diffCallback) {

    private val deleteMap by lazy { HashMap<Int, ContentEntity.DataEntity>() }
    private val deleteList by lazy { arrayListOf<ContentEntity.DataEntity>() }
    private var isDeleteBoxVisible: Boolean = false

    var itemClickCallback: ((position: Int, id: Long) -> Unit)? = null
    var itemLongClickCallback: ((position: Int) -> Unit)? = null
    var deleteAllCallback: ((isDeleteAll: Boolean) -> Unit)? = null

    // CheckBox可见
    fun setDeleteBoxVisibility(isDeleteBoxVisible: Boolean) {
        currentList ?: return
        this.isDeleteBoxVisible = isDeleteBoxVisible
        if (!isDeleteBoxVisible) {
            currentList!!.forEach { it.isChecked = false }
        }
        notifyDataSetChanged()
    }

    // 勾选指定位置Item
    fun invertItemStateAtPosition(position: Int) {
        getItem(position)!!.isChecked = getItem(position)!!.isChecked.not()
        notifyDataSetChanged()
    }

    // 全选/取消全选
    fun setIsCheckedAllItem(isCheckedAll: Boolean) {
        currentList ?: return
        currentList!!.forEach { it.isChecked = isCheckedAll }
        notifyDataSetChanged()
    }

    // 删除数据库中的数据
    fun deleteData(): List<ContentEntity.DataEntity> {
        deleteList.clear()
        val iterator = deleteMap.iterator()
        while (iterator.hasNext()) {
            deleteList.add(iterator.next().value)
            iterator.remove()
        }
        return deleteList
    }

    override fun getItemId(position: Int): Long = getItem(position)!!.id!!.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_collection)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(parent: ViewGroup, layoutId: Int) :
        BaseViewHolder<ContentEntity.DataEntity, ItemCollectionBinding>(parent, layoutId) {

        override fun create() {
            super.create()
            itemView.setOnClickListener {
                this@CollectionAdapter.itemClickCallback?.invoke(
                    adapterPosition,
                    getItemId(adapterPosition)
                )
            }
            itemView.setOnLongClickListener {
                this@CollectionAdapter.itemLongClickCallback?.invoke(adapterPosition)
                true
            }
            itemView.delete_box.setOnCheckedChangeListener { buttonView, isChecked ->
                binding.collection!!.isChecked = isChecked
                if (isChecked) {
                    this@CollectionAdapter.deleteMap[adapterPosition] = binding.collection!!
                } else {
                    this@CollectionAdapter.deleteMap.remove(adapterPosition)
                }

                // 监听是否全选/取消全选，修改Activity中按钮状态
                var count = 0
                this@CollectionAdapter.currentList!!.forEach { if (it.isChecked) count++ }
                if (count == 0) {
                    this@CollectionAdapter.deleteAllCallback?.invoke(false)
                } else if (count == currentList!!.size) {
                    this@CollectionAdapter.deleteAllCallback?.invoke(true)
                }
            }
        }

        override fun bind(currentItem: ContentEntity.DataEntity) {
            binding.collection = currentItem
            itemView.delete_box.visibility =
                if (this@CollectionAdapter.isDeleteBoxVisible) View.VISIBLE else View.INVISIBLE
            itemView.delete_box.isChecked = currentItem.isChecked
        }
    }

    // RecyclerView附属的比较新旧item差异的回调
    private class ItemCallback : DiffUtil.ItemCallback<ContentEntity.DataEntity>() {
        override fun areItemsTheSame(
            oldItem: ContentEntity.DataEntity,
            newItem: ContentEntity.DataEntity
        ): Boolean = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ContentEntity.DataEntity,
            newItem: ContentEntity.DataEntity
        ): Boolean = oldItem == newItem
    }

    companion object {
        private val diffCallback = ItemCallback()
    }
}