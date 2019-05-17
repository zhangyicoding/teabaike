package estyle.base

import android.view.View
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagedListAdapter<T, VM : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VM>(diffCallback) {

    var emptyView: View? = null

    override fun onCurrentListChanged(
        previousList: PagedList<T>?,
        currentList: PagedList<T>?
    ) {
        super.onCurrentListChanged(previousList, currentList)
        emptyView?.visibility = if (this.itemCount == 0) View.VISIBLE else View.GONE
    }
}
