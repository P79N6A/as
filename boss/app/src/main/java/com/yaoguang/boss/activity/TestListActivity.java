package com.yaoguang.boss.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.boss.R;
import com.yaoguang.boss.databinding.ActivityTestListBinding;
import com.yaoguang.boss.testlist.TestListFragment;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class TestListActivity extends SupportActivity {

    private ActivityTestListBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_test_list);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.container, TestListFragment.newInstance()).commit();
        }
    }
}
