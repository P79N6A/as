package com.yaoguang.appcommon.phone.order.feedback;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.file.VideoUrl;
import com.yaoguang.lib.net.bean.PageList;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import ezy.ui.layout.LoadingLayout;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单反馈列表
 * Created by 韦理英 on 2017/5/17.
 * <p>
 * 查看故障反馈、查看动态记录
 * update zhongjh
 * data 2018-03-25
 */
public abstract class BaseOrderFeedBackListFragment extends BaseFragment2 implements OrderFeedBackListContacts.View {

    protected static final String ORDERID = "orderId";
    protected static final String FLAG_TYPE = "type";

    InitialView mInitialView;
    OrderFeedBackListAdapter orderFeedBackListAdapter;

    OrderFeedBackListContacts.Presenter mOrderFeedBackListPresenter;

    public CompositeDisposable mCompositeDisposable;

    /**
     * 0是故障反馈列表
     * 1是节点提交信息
     */
    private int type;
    private String orderId;

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mOrderFeedBackListPresenter.getList(orderId, type);
    }

    public BasePresenter getPresenter() {
        return mOrderFeedBackListPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_feedback_list;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mCompositeDisposable = new CompositeDisposable();
        Bundle bundle = getArguments();
        if (bundle != null) {
            orderId = getArguments().getString(ORDERID);
            type = getArguments().getInt(FLAG_TYPE);
        }
        mOrderFeedBackListPresenter = new OrderFeedBackListPresenter(this);

        mInitialView = new InitialView(view);
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void show(PageList<DriverOrderNodeFeedback> value) {
        mCompositeDisposable.add(Observable.create((ObservableOnSubscribe<ArrayList<DriverOrderNodeFeedback>>) e -> {

            for (DriverOrderNodeFeedback driverOrderNodeFeedback : value.getResult()) {
                if (!TextUtils.isEmpty(driverOrderNodeFeedback.getVideoUrl())) {
                    Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(driverOrderNodeFeedback.getVideoUrl(), MediaStore.Images.Thumbnails.MICRO_KIND);
                    if (mBitmapVideo == null) break;
                    final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final byte[] bytes = stream.toByteArray();
                    driverOrderNodeFeedback.setVideo(bytes);
                }
            }

            e.onNext(value.getResult());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ArrayList<DriverOrderNodeFeedback> value2) -> {
                    //列表初始化
                    orderFeedBackListAdapter = new OrderFeedBackListAdapter(type);
                    orderFeedBackListAdapter.appendToList(value2);

                    RecyclerViewUtils.initPage(mInitialView.rlView, orderFeedBackListAdapter, null, getContext(), false);
                    mInitialView.rlView.setBackgroundResource(R.color.white);
                    mInitialView.refreshLayout.setEnableRefresh(false);
                    mInitialView.refreshLayout.setEnableLoadMore(false);

                    // 判断是否有数据
                    if (value2.size() <= 0) {
                        mInitialView.loading.showEmpty();
                    }else{
                        mInitialView.loading.showContent();
                    }

                }));
    }


    public class InitialView {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public AppBarLayout app_bar;
        public RecyclerView rlView;
        public LoadingLayout loading;
        public SmartRefreshLayout refreshLayout;

        InitialView(View view) {
            initView(view);
        }

        void initView(View view) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.app_bar = (AppBarLayout) rootView.findViewById(R.id.appBar);
            this.rlView = (RecyclerView) rootView.findViewById(R.id.rlView);
            this.loading = (LoadingLayout) rootView.findViewById(R.id.loading);
            this.refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refreshLayout);

            switch (type) {
                case 0:
                    toolbar_title.setText(R.string.look_feedback);
                    break;
//                case 1:
                default:
                    toolbar_title.setText(R.string.look_noderecord);
                    break;
            }

            imgReturn.setOnClickListener(v -> pop());
        }
    }
}
