package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.base.rxjava.observer.SnackbarObserver
import estyle.base.util.VersionUtil
import estyle.teabaike.R
import estyle.teabaike.adapter.MainPagerAdapter
import estyle.teabaike.entity.VersionEntity
import estyle.teabaike.fragment.dialog.UpdateDialogFragment
import estyle.teabaike.viewmodel.MainListViewModel
import estyle.teabaike.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    private val toggle by lazy { ActionBarDrawerToggle(this, main_drawer_layout, toolbar, 0, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toggle.syncState()
        main_drawer_layout.addDrawerListener(toggle)

        main_view_pager.adapter = MainPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(main_view_pager)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.checkVersion()
            .`as`<ObservableSubscribeProxy<VersionEntity.DataEntity>>(
                DisposableConverter.dispose(this)
            )
            .subscribe(object :
                SnackbarObserver<VersionEntity.DataEntity>(main_drawer_layout) {
                override fun onNext(it: VersionEntity.DataEntity) {
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

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}