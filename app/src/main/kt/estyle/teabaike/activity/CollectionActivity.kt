package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.teabaike.R
import estyle.teabaike.adapter.CollectionAdapter
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.fragment.dialog.CollectionDialogFragment
import estyle.teabaike.rxjava.DisposableConverter
import estyle.teabaike.rxjava.RefreshObserver
import estyle.teabaike.rxjava.SnackbarObserver
import estyle.teabaike.viewmodel.CollectionViewModel
import estyle.teabaike.widget.Snackbar
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[CollectionViewModel::class.java] }

    private val adapter by lazy { CollectionAdapter() }

    // 删除功能是否可用
    private var mIsDeleteEnabled: Boolean = false

    private val deleteDialog by lazy {
        CollectionDialogFragment.newInstance().apply {
            positiveCallback = { deleteItems() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter.emptyView = empty_view
        adapter.itemClickCallback = { position, id ->
            if (!mIsDeleteEnabled) {
                ContentActivity.startActivity(this, id)
            } else {
                adapter.invertItemStateAtPosition(position)
            }
        }
        adapter.itemLongClickCallback = { position ->
            if (!mIsDeleteEnabled) {
                adapter.invertItemStateAtPosition(position)
                setDeleteEnabled(true)
            }
        }
        // 控制全选按钮文字
        adapter.deleteAllCallback = { isDeleteAll ->
            check_all_text_view.text =
                getString(if (isDeleteAll) R.string.uncheck_all else R.string.check_all)
        }
        recycler_view.adapter = adapter

        check_all_text_view.setOnClickListener {
            when (check_all_text_view.text.toString()) {
                getString(R.string.check_all) -> adapter.setIsCheckedAllItem(true)
                getString(R.string.uncheck_all) -> adapter.setIsCheckedAllItem(false)
            }
        }
        delete_btn.setOnClickListener { deleteDialog.show(supportFragmentManager, null) }
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
        viewModel.refresh()
            .`as`<ObservableSubscribeProxy<PagedList<ContentEntity.DataEntity>>>(
                DisposableConverter.dispose(this)
            )
            .subscribe(object :
                RefreshObserver<PagedList<ContentEntity.DataEntity>>(swipe_refresh_layout) {
                override fun onNext(it: PagedList<ContentEntity.DataEntity>) {
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
            android.R.id.home -> if (mIsDeleteEnabled) {
                setDeleteEnabled(false)
            } else {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 删除选中数据
    private fun deleteItems() {
        viewModel.deleteItems(adapter.deleteData())
            .`as`<ObservableSubscribeProxy<Int>>(DisposableConverter.dispose(this))
            // todo 写android.R.id.content行不行
            .subscribe(object : SnackbarObserver<Int>(swipe_refresh_layout) {
                override fun onNext(deleteCount: Int) {
                    super.onNext(deleteCount)
                    val text = String.format(getString(R.string.delete_successful, deleteCount))
                    Snackbar.make(swipe_refresh_layout, text, Snackbar.LENGTH_SHORT).show()
                }
            })
    }

    // 设置是否可删除
    private fun setDeleteEnabled(isDeleteEnabled: Boolean) {
        this.mIsDeleteEnabled = isDeleteEnabled
        adapter.setDeleteBoxVisibility(isDeleteEnabled)
        val visibility: Int = if (isDeleteEnabled) {
            delete_btn.show()
            View.VISIBLE
        } else {
            delete_btn.hide()
            View.INVISIBLE
        }
        check_all_text_view.visibility = visibility
    }

    override fun onBackPressed() {
        if (mIsDeleteEnabled) {
            setDeleteEnabled(false)
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, CollectionActivity::class.java)
            context.startActivity(intent)
        }
    }
}