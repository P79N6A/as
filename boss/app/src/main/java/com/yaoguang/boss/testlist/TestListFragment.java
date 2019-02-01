package com.yaoguang.boss.testlist;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.boss.R;
import com.yaoguang.boss.databinding.FragmentListBinding;
import com.yaoguang.boss.testlist.adapter.TestAdapter;
import com.yaoguang.common.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class TestListFragment extends Fragment {
    private FragmentListBinding dataBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);


        TestAdapter adapter = new TestAdapter();
        RecyclerViewUtils.initPage(dataBinding.recyclerView, adapter, null, container.getContext(), false);

        List list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        adapter.appendToList(list);
        adapter.notifyDataSetChanged();
        return dataBinding.getRoot();
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        TestListFragment fragment = new TestListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
