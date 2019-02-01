package com.yaoguang.driver.order.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.common.base.BaseFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/22 0022.
 * 版    权：
 */
public class MapSearchKeywordFragment extends BaseFragment {

    @BindView(R.id.imReturn)
    ImageView imReturn;
    @BindView(R.id.etKeyword)
    EditText etKeyword;
    @BindView(R.id.tvSearchButton)
    TextView tvSearchButton;
    @BindView(R.id.tvMyLocal)
    TextView tvMyLocal;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvClear)
    TextView tvClear;
    @BindView(R.id.flSearch)
    FrameLayout flSearch;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_flsearch, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
