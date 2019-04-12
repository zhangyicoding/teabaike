package estyle.teabaike.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import estyle.teabaike.R;
import estyle.teabaike.activity.ContentActivity;
import estyle.teabaike.application.TeaBaikeApplication;
import estyle.teabaike.bean.HeadlineBean;
import estyle.teabaike.databinding.ViewHeadlineHeaderBinding;
import estyle.teabaike.manager.RetrofitManager;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HeadlineHeaderView extends FrameLayout {

    private ViewHeadlineHeaderBinding binding;

    private Disposable mHttpDisposable;

    @Inject
    RetrofitManager mNetworkProvider;

    public HeadlineHeaderView(@NonNull Context context) {
        super(context);
        TeaBaikeApplication.getInstance().getTeaBaikeComponent().inject(this);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.view_headline_header, this, true);
    }

    public void loadData() {
        mHttpDisposable = mNetworkProvider.loadHeadlineData()
                .doOnNext(dataBeans -> {
                    binding.pointsView.setPointCount(dataBeans.size());
                    binding.headlineTextView.setText(dataBeans.get(0).getTitle());
                    binding.bannerView.setOnBannerSelectedListener(position -> {
                        binding.headlineTextView.setText(dataBeans.get(position).getTitle());
                        binding.pointsView.setSelectedPosition(position);
                    });
                    binding.bannerView.setOnBannerClickListener(position ->
                            ContentActivity.startActivity(getContext(),
                                    Long.parseLong(dataBeans.get(position).getId()),
                                    true));
                })
                .concatMap(Observable::fromIterable)
                .map(HeadlineBean.DataBean::getImage)
                .toList()
                .subscribe(imagePathList -> binding.bannerView.loadImages(imagePathList,
                        (path, view) -> Glide.with(getContext())
                                .load(path)
                                .into(view)),
                        throwable -> Toast.makeText(getContext(),
                                R.string.fail_connect,
                                Toast.LENGTH_SHORT).show());
    }

    public void onDestroy() {
        mHttpDisposable.dispose();
        binding.bannerView.onDestroy();
    }

}
