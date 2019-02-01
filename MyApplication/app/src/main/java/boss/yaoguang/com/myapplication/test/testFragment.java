package boss.yaoguang.com.myapplication.test;

import android.os.Bundle;
import android.view.View;

import boss.yaoguang.com.myapplication.base.MvpBaseFragment;

/**
 * @author zhongjh
 * @Package boss.yaoguang.com.myapplication.test
 * @Description: $description
 * @date 2018/01/03
 */
public class testFragment extends BaseFragment implements testContract.View{

    private testContract.Presenter mPresenter;

    public static testFragment getInstance(){
        return new testFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void setPresenter(testContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }
}
