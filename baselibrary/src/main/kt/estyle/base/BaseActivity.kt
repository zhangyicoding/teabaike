package estyle.base

import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

open class BaseActivity : AppCompatActivity() {
    protected val aRouter by lazy { ARouter.getInstance() }
}