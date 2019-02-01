package com.yaoguang.driver.main;


import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.data.source.BaseDataSource;

/**
 * Created by wly on 2017/8/8.
 */

public interface MainContact {


    interface MainView {
        void startLoginActivity();
    }

    abstract class MainPresenter extends BasePresenter<MainView, BaseDataSource> {
    }


}
