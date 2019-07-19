package estyle.teabaike.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.base.BaseActivity
import estyle.base.rxjava.DisposableConverter
import estyle.teabaike.config.RoutePath
import estyle.teabaike.viewmodel.SplashViewModel

class SplashActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[SplashViewModel::class.java] }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.delay(2)
            .`as`<ObservableSubscribeProxy<Boolean>>(DisposableConverter.dispose(this))
            .subscribe { isFirstLogin ->
                if (isFirstLogin) {
                    aRouter.build(RoutePath.LAUNCHER_WELCOME).navigation()
                } else {
                    aRouter.build(RoutePath.LAUNCHER_MAIN).navigation()
                }
                finish()
            }
    }
}