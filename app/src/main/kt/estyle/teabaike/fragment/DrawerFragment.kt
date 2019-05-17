package estyle.teabaike.fragment


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.navigation.NavigationView
import estyle.base.BaseFragment
import estyle.teabaike.R
import estyle.teabaike.activity.CollectionActivity
import estyle.teabaike.activity.CopyrightActivity
import estyle.teabaike.activity.FeedbackActivity
import estyle.teabaike.activity.SearchActivity

class DrawerFragment : BaseFragment(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private var mKeywordEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootView = view as NavigationView
        rootView.setNavigationItemSelectedListener(this)
        val headerView = rootView.getHeaderView(0)
        mKeywordEditText = headerView.findViewById(R.id.keyword_et)
        val searchBtn = headerView.findViewById<Button>(R.id.search_btn)
        searchBtn.setOnClickListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_collection_item -> CollectionActivity.startActivity(context!!)
            R.id.copyright_info_item -> CopyrightActivity.startActivity(context!!)
            R.id.suggestion_item -> FeedbackActivity.startActivity(context!!)
        }
        return true
    }

    override fun onClick(v: View) {
        val keyword = mKeywordEditText!!.text.toString()
        if (!TextUtils.isEmpty(keyword)) {
            SearchActivity.startActivity(context!!, keyword)
        }
    }
}
