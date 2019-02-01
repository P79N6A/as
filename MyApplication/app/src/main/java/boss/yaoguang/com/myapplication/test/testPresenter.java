package boss.yaoguang.com.myapplication.test;

import android.content.Context;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author zhongjh
 * @Package boss.yaoguang.com.myapplication.test
 * @Description: $description
 * @date 2018/01/03
 */
public class testPresenter implements testContract.Presenter{

    private Context mContext;
    private testContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public testPresenter(Context context, testContract.View view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

}
