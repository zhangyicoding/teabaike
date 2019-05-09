package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.teabaike.R
import estyle.teabaike.adapter.MainAdapter
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.rxjava.DisposableConverter
import estyle.teabaike.rxjava.RefreshObserver
import estyle.teabaike.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[SearchViewModel::class.java] }

    private val adapter by lazy { MainAdapter() }

    private val keyword by lazy { intent.getStringExtra("keyword") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = keyword

        adapter.emptyView = empty_view
        adapter.itemClickCallback = { ContentActivity.startActivity(this, it) }
        recycler_view.adapter = adapter

        swipe_refresh_layout.setOnRefreshListener { refresh() }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        swipe_refresh_layout.post {
            swipe_refresh_layout.isRefreshing = true
            refresh()
        }
    }

    private fun refresh() {
        viewModel.refresh(keyword)
            .`as`<ObservableSubscribeProxy<PagedList<MainEntity.DataEntity>>>(
                DisposableConverter.dispose(this)
            )
            .subscribe(object :
                RefreshObserver<PagedList<MainEntity.DataEntity>>(swipe_refresh_layout) {
                override fun onNext(it: PagedList<MainEntity.DataEntity>) {
                    super.onNext(it)
                    adapter.submitList(it)
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

    companion object {

        fun startActivity(context: Context, keyword: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("keyword", keyword)
            context.startActivity(intent)
        }
    }
}
