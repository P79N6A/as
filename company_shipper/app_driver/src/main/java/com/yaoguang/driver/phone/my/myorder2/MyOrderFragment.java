package com.yaoguang.driver.phone.my.myorder2;


import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentMyOrder2Binding;
import com.yaoguang.driver.file.FileFragment;
import com.yaoguang.driver.phone.my.myorder2.adapter.OrderChildAdapterComplete;
import com.yaoguang.driver.phone.my.myorder2.clientcondition.ClientConditionFragment;
import com.yaoguang.driver.phone.my.myorder2.datecondition.DateConditionFragment;
import com.yaoguang.driver.phone.order.detail.OrderDetailFragment;
import com.yaoguang.greendao.entity.driver.DriverOrderCondition;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.SignInterceptor;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.zhongjh.retrofitdownloadlib.http.DownloadHelper;
import com.zhongjh.retrofitdownloadlib.http.DownloadListener;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yaoguang.lib.common.DateUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.myorder
 * @Description: 我的订单 窗体
 * @date 2018/04/04
 */
public class MyOrderFragment extends BaseFragmentListConditionDataBind<DriverOrderWrapper, DriverOrderCondition, OrderChildAdapterComplete, FragmentMyOrder2Binding> implements MyOrderContract.View, DownloadListener {

    MyOrderContract.Presenter mPresenter = new MyOrderPresenter(this);
    DriverOrderCondition mDriverOrderCondition = new DriverOrderCondition();
    HashMap<String, String> mCompanyIds;// 选择的公司，key:id,value:name

    static final int REQUEST_DATE = 1;
    static final int REQUEST_COMPANY_NAME = 2;

    ViewHolder mHeader;

    DownloadHelper mDownloadHelper = new DownloadHelper("http://www.baseurl.com", this);

    String mFileName = "";

    public static MyOrderFragment newInstance() {
        return new MyOrderFragment();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_order2;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new OrderChildAdapterComplete(getContext());
    }

    @Override
    public DriverOrderCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mDataBinding != null) {
                //mMyOrderCondition.setXXX(mViewBinding.tvValue.getText().toString());
                // 如果时间是无限的，那么就默认查一个月的
                if (mHeader.tvDate.getText().toString().equals(getString(R.string.unlimited))) {
                    mDriverOrderCondition.setDateScopeType("2");
                }
            }
        }
        return mDriverOrderCondition;
    }

    @Override
    public void setConditionView(DriverOrderCondition condition) {

    }

    @Override
    public void init() {
        // 实例化顶部view
        mHeader = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.view_head_my_order, null, false));
        mBaseLoadMoreRecyclerAdapter.addHeaderView(mHeader.view);
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "我的订单", -1, null);
        }

        //绑定数据
//        mDataBinding.setMyOrderCondition(mMyOrderCondition);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE:
                    mDriverOrderCondition = data.getParcelable("DriverOrderCondition");
                    // 查询数据
                    refreshDataAnimation();
                    break;
                case REQUEST_COMPANY_NAME:
                    mCompanyIds = (HashMap<String, String>) data.getSerializable("CompanyIds");
                    // 组成字符串
                    StringBuilder companyidStrs = new StringBuilder();
                    StringBuilder companyidName = new StringBuilder();
                    String companyName = "";
                    if (mCompanyIds != null) {
                        for (Map.Entry<String, String> entry : mCompanyIds.entrySet()) {
                            companyidStrs.append(entry.getKey()).append(",");
                            companyidName.append(entry.getValue()).append(",");
                        }
                    }
                    if (companyidName.length() > 0)
                        companyName = companyidName.substring(0, companyidName.toString().length() - 1);
                    mDriverOrderCondition.setCompanyIds(companyidStrs.toString());
                    mHeader.tvCompanyName.setText(companyName);
                    // 查询数据
                    refreshDataAnimation();
                    break;
            }
        }
        super.onFragmentResult(requestCode, resultCode, data);

    }

    @Override
    public void recyclerViewShowError(String strMessage) {
        // 因为这个特殊的顶部，不做任何改变
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewShowEmpty(boolean isShowRecyclerView) {
        // 因为这个特殊的顶部，不做任何改变
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        mHeader.imgDate.setOnClickListener(v -> startForResult(DateConditionFragment.newInstance(mDriverOrderCondition), REQUEST_DATE));

        mHeader.imgCompanyName.setOnClickListener(v -> startForResult(ClientConditionFragment.newInstance(mCompanyIds), REQUEST_COMPANY_NAME));

        // 列表跳转详情事件
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (AppClickUtil.isDuplicateClick()) return;

            DriverOrderWrapper driverOrderWrapper = (DriverOrderWrapper) item;
            start(OrderDetailFragment.newInstance(driverOrderWrapper.getOrderId(), true, true));
        });

        // 导出
        mHeader.imgExport.setOnClickListener(v -> export());
        mHeader.tvTitleExport.setOnClickListener(v -> export());

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setTotalFreight(BaseResponse<PageList<DriverOrderWrapper>> response, String totalFreight, String startDate, String endDate) {
        mHeader.tvSum.setText(ObjectUtils.parseString(totalFreight));
        mHeader.tvDate.setText(startDate + "～" + endDate);
        mHeader.tvSumCount.setText(response.getTotalCount() + "单");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDownloadHelper.dispose();
    }

    /**
     * 导出
     */
    private void export() {
        mFileName = DateUtils.dateToString(new Date(), YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) + ".xlsx";
        StringBuilder url = new StringBuilder();
        url.append(BuildConfig.ENDPOINT + "driver/order/count/downloads.do?");
        TreeMap<String, String> param_map = new TreeMap<>();
        param_map.put("driverId", DataStatic.getInstance().getId());
        if (!TextUtils.isEmpty(mDriverOrderCondition.getCompanyIds())) {
            param_map.put("companyIds", mDriverOrderCondition.getCompanyIds());
        }
        if (!TextUtils.isEmpty(mDriverOrderCondition.getDateScopeType())) {
            param_map.put("dateScopeType", mDriverOrderCondition.getDateScopeType());
        }
        if (!TextUtils.isEmpty(mDriverOrderCondition.getStartDate())) {
            param_map.put("startDate", mDriverOrderCondition.getStartDate());
        }
        if (!TextUtils.isEmpty(mDriverOrderCondition.getEndDate())) {
            param_map.put("endDate", mDriverOrderCondition.getEndDate());
        }
        param_map = SignInterceptor.getQueryParameter(param_map);
        // 循环param_map添加参数

        for (Map.Entry<String, String> entry : param_map.entrySet()) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }

        mDownloadHelper.downloadFile(url.toString(),
                Environment.getExternalStorageDirectory() + File.separator + "/HuoYunJi", mFileName);

    }

    @Override
    public void onStartDownload() {
        showProgressDialog();
    }

    @Override
    public void onProgress(int i) {

    }

    @Override
    public void onFinishDownload(File file) {
        hideProgressDialog();
        Toast.makeText(BaseApplication.getInstance(), "下载完成", Toast.LENGTH_SHORT).show();

        String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "HuoYunJi" + File.separator + mFileName;
        if (!EasyPermissions.hasPermissions(_mActivity, perms)) {
            EasyPermissions.requestPermissions(_mActivity, "需要访问手机存储权限！", 10086, perms);
        } else {
            start(FileFragment.newInstance(filePath));
        }

    }

    @Override
    public void onFail(Throwable throwable) {
        hideProgressDialog();
    }

    static class ViewHolder {

        View view;

        @BindView(R.id.tvTitleSum)
        TextView tvTitleSum;
        @BindView(R.id.tvSum)
        TextView tvSum;
        @BindView(R.id.vLine)
        View vLine;
        @BindView(R.id.tvSumCount)
        TextView tvSumCount;
        @BindView(R.id.tvTitleSumCount)
        TextView tvTitleSumCount;
        @BindView(R.id.imgExport)
        ImageView imgExport;
        @BindView(R.id.tvTitleExport)
        TextView tvTitleExport;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.imgDate)
        ImageView imgDate;
        @BindView(R.id.tvCompanyName)
        TextView tvCompanyName;
        @BindView(R.id.imgCompanyName)
        ImageView imgCompanyName;
        @BindView(R.id.activity_description_content)
        ConstraintLayout activityDescriptionContent;

        ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

}
