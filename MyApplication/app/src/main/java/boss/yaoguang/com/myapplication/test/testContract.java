package boss.yaoguang.com.myapplication.test;

import boss.yaoguang.com.myapplication.base.BasePresenter;
import boss.yaoguang.com.myapplication.base.BaseView;

/**
 * @author zhongjh
 * @Package boss.yaoguang.com.myapplication.test
 * @Description: $description
 * @date 2018/01/03
 */
public interface testContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
