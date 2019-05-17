package estyle.base.rxjava

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

open class RefreshObserver<T>(
    private val swipeRefreshLayout: SwipeRefreshLayout?,
    errorText: String? = null
) :
    SnackbarObserver<T>(swipeRefreshLayout, errorText) {

    override fun onNext(it: T) {
        swipeRefreshLayout?.isRefreshing = false
        super.onNext(it)
    }

    override fun onError(e: Throwable) {
        swipeRefreshLayout?.isRefreshing = false
        super.onError(e)
    }

    override fun onComplete() {
        swipeRefreshLayout?.isRefreshing = false
        super.onComplete()
    }
}
