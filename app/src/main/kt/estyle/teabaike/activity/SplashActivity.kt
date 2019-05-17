package estyle.teabaike.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.teabaike.viewmodel.SplashViewModel

class SplashActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[SplashViewModel::class.java] }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.delay(2)
            .`as`<ObservableSubscribeProxy<Boolean>>(DisposableConverter.dispose(this))
            .subscribe { isFirstLogin ->
                if (isFirstLogin) {
                    WelcomeActivity.startActivity(this)
                } else {
                    MainActivity.startActivity(this)
                }
                finish()
            }
    }
}