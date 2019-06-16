package estyle.base.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingRecyclerView(
    context: Context,
    attrs: AttributeSet?
) : RecyclerView(context, attrs) {

    var prefetchCount = 3
    var isLoading: Boolean = false

    private var mListener: OnLoadListener? = null
    private val mLayoutManager by lazy { LinearLayoutManager(context) }

    private val mOnScrollListener = object : OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            val itemCount = mLayoutManager.itemCount
            prefetchCount = if (prefetchCount < 0) 0 else prefetchCount
            val isScrollToBottom = lastVisibleItemPosition >= itemCount - 1 - prefetchCount
            if (!isLoading && isScrollToBottom) {
                isLoading = true
                mListener?.onLoad()
            }
        }
    }

    init {
        layoutManager = mLayoutManager
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    fun setOnLoadListener(listener: OnLoadListener?) {
        mListener = listener
        if (mListener != null) {
            addOnScrollListener(mOnScrollListener)
        } else {
            removeOnScrollListener(mOnScrollListener)
        }
    }

    interface OnLoadListener {
        fun onLoad()
    }
}