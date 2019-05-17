package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.RefreshObserver
import estyle.base.rxjava.SnackbarObserver
import estyle.base.widget.Snackbar
import estyle.teabaike.R
import estyle.teabaike.databinding.ActivityContentBinding
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.viewmodel.ContentViewModel
import kotlinx.android.synthetic.main.activity_content.*
import java.lang.reflect.Method

class ContentActivity : BaseActivity() {

    private val id by lazy { intent.getLongExtra("id", 0) }

    // TODO 此处使用val不可以懒加载
    // 但是在HeadlineHeaderView中就可以
    private lateinit var binding: ActivityContentBinding
    private val viewModel by lazy { ViewModelProviders.of(this)[ContentViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        viewModel.refresh(id)
            .`as`<ObservableSubscribeProxy<ContentEntity.DataEntity>>(
                DisposableConverter.dispose(this)
            )
            .subscribe(object : RefreshObserver<ContentEntity.DataEntity>(swipe_refresh_layout) {
                override fun onNext(it: ContentEntity.DataEntity) {
                    super.onNext(it)
                    binding.content = it
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        val setOptionalIconsVisibleMethod: Method = menu.javaClass.getDeclaredMethod(
            "setOptionalIconsVisible",
            Boolean::class.javaPrimitiveType
        )
        setOptionalIconsVisibleMethod.isAccessible = true
        setOptionalIconsVisibleMethod.invoke(menu, true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share_item -> share()
            R.id.collect_item -> collect()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // 收藏文章
    private fun collect() {
        viewModel.collect(binding.content!!)
            .`as`<ObservableSubscribeProxy<Long>>(DisposableConverter.dispose(this))
            .subscribe(object :
                SnackbarObserver<Long>(binding.root, getString(R.string.collect_failure)) {
                override fun onComplete() {
                    super.onComplete()
                    showTip(getString(R.string.collect_successful))
                }
            })
    }

    // 分享文章
    private fun share() {
        showTip(getString(R.string.share_successful))
    }

    // 展示Snackbar
    private fun showTip(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        content_web_view.removeAllViews()
        content_web_view.destroy()
    }

    companion object {

        fun startActivity(context: Context, id: Long) {
            val intent = Intent(context, ContentActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }
}
