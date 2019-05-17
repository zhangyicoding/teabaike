package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import estyle.base.BaseActivity
import estyle.teabaike.R
import estyle.teabaike.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

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