package estyle.base.adpter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import estyle.base.adpter.BaseAdapter

abstract class BaseViewHolder<T, VDB : ViewDataBinding>(parent: ViewGroup, layoutId: Int) :
    RecyclerView.ViewHolder(
        DataBindingUtil.inflate<VDB>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        ).root
    ) {

    protected lateinit var binding: VDB

    init {
        this.create()
    }

    protected open fun create() {
        binding = DataBindingUtil.getBinding(itemView)!!
    }

    abstract fun bind(currentItem: T)
}