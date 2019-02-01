package com.yaoguang.driver.my.setting.accountsecurity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.yaoguang.appcommon.common.base.BaseFragmentListV2;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;
import com.yaoguang.driver.my.modify.password.ModifyPassFragment;
import com.yaoguang.driver.my.bindphone.BindNewPhoneFragment;
import com.yaoguang.driver.util.Injection;

import java.util.Arrays;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/23
 * 描    述：
 * 帐户安全
 * =====================================
 */

public class AccountSecurityFragment extends BaseFragmentListV2 implements BaseLoadMoreRecyclerAdapter.OnRecyclerViewItemClickListener {
    private String[] items = new String[]{"更换手机号码", "修改登录密码"};
    private String[] mArrayParam = new String[]{"", "修改"};

    private AccountSecurityAdapter mAccountSecurityAdapter;
    private ImageView imgReturn;
    private TextView toolbar_title;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    public static AccountSecurityFragment newInstance() {

        Bundle args = new Bundle();

        AccountSecurityFragment fragment = new AccountSecurityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_account_security, null);


        initView(inflate);
        String mobile = "";
        if (getContext() != null)
            mobile = Injection.provideDriverRepository(getContext()).getDriver().getMobile();
        mArrayParam[0] = mobile;

        mAccountSecurityAdapter = new AccountSecurityAdapter(mArrayParam);
        initSwipeRecyclerView(inflate, mAccountSecurityAdapter);
        mAccountSecurityAdapter.appendToList(Arrays.asList(items));
        mAccountSecurityAdapter.notifyDataSetChanged();
        mAccountSecurityAdapter.setOnItemClickListener(this);

        return inflate;
    }

    @Override
    public void onItemClick(View itemView, Object item, int position) {
        String value = ((TextView) itemView.findViewById(R.id.title)).getText().toString();
        switch (value) {
            case "更换手机号码":
                start(BindNewPhoneFragment.newInstance());
                break;
            case "修改登录密码":
                start(ModifyPassFragment.newInstance());
                break;
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void refreshData() {

    }

    private void initView(View inflate) {
        imgReturn = (ImageView) inflate.findViewById(R.id.imgReturn);
        toolbar_title = (TextView) inflate.findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);


        toolbar_title.setText("帐户安全");
        imgReturn.setOnClickListener(v -> pop());
    }
}

