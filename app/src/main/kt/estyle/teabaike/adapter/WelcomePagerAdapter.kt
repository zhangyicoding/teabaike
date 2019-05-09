package estyle.teabaike.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import estyle.teabaike.R
import java.util.*

class WelcomePagerAdapter(context: Context) : PagerAdapter() {

    private val mViewList: MutableList<View>
    private var mListener: OnButtonClickListener? = null

    init {
        mViewList = ArrayList()
        val imgIds = intArrayOf(R.drawable.welcome1, R.drawable.welcome2)
        var view: View
        for (imgId in imgIds) {
            view = View(context)
            view.setBackgroundResource(imgId)
            mViewList.add(view)
        }
        view = LayoutInflater.from(context).inflate(R.layout.view_welcome3, null)
        mViewList.add(view)
    }

    override fun getCount(): Int {
        return mViewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        container.addView(mViewList[position])
        return mViewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mViewList[position])
    }

    fun setOnButtonClickListener(listener: OnButtonClickListener) {
        this.mListener = listener
        mViewList[mViewList.size - 1]
            .findViewById<View>(R.id.main_btn)
            .setOnClickListener { v -> mListener!!.onButtonClick(v) }
    }

    interface OnButtonClickListener {
        fun onButtonClick(v: View)
    }

}
