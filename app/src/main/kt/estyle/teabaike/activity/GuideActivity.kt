package estyle.teabaike.activity

import android.content.Context
import android.os.Bundle
import android.util.SparseIntArray
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import estyle.base.BaseActivity
import estyle.base.widget.adapter.BaseAdapter
import estyle.teabaike.R
import estyle.teabaike.config.RoutePath
import estyle.teabaike.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.btn_guide.view.*
import kotlinx.android.synthetic.main.item_guide.view.*

@Route(path = RoutePath.LAUNCHER_GUIDE)
class GuideActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        guide_view_pager.adapter = GuidePagerAdapter(
            arrayListOf(R.drawable.guide1, R.drawable.gudie2, R.drawable.guide3)
        ).also { dots_view.dotCount = it.itemCount }
        guide_view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dots_view.selectedPosition = position
            }
        })
    }

    private inner class GuidePagerAdapter(items: List<Int>) : BaseAdapter<Int>(items) {
        override fun initLayoutId(multiTypes: SparseIntArray) = R.layout.item_guide

        override fun onBind(itemView: View, item: Int, position: Int, viewType: Int) {
            itemView.setBackgroundResource(item)
            if (position == items.size - 1) {
                itemView.main_stub.inflate()
                itemView.main_btn.setOnClickListener {
                    // todo config 和is_first_login需要规范一下位置
                    getSharedPreferences("config", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(SplashViewModel.IS_FIRST_LOGIN, false)
                        .apply()
                    MainActivity.startActivity(this@GuideActivity)
                    finish()
                }
            }
        }
    }
}