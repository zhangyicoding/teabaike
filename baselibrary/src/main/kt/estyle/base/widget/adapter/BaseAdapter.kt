package estyle.base.widget.adapter

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.set
import androidx.core.util.size
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>
    (val items: List<T> = arrayListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var emptyView: View? = null

    lateinit var holder: RecyclerView.ViewHolder

    private val types by lazy { SparseIntArray() }

    fun clear() {
        val clearSize = items.size
        (items as ArrayList).clear()
        notifyItemRangeRemoved(0, clearSize)
        notifyItemRangeChanged(0, clearSize)
    }

    fun refresh(t: List<T>?) {
        clear()
        addAll(t)
    }

    fun add(t: T?, index: Int = items.size) {
        t ?: return
        (items as ArrayList).add(index, t)
        notifyItemInserted(index)
        notifyItemRangeChanged(index, items.size - index)
    }

    fun addAll(t: List<T>?, index: Int = items.size) {
        t ?: return
        (items as ArrayList).addAll(index, t)
        notifyItemRangeInserted(index, t.size)
        notifyItemRangeChanged(index, items.size - index)
    }

    fun set(t: T?, index: Int) {
        t ?: return
        (items as ArrayList)[index] = t
        notifyItemChanged(index)
    }

    fun remove(t: T) {
        removeAt(items.indexOf(t))
    }

    fun removeAt(index: Int) {
        if (index < 0 || index > items.size - 1) return
        (items as ArrayList).removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size - index)
    }

    // todo bug 只判断第一个非常危险啊
    fun removeAll(t: List<T>) {
        for ((index, item) in items.withIndex()) {
            if (item == t[0]) {
                (items as ArrayList).removeAll(t)
                notifyItemRangeRemoved(index, t.size)
                notifyItemRangeChanged(index, items.size - index)
                break
            }
        }
    }

    init {
        val layoutId = initLayoutId(types)
        if (layoutId != null) {
            types.clear()
            types[DEFAULT_VIEW_TYPE] = layoutId
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (types.size == 1) {
            return DEFAULT_VIEW_TYPE
        }
        return (items[position] as ViewType).viewType()
    }

    override fun getItemCount(): Int {
        emptyView?.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(types[viewType], parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        this.holder = holder
        onBind(holder.itemView, items[position], position, holder.itemViewType)
    }

    abstract fun initLayoutId(multiTypes: SparseIntArray): Int?

    abstract fun onBind(itemView: View, item: T, position: Int, viewType: Int)

    interface ViewType {
        fun viewType(): Int
    }

    companion object {
        private const val DEFAULT_VIEW_TYPE = 0
    }
}