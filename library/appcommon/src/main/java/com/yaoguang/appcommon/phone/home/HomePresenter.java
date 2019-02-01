package com.yaoguang.appcommon.phone.home;

import com.yaoguang.datasource.common.SysMsgDataSource;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.ApiDefault;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.common.AppDataSource;
import com.yaoguang.greendao.entity.AliWeatherComplex;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.SpecialMsg;
import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.datasource.api.WeatherApi;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页控制层
 * Created by zhongjh on 2018/1/22.
 */
public class HomePresenter extends BasePresenter implements HomeContact.Presenter {

    private HomeContact.View mView;
    private AppDataSource mAppDataSource = new AppDataSource();
    private SysMsgDataSource mSysMsgDataSource = new SysMsgDataSource();


    HomePresenter(HomeContact.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void initBannerData() {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        mAppDataSource.getBannerPic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<BannerPic>>>(mCompositeDisposable) {

                    @Override
                    public void onNext(BaseResponse<PageList<BannerPic>> response) {
                        initMessageBannerData();
                        super.onNext(response);
                    }

                    @Override
                    public void onSuccess(BaseResponse<PageList<BannerPic>> response) {
                        mView.initBannerView(response.getResult().getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.finishRefreshing();
                    }
                });
    }

    @Override
    public void initMessageBannerData() {
        mAppDataSource.specialMsgList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<SpecialMsg>>>(mCompositeDisposable) {

                    @Override
                    public void onError(Throwable e) {
                        mView.finishRefreshing();
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.finishRefreshing();
                    }

                    @Override
                    public void onSuccess(BaseResponse<List<SpecialMsg>> response) {
                        mView.initMessageBannerView(response.getResult());
                        mView.finishRefreshing();
                    }

                });
    }

    @Override
    public void initMessageData() {
        mSysMsgDataSource.unReadNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HashMap<String, Integer>>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<HashMap<String, Integer>> response) {
                        mView.initMessageView(response.getResult());
                    }
                });
    }

//    @Override
//    public void initWeatherData(double lat, double lon) {
//        WeatherApi weatherApi = ApiDefault.getInstance().retrofit.create(WeatherApi.class);
//        weatherApi.getWeather2("APPCODE ebbefba3b9224a5389e9f18298e954a0", "3", ObjectUtils.parseString(lat), ObjectUtils.parseString(lon), "1", "0", "0", "0", "1")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<AliWeatherComplex>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mCompositeDisposable.add(d);
//                        mView.setWeatherText("加载中...");
//                    }
//
//                    @Override
//                    public void onNext(AliWeatherComplex aliWeather) {
//                        mView.initWeatherView(aliWeather);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.setWeatherText("加载失败");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    @Override
    public void initDialogData() {
        mSysMsgDataSource.getPopUpMsg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<SysMsg>>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<List<SysMsg>> response) {
                        mView.initDialogDataView(response.getResult());
                    }

                });
    }

    @Override
    public void updatePopUpData(String msgId) {
        mSysMsgDataSource.updatePopUp(msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.removewSysMsg();
                    }

                });
    }

}
