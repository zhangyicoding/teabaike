package estyle.teabaike.fragment


import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import estyle.base.fragment.BaseFragment
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.observer.RefreshObserver
import estyle.base.rxjava.observer.SnackbarObserver
import estyle.base.widget.PagingRecyclerView
import estyle.teabaike.R
import estyle.teabaike.activity.ContentActivity
import estyle.teabaike.adapter.MainListAdapter
import estyle.teabaike.config.Url
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.viewmodel.MainListViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainFragment : BaseFragment(), PagingRecyclerView.OnLoadListener {

    private val viewModel by lazy { ViewModelProviders.of(this)[MainListViewModel::class.java] }
    private lateinit var adapter: MainListAdapter

    val title: String by lazy { arguments!!.getString(TITLE)!! }
    private val type by lazy { arguments!!.getString(TYPE)!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainListAdapter()
        adapter.emptyView = empty_view
        // TODO context全部改为nonnull
        adapter.onItemClickListener =
            { position, id -> ContentActivity.startActivity(context!!, id) }
        recycler_view.adapter = adapter
        recycler_view.setOnLoadListener(this)
        swipe_refresh_layout.setOnRefreshListener { refresh() }

        // 头条Banner
        if (TextUtils.equals(type, Url.TYPES[0])) {
            adapter.onHeadlineViewHolderCreatedCallback = { holder ->
                lifecycle.addObserver(holder)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataWithPermissionCheck()
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun initData() {
        swipe_refresh_layout.post {
            swipe_refresh_layout.isRefreshing = true
            refresh()
        }
    }

    // SwipeRefreshLayout
    private fun refresh() {
        val refreshListObservable = viewModel.refreshList(type)
            .doOnNext { adapter.refresh(it) }

        var observable: Observable<out List<Any>> = refreshListObservable

        // 头条
        if (TextUtils.equals(type, Url.TYPES[0])) {
            val loadHeadlineObservable = viewModel.loadHeadline()
                .doOnNext { adapter.headlineList = it }
            observable = Observable.mergeDelayError(refreshListObservable, loadHeadlineObservable)
        }

        // 当所有网络请求全部结束后，更新刷新状态
        observable
            .map {}// TODO 原始数据类型会报错，必须转成其它类型？空的也可以？？？醉了
            .`as`(DisposableConverter.dispose(this))
            .subscribe(object : RefreshObserver<Any>(swipe_refresh_layout) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    empty_view.setText(R.string.request_fail)
                }
            })
    }

    // PagingRecyclerView
    override fun onLoad() {
        viewModel.moreList(type)
            .`as`(DisposableConverter.dispose(this))
            .subscribe(object : SnackbarObserver<List<MainEntity>>(recycler_view) {

                override fun onNext(it: List<MainEntity>) {
                    super.onNext(it)
                    adapter.load(it)
                    recycler_view.isLoading = false
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    recycler_view.isLoading = false
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        swipe_refresh_layout.setOnRefreshListener(null)
        recycler_view.setOnLoadListener(null)
        (recycler_view.adapter as MainListAdapter).emptyView = null
        recycler_view.adapter = null
    }

    companion object {

        private const val TITLE = "title"
        private const val TYPE = "type"

        fun newInstance(title: String, type: String?): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(TYPE, type)
                }
            }
        }
    }
}
