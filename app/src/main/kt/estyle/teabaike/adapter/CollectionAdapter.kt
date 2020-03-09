package estyle.teabaike.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import estyle.base.adpter.BasePagedListAdapter
import estyle.teabaike.R
import estyle.teabaike.adapter.viewholder.CollectionViewHolder
import estyle.teabaike.entity.ContentEntity

class CollectionAdapter :
    BasePagedListAdapter<ContentEntity.DataEntity, CollectionViewHolder>(diffCallback) {

    val deleteMap by lazy { HashMap<Int, ContentEntity.DataEntity>() }
    var isDeleteBoxVisible: Boolean = false
    private val deleteList by lazy { arrayListOf<ContentEntity.DataEntity>() }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder =
        CollectionViewHolder(parent, R.layout.item_collection, this)

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
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