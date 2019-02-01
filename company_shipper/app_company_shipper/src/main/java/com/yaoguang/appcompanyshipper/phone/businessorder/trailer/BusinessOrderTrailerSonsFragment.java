package com.yaoguang.appcompanyshipper.phone.businessorder.trailer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerSonsBinding;
import com.yaoguang.greendao.entity.AppTruckSono;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AoHaiUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.map.common.ToastUtil;

import java.util.ArrayList;


/**
 * 编辑货柜
 * Created by zhongjh on 2017/10/9.
 */
public class BusinessOrderTrailerSonsFragment extends BaseFragmentDataBind<FragmentBusinessOrderTrailerSonsBinding> {

    ArrayList<AppTruckSono> mAppTruckSonos;
    ArrayList<MenuEntity> mSonosMenuEntity;
    DialogHelper mDialogHelper;
    int type = -1;
    int position = -1;
    String compyId;

    // 添加
    public static final int ADD = 0;
    // 修改
    public static final int EDIT = 1;

    SweetSheet mRegionIdSweetSheet;

    public static BusinessOrderTrailerSonsFragment newInstance(ArrayList<AppTruckSono> appTruckSonos, ArrayList<MenuEntity> sonosMenuEntity, int type, int position, String compyId) {
        BusinessOrderTrailerSonsFragment businessOrderTrailerSonsFragment = new BusinessOrderTrailerSonsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("appTruckSonos", appTruckSonos);
        bundle.putParcelableArrayList("sonosMenuEntity", sonosMenuEntity);
        bundle.putInt("type", type);
        bundle.putInt("position", position);
        bundle.putString("compyId", compyId);
        businessOrderTrailerSonsFragment.setArguments(bundle);
        return businessOrderTrailerSonsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mAppTruckSonos = args.getParcelableArrayList("appTruckSonos");
            mSonosMenuEntity = args.getParcelableArrayList("sonosMenuEntity");
            type = args.getInt("type");
            position = args.getInt("position");
            compyId = args.getString("compyId");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_trailer_sons;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "编辑货柜", -1, null);

        //初始化柜型
        mRegionIdSweetSheet = new SweetSheet(mDataBinding.flMain);
        mRegionIdSweetSheet.setMenuList(mSonosMenuEntity);
        mRegionIdSweetSheet.setTitle("柜型");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
        mRegionIdSweetSheet.setDelegate(recyclerViewDelegate);
        mRegionIdSweetSheet.setBackgroundEffect(new DimEffect(4));
        mRegionIdSweetSheet.setOnMenuItemClickListener((position, menuEntity1) -> {
            mDataBinding.tvValueContId.setText(menuEntity1.title);
            return true;
        });

        //柜号只能输入数字和字母
        TextViewUtils.setAlphaNumeric(mDataBinding.etValueContNo);
        // 自动小写转大写
        mDataBinding.etValueContNo.setTransformationMethod(TextViewUtils.replacementTransformationMethod);
        //封号只能输入数字和字母
        TextViewUtils.setAlphaNumeric(mDataBinding.etValueSealNo);

        initData();
    }

    @Override
    public void initListener() {
        //选择柜型
        mDataBinding.rlRegionId.setOnClickListener(v -> mRegionIdSweetSheet.show());
        //提柜点 - 码头
        mDataBinding.rlCarryPort.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFODOCK);
            } else {
                if (TextUtils.isEmpty(compyId)) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择关联的公司");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFODOCK, compyId);
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFODOCK));
        });
        //还柜点 - 码头
        mDataBinding.rlGetPort.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFODOCK);
            } else {
                if (TextUtils.isEmpty(compyId)) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择关联的公司");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFODOCK, compyId);
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFODOCK) + 1);
        });
        //点击确定提交数据
        mDataBinding.cpbConfirm.setOnClickListener(v -> {
            // 检查提柜点和还柜点不能为空
            if (TextUtils.isEmpty(mDataBinding.tvValueCarryPort.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "提柜点不能为空", Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueGetPort.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "还柜点不能为空", Toast.LENGTH_LONG).show();
            }

            // 检查是否有重复的柜号和封号
            for (AppTruckSono item : mAppTruckSonos) {
                if (item.getContNo().equals(mDataBinding.etValueContNo.getText().toString()) && !mDataBinding.etValueContNo.getText().toString().equals(mAppTruckSonos.get(position).getContNo())) {
                    Toast.makeText(BaseApplication.getInstance(), "柜号不能重复，请重新输入", Toast.LENGTH_LONG).show();
                    mDataBinding.etValueContNo.requestFocus();
                    return;
                }
                if (item.getSealNo().equals(mDataBinding.etValueSealNo.getText().toString()) && !mDataBinding.etValueSealNo.getText().toString().equals(mAppTruckSonos.get(position).getSealNo())) {
                    Toast.makeText(BaseApplication.getInstance(), "封号不能重复，请重新输入", Toast.LENGTH_LONG).show();
                    mDataBinding.etValueSealNo.requestFocus();
                    return;
                }
            }

            // 检查柜号是否有效
            if (!TextUtils.isEmpty(mDataBinding.etValueContNo.getText().toString()) && !AoHaiUtils.checkContNo(mDataBinding.etValueContNo.getText().toString())) {
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(getContext(), null, mDataBinding.etValueContNo.getText().toString() + " 是无效柜号,是否重新输入?", "是", "否", new CommonDialog.Listener() {
                        @Override
                        public void ok(String msg) {
                            mDialogHelper.hideDialog();
                            mDataBinding.etValueContNo.requestFocus();
                        }

                        @Override
                        public void cancel() {
                            mDialogHelper.hideDialog();
                            comfit();
                        }
                    });
                mDialogHelper.show();
            } else {
                comfit();
            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_INFODOCK:
                    mDataBinding.tvValueCarryPort.setText(data.getString("name"));
                    break;
                case (PublicSearchInteractorImpl.TYPE_INFODOCK + 1):
                    mDataBinding.tvValueGetPort.setText(data.getString("name"));
                    break;
            }
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (type == EDIT) {
            //赋值
            AppTruckSono appTruckSono = mAppTruckSonos.get(position);
            mDataBinding.tvValueCarryPort.setText(appTruckSono.getCarryPort());
            mDataBinding.tvValueContId.setText(appTruckSono.getContId());
            mDataBinding.etValueContNo.setText(appTruckSono.getContNo());
            mDataBinding.tvValueGetPort.setText(appTruckSono.getGetPort());
            mDataBinding.etValueSealNo.setText(appTruckSono.getSealNo());
        } else if (type == ADD) {
            //判断索引是否大于等于0，如果是，就带参数，否则没事发生
            if (position >= 0) {
                AppTruckSono appTruckSono = mAppTruckSonos.get(position);
                mDataBinding.tvValueCarryPort.setText(appTruckSono.getCarryPort());
                mDataBinding.tvValueContId.setText(appTruckSono.getContId());
                mDataBinding.tvValueGetPort.setText(appTruckSono.getGetPort());
            }

        }
    }

    /**
     * 提交数据
     */
    private void comfit() {
        //判断是添加还是编辑
        Bundle bundle = new Bundle();
        if (type == EDIT) {
            bundle.putInt("position", position);
            bundle.putInt("type", EDIT);
        } else {
            bundle.putInt("type", ADD);
        }
        AppTruckSono appTruckSono = new AppTruckSono();
        appTruckSono.setCarryPort(mDataBinding.tvValueCarryPort.getText().toString());
        appTruckSono.setContId(mDataBinding.tvValueContId.getText().toString());
        appTruckSono.setContNo(mDataBinding.etValueContNo.getText().toString());
        appTruckSono.setGetPort(mDataBinding.tvValueGetPort.getText().toString());
        appTruckSono.setSealNo(mDataBinding.etValueSealNo.getText().toString());
        bundle.putParcelable("appTruckSono", appTruckSono);
        BusinessOrderTrailerSonsFragment.this.setFragmentResult(RESULT_OK, bundle);
        //关闭本身
        pop();
    }

}
