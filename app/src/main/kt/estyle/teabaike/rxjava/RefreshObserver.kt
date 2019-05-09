package estyle.teabaike.rxjava

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

open class RefreshObserver<T>(
    private val swipeRefreshLayout: SwipeRefreshLayout?,
    errorText: String? = null
) :
    SnackbarObserver<T>(swipeRefreshLayout, errorText) {

    override fun onNext(it: T) {
        super.onNext(it)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onComplete() {
        super.onComplete()
        swipeRefreshLayout?.isRefreshing = false
    }
}
