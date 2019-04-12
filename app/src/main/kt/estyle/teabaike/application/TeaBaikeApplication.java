package estyle.teabaike.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import estyle.teabaike.dagger.component.DaggerTeaBaikeComponent;
import estyle.teabaike.dagger.component.TeaBaikeComponent;
import estyle.teabaike.dagger.module.DataModule;
import estyle.teabaike.dagger.module.TimerModule;

public class TeaBaikeApplication extends Application {

    private static TeaBaikeApplication sApplication;
    private RefWatcher mRefWatcher;
    private TeaBaikeComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;
        initLeakCanary();
        initDagger();
    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = LeakCanary.install(this);
        }
    }

    private void initDagger() {
        mComponent = DaggerTeaBaikeComponent
                .builder()
                .dataModule(new DataModule(this))
                .timerModule(new TimerModule(this))
                .build();
    }

    public static TeaBaikeApplication getInstance() {
        return sApplication;
    }

    public TeaBaikeComponent getTeaBaikeComponent() {
        return mComponent;
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }
}
