package estyle.teabaike.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import estyle.teabaike.config.Url
import estyle.teabaike.fragment.MainFragment
import java.util.*

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList by lazy { ArrayList<MainFragment>() }

    init {
        for (i in Url.TITLES.indices) {
            fragmentList.add(MainFragment.newInstance(Url.TITLES[i], Url.TYPES[i]))
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].title
    }
}
