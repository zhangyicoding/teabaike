package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.observer.SnackbarObserver
import estyle.base.util.VersionUtil
import estyle.teabaike.R
import estyle.teabaike.config.RoutePath
import estyle.teabaike.config.Url
import estyle.teabaike.entity.VersionEntity
import estyle.teabaike.fragment.MainFragment
import estyle.teabaike.fragment.dialog.UpdateDialogFragment
import estyle.teabaike.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Route(path = RoutePath.LAUNCHER_MAIN)
class MainActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private val toggle by lazy { ActionBarDrawerToggle(this, main_drawer_layout, toolbar, 0, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toggle.syncState()
        main_drawer_layout.addDrawerListener(toggle)

        main_view_pager.also {
            // todo 行为古怪，学习rv缓存机制势在必行
            (it.getChildAt(0) as RecyclerView).setItemViewCacheSize(4)
            it.adapter = MainPagerAdapter()
            val titles = resources.getStringArray(R.array.main_title)
            TabLayoutMediator(tab_layout, it) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.checkVersion()
            .`as`(DisposableConverter.dispose(this))
            .subscribe(object :
                SnackbarObserver<VersionEntity>(main_drawer_layout) {
                override fun onNext(it: VersionEntity) {
                    super.onNext(it)
                    if (VersionUtil.needUpdate(this@MainActivity, it.version_code)) {
                        UpdateDialogFragment.newInstance(it)
                            .show(supportFragmentManager, null)
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        main_drawer_layout.removeDrawerListener(toggle)
    }

    override fun onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private inner class MainPagerAdapter : FragmentStateAdapter(this) {

        private val fragmentList by lazy { ArrayList<MainFragment>() }

        init {
            Url.TYPES.forEach {
                fragmentList.add(MainFragment.newInstance(it))
            }
        }

        override fun getItemCount() = fragmentList.size

        override fun createFragment(position: Int) = fragmentList[position]
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}