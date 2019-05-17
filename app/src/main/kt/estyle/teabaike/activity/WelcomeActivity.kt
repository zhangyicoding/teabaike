package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import estyle.base.BaseActivity
import estyle.teabaike.R
import estyle.teabaike.adapter.WelcomePagerAdapter
import estyle.teabaike.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity(),
    WelcomePagerAdapter.OnButtonClickListener, ViewPager.OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initView()
    }

    private fun initView() {
        val adapter = WelcomePagerAdapter(this)
        adapter.setOnButtonClickListener(this)
        welcome_view_pager.adapter = adapter
        welcome_view_pager.addOnPageChangeListener(this)
        dots_view.dotCount = adapter.count
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        dots_view.selectedPosition = position
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onButtonClick(v: View) {
        // todo config 和is_first_login需要规范一下位置
        applicationContext.getSharedPreferences("config", Context.MODE_PRIVATE)
            .edit()
            .putBoolean(SplashViewModel.IS_FIRST_LOGIN, false)
            .apply()
        MainActivity.startActivity(this)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        welcome_view_pager.removeOnPageChangeListener(this)
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, WelcomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}