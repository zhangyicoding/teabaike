package estyle.teabaike.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import estyle.teabaike.activity.CollectionActivity;
import estyle.teabaike.activity.ContentActivity;
import estyle.teabaike.activity.SearchActivity;
import estyle.teabaike.activity.SplashActivity;
import estyle.teabaike.adapter.CollectionAdapter;
import estyle.teabaike.dagger.module.DataModule;
import estyle.teabaike.dagger.module.TimerModule;
import estyle.teabaike.fragment.MainFragment;
import estyle.teabaike.widget.HeadlineHeaderView;

@Singleton
@Component(modules = {DataModule.class, TimerModule.class})
public interface TeaBaikeComponent {

    void inject(MainFragment fragment);

    void inject(HeadlineHeaderView view);

    void inject(ContentActivity activity);

    void inject(SearchActivity activity);

    void inject(CollectionActivity activity);

    void inject(CollectionAdapter adapter);

    void inject(SplashActivity activity);

}
