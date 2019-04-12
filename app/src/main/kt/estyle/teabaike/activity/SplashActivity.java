package estyle.teabaike.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

import estyle.teabaike.application.TeaBaikeApplication;
import estyle.teabaike.manager.TimerManager;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {

    @Inject
    TimerManager mTimerManager;

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TeaBaikeApplication.getInstance().getTeaBaikeComponent().inject(this);

        mDisposable = mTimerManager.splash()
                .subscribe(isFirstLogin -> {
                    if (isFirstLogin) {
                        WelcomeActivity.startActivity(SplashActivity.this);
                        SharedPreferences sharedPreferences = getSharedPreferences(TimerManager.CONFIG_NAME,
                                Context.MODE_PRIVATE);
                        sharedPreferences.edit()
                                .putBoolean("is_first_login", false)
                                .apply();
                    } else {
                        MainActivity.startActivity(SplashActivity.this);
                    }
                    finish();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
