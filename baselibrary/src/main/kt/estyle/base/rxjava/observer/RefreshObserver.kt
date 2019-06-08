package estyle.base.rxjava.observer

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

open class RefreshObserver<T>(
    private val swipeRefreshLayout: SwipeRefreshLayout?,
    errorText: String? = null
) : AbstractViewObserver<T>(swipeRefreshLayout, errorText) {

    override fun gone() {
        swipeRefreshLayout?.isRefreshing = false
    }
}