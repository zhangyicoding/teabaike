package estyle.teabaike.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import estyle.teabaike.manager.TimerManager;

@Module
public class TimerModule {

    private Context mContext;

    public TimerModule(Context context) {
        this.mContext = context;
    }

    @Singleton
    @Provides
    public TimerManager provideTimerManager() {
        return new TimerManager(mContext);
    }

}
