package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import estyle.teabaike.R
import kotlinx.android.synthetic.main.activity_copyright.*

class CopyrightActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copyright)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, CopyrightActivity::class.java)
            context.startActivity(intent)
        }
    }
}
