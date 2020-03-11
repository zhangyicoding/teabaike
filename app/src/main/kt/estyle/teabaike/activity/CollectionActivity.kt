package estyle.teabaike.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.paging.PagedList
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.observer.RefreshObserver
import estyle.base.rxjava.observer.SnackbarObserver
import estyle.base.widget.Snackbar
import estyle.teabaike.R
import estyle.teabaike.adapter.CollectionAdapter
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.fragment.dialog.DeleteCollectionDialogFragment
import estyle.teabaike.viewmodel.CollectionViewModel
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[CollectionViewModel::class.java] }

    private val adapter by lazy { CollectionAdapter() }

    private val deleteReceiver by lazy { DeleteReceiver() }

    // 删除功能是否可用
    private var mIsDeleteEnabled: Boolean = false

    private val deleteDialog by lazy {
        DeleteCollectionDialogFragment.newInstance().apply {
            positiveCallback = { deleteItems() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter.emptyView = empty_view
        adapter.onItemClickListener = { position, id ->
            if (!mIsDeleteEnabled) {
                ContentActivity.startActivity(this, id)
            } else {
                adapter.invertItemStateAtPosition(position)
            }
        }
        adapter.onItemLongClickListener = { position, id ->
            if (!mIsDeleteEnabled) {
                adapter.invertItemStateAtPosition(position)
                setDeleteEnabled(true)
            }
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

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(deleteReceiver, IntentFilter(ACTION_DELETE_ITEM))
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
            .`as`(DisposableConverter.dispose(this))
            .subscribe(object :
                RefreshObserver<PagedList<ContentEntity>>(swipe_refresh_layout) {
                override fun onNext(it: PagedList<ContentEntity>) {
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
            .`as`(DisposableConverter.dispose(this))
            // todo 写android.R.id.content行不行
            .subscribe(object : SnackbarObserver<Int>(swipe_refresh_layout) {
                override fun onNext(deleteCount: Int) {
                    super.onNext(deleteCount)
                    val text = String.format(getString(R.string.delete_successful, deleteCount))
                    Snackbar.make(swipe_refresh_layout, text, Snackbar.LENGTH_SHORT).show()
                    setDeleteEnabled(false)
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

    override fun onDestroy() {
        super.onDestroy()
        swipe_refresh_layout.setOnRefreshListener(null)
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(deleteReceiver)
    }

    inner class DeleteReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isDeleteAll = intent!!.getBooleanExtra(EXTRA_DELETE_ALL, false)
            check_all_text_view.text =
                getString(if (isDeleteAll) R.string.uncheck_all else R.string.check_all)
        }
    }

    companion object {
        const val ACTION_DELETE_ITEM = "action.DELETE_ITEM"
        const val EXTRA_DELETE_ALL = "EXTRA_DELETE_ALL"

        fun startActivity(context: Context) {
            val intent = Intent(context, CollectionActivity::class.java)
            context.startActivity(intent)
        }
    }
}