package estyle.teabaike.fragment


import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.base.fragment.BaseFragment
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.observer.RefreshObserver
import estyle.teabaike.R
import estyle.teabaike.activity.ContentActivity
import estyle.teabaike.adapter.MainAdapter
import estyle.teabaike.config.Url
import estyle.teabaike.viewmodel.MainListViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelProviders.of(this)[MainListViewModel::class.java] }

    val title: String by lazy { arguments!!.getString(TITLE) }
    private val type by lazy { arguments!!.getString(TYPE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MainAdapter()
        adapter.emptyView = empty_view
        // todo context全部改为nonnull
        adapter.itemClickCallback = { ContentActivity.startActivity(context!!, it) }
        recycler_view.adapter = adapter

        viewModel.mainList.observe(this, Observer { adapter.submitList(it) })
        if (TextUtils.equals(type, Url.TYPES[0])) {
            viewModel.headerList.observe(this, Observer {
                adapter.headlineList = it
                adapter.onHeadlineViewHolderCreatedCallback = { holder ->
                    lifecycle.addObserver(holder)
                }
            })
        }
        swipe_refresh_layout.setOnRefreshListener { refresh() }
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

    // 数据回调
    private fun refresh() {
        viewModel.refresh(type)
            // todo paging的datasource中已经canceled/disposed，切换其他频道crash
            .`as`<ObservableSubscribeProxy<MainListViewModel>>(DisposableConverter.dispose(this))
            .subscribe(object : RefreshObserver<MainListViewModel>(swipe_refresh_layout) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    empty_view.setText(R.string.request_fail)
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
        (recycler_view.adapter as MainAdapter).emptyView = null
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
