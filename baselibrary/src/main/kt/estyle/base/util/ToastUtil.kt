package estyle.base.util

import android.content.Context
import android.widget.Toast

object ToastUtil {

    lateinit var context: Context

    fun init(applicationContext: Context) {
        context = applicationContext.applicationContext
    }

    fun showLong(text: String?) {
        show(text, Toast.LENGTH_LONG)
    }

    fun showShort(text: String?) {
        show(text, Toast.LENGTH_SHORT)
    }

    private fun show(text: String?, duration: Int) {
        context ?: return
        text ?: return
        Toast.makeText(context, text, duration).show()
    }
}