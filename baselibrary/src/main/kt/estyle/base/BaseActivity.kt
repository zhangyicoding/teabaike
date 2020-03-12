package estyle.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter

open class BaseActivity : AppCompatActivity(), HasDefaultViewModelProviderFactory {
    protected val aRouter by lazy { ARouter.getInstance() }

    override fun getDefaultViewModelProviderFactory() =
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
}