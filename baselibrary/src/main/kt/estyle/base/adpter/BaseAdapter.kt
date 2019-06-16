package estyle.base.adpter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VM : RecyclerView.ViewHolder> : RecyclerView.Adapter<VM>() {

    var onItemClickListener: ((position: Int, id: Long) -> Unit)? = null
    var onItemLongClickListener: ((position: Int, id: Long) -> Unit)? = null

    var emptyView: View? = null
    protected val itemList by lazy { arrayListOf<T>() }

    fun refresh(list: List<T>) {
        itemList.clear()
        load(list)
    }

    fun load(list: List<T>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        emptyView?.visibility = if (itemList.size == 0) View.VISIBLE else View.GONE
        return itemList.size
    }
}