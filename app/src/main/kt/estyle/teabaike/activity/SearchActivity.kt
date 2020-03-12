package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.observer.RefreshObserver
import estyle.base.rxjava.observer.SnackbarObserver
import estyle.base.widget.PagingRecyclerView
import estyle.teabaike.R
import estyle.teabaike.adapter.SearchAdapter
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), PagingRecyclerView.OnLoadListener {


    private val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    private val adapter by lazy { SearchAdapter() }

    private val keyword by lazy { intent.getStringExtra("keyword") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = keyword

        adapter.emptyView = empty_view
        adapter.onItemClickListener = { position, id -> ContentActivity.startActivity(this, id) }
        recycler_view.adapter = adapter

        recycler_view.setOnLoadListener(this)
        swipe_refresh_layout.setOnRefreshListener { refresh() }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        swipe_refresh_layout.post {
            swipe_refresh_layout.isRefreshing = true
            refresh()
        }
    }

    // PagingRecyclerView
    override fun onLoad() {
        viewModel.loadMore(keyword)
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

    // SwipeRefreshLayout
    private fun refresh() {
        viewModel.refresh(keyword)
            .`as`(DisposableConverter.dispose(this))
            .subscribe(object :
                RefreshObserver<List<MainEntity>>(swipe_refresh_layout) {
                override fun onNext(it: List<MainEntity>) {
                    super.onNext(it)
                    adapter.refresh(it)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    empty_view.setText(R.string.request_fail)
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        swipe_refresh_layout.setOnRefreshListener(null)
        recycler_view.setOnLoadListener(null)
    }

    companion object {

        fun startActivity(context: Context, keyword: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("keyword", keyword)
            context.startActivity(intent)
        }
    }
}
