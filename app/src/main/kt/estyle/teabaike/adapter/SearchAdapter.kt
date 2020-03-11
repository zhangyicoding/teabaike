package estyle.teabaike.adapter

import android.view.ViewGroup
import estyle.base.adpter.BaseAdapter
import estyle.teabaike.R
import estyle.teabaike.adapter.viewholder.MainViewHolder
import estyle.teabaike.entity.MainEntity

class SearchAdapter : BaseAdapter<MainEntity, MainViewHolder>() {

    override fun getItemId(position: Int): Long = itemList[position].id?.toLong() ?: 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(parent, R.layout.item_main, this)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}