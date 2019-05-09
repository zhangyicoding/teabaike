package estyle.teabaike.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zhangyi on 2018/4/3.
 * <p>
 * loadImages：设置图片路径并启动自动轮播，参数ImageCallback指定图片加载方式
 * setCircling：设置是否开启自动轮播，默认开启
 * isCircling：获取当前轮播状态
 * setOnBannerClickListener：设置图片点击监听
 * setOnBannerSelectedListener：设置图片位置监听
 * 在销毁生命周期中需要调用onDestroy()释放内存
 */
@Deprecated
public class BannerViewOld extends ViewPager {

    public static final String TAG = BannerViewOld.class.getSimpleName();

    private static final int MSG_AUTO_CIRCLE = 0;

    private int mCurrentPosition;// ViewPager当前位置

    private boolean mIsCircling = true;// 是否正在循环
    private boolean mNeedCircleAgain = true;// 是否需要再次循环，防止重复叠加循环

    private long mDelayMillis = 4000L;// 轮播时间间隔

    private long mDownTime;// 手指按下的时间

    private int mRealImageCount;// 图片真实数量

    private int mRealPosition;// 真实展示位置

    private BannerAdapter mAdapter;

    private ImageCallback mImageCallback;

    private OnCircleListener mOnCircleListener;
    private onBannerClickListener mOnBannerClickListener;

    private OnBannerSelectedListener mOnBannerSelectedListener;

    private WeakHandler mHandler;

    public BannerViewOld(Context context) {
        super(context);
    }

    public BannerViewOld(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnBannerClickListener(onBannerClickListener onBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener;
    }

    public void setOnBannerSelectedListener(OnBannerSelectedListener onBannerSelectedListener) {
        mOnBannerSelectedListener = onBannerSelectedListener;
    }

    // 获取是否正在轮播的状态
    public boolean isCircling() {
        return mIsCircling;
    }

    // 设置是否启用轮播
    public void setCircling(boolean circling) {
        if (mIsCircling == circling) return;
        mIsCircling = circling;
        if (mIsCircling) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    // 获取展示图片对应位置
    public int getPosition() {
        return mRealPosition;
    }

    // 设置图片路径
    public void loadImages(List<String> imagePathList, ImageCallback callback) {
        if (callback == null || imagePathList == null || imagePathList.size() == 0) return;

        setCircling(false);
        mImageCallback = callback;
        mRealImageCount = imagePathList.size();

        initAdapter(imagePathList);
        initCircleListener();
    }

    // 初始化ViewPager适配器
    private void initAdapter(List<String> imagePathList) {
        if (mAdapter != null) {
            mAdapter.refreshImages(imagePathList);
        } else {
            mAdapter = new BannerAdapter(this, imagePathList);
            setAdapter(mAdapter);
            int startPosition = 20;// 不能设置为Integer.MAX_VALUE/2，否则有严重卡顿
            mCurrentPosition = startPosition - startPosition % imagePathList.size();
            setCurrentItem(mCurrentPosition, false);
        }
    }

    // 初始化无限自动轮播监听器
    private void initCircleListener() {
        if (mRealImageCount == 1) return;
        if (mHandler == null) {
            mHandler = new WeakHandler(this);
            mOnCircleListener = new OnCircleListener(this);
            addOnPageChangeListener(mOnCircleListener);
        }
        setCircling(true);
    }

    // 启动定时
    private void startTimer() {
        if (mHandler == null) return;

        if (mNeedCircleAgain) {
            mNeedCircleAgain = false;
            mHandler.sendEmptyMessageDelayed(MSG_AUTO_CIRCLE, mDelayMillis);
        }
    }

    // 停止定时
    private void stopTimer() {
        if (mHandler == null) return;

        mNeedCircleAgain = true;
        mHandler.removeMessages(MSG_AUTO_CIRCLE);
    }

    // 销毁生命周期中调用，释放内存
    public void onDestroy() {
        if (mImageCallback != null) mImageCallback = null;
        if (mOnBannerClickListener != null) mOnBannerClickListener = null;
        if (mOnBannerSelectedListener != null) mOnBannerSelectedListener = null;
        if (mAdapter != null) mAdapter = null;

        if (mHandler != null) {
            mHandler.removeMessages(MSG_AUTO_CIRCLE);
            mHandler = null;

            removeOnPageChangeListener(mOnCircleListener);
            mOnCircleListener = null;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownTime = System.currentTimeMillis();
                stopTimer();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:// ViewPager的child可点击时
                long upTime = System.currentTimeMillis();
                startTimer();
                if (upTime - mDownTime >= 500) return true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:// ViewPager的child不可点击时
                startTimer();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private static class WeakHandler extends Handler {

        private WeakReference<BannerViewOld> mWeakReference;

        WeakHandler(BannerViewOld BannerViewOld) {
            mWeakReference = new WeakReference<>(BannerViewOld);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_AUTO_CIRCLE) {
                BannerViewOld BannerViewOld = mWeakReference.get();
                if (BannerViewOld == null) return;

                BannerViewOld.setCurrentItem(++BannerViewOld.mCurrentPosition);
                sendEmptyMessageDelayed(MSG_AUTO_CIRCLE, BannerViewOld.mDelayMillis);
            }
        }
    }

    private static class OnCircleListener extends SimpleOnPageChangeListener {

        private BannerViewOld mBannerViewOld;

        OnCircleListener(BannerViewOld BannerViewOld) {
            mBannerViewOld = BannerViewOld;
        }

        @Override
        public void onPageSelected(int position) {
            mBannerViewOld.mCurrentPosition = position;
            if (mBannerViewOld.mOnBannerSelectedListener != null) {
                mBannerViewOld.mRealPosition = position % mBannerViewOld.mRealImageCount;
                if (mBannerViewOld.mRealImageCount == 2 || mBannerViewOld.mRealImageCount == 3) {
                    mBannerViewOld.mRealPosition %= mBannerViewOld.mRealImageCount;
                }
                mBannerViewOld.mOnBannerSelectedListener.onBannerSelected(mBannerViewOld.mRealPosition);
            }
        }
    }

    private static class BannerAdapter extends PagerAdapter implements OnClickListener {

        private List<String> mImagePathList;
        private BannerViewOld mBannerViewOld;

        BannerAdapter(BannerViewOld BannerViewOld, List<String> imagePathList) {
            mBannerViewOld = BannerViewOld;
            mImagePathList = imagePathList;
        }

        private void refreshImages(List<String> imagePathList) {
            mImagePathList = imagePathList;
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            if (mBannerViewOld.mOnBannerClickListener == null) return;

            mBannerViewOld.mOnBannerClickListener.onBannerClick(v.getId());
        }

        @Override
        public int getCount() {
            return mImagePathList.size() == 1 ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = position % mImagePathList.size();
            ImageView imageView = new ImageView(mBannerViewOld.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mBannerViewOld.mImageCallback.onLoadImage(mImagePathList.get(realPosition), imageView);
            imageView.setId(realPosition);
            imageView.setOnClickListener(this);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((ImageView) object));
        }

    }

    public interface ImageCallback {
        void onLoadImage(String path, ImageView view);
    }

    public interface onBannerClickListener {
        void onBannerClick(int position);
    }

    public interface OnBannerSelectedListener {
        void onBannerSelected(int position);
    }
}
