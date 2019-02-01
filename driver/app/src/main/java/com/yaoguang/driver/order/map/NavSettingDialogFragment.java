package com.yaoguang.driver.order.map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.map.navi.NavSetting;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.yaoguang.common.common.UiUtils.getViewTag;

/**
 * 文件名：
 * 描    述： 导航 躲避拥堵,避免收费,不走高速,高速优先 设置
 * 作    者：韦理英
 * 时    间：2017/8/22 0022.
 * 版    权：
 */
public class NavSettingDialogFragment extends DialogFragment {
    Comeback mComeback;
    NavSetting mNavSetting;
    @BindView(R.id.congestion)
    TextView congestion;  // 躲避拥堵
    @BindView(R.id.cost)
    TextView cost;  // 避免收费
    @BindView(R.id.avoidHighSpeed)
    TextView avoidHighSpeed;  // 不走高速
    @BindView(R.id.highSpeed)
    TextView highSpeed;  // 高速优先
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.cbHuoChe)
    CheckBox cbHuoChe;
    Unbinder unbinder;
    @BindView(R.id.etChePai)
    EditText etChePai;
    @BindView(R.id.cbXianXing)
    CheckBox cbXianXing;
    @BindView(R.id.etHuoCheHeight)
    EditText etHuoCheHeight;
    @BindView(R.id.cbZhaiZhong)
    CheckBox cbZhaiZhong;
    @BindView(R.id.etWeight)
    EditText etWeight;
    @BindView(R.id.llHuoChe)
    LinearLayout llHuoChe;

    public static NavSettingDialogFragment newInstance() {
        return new NavSettingDialogFragment();
    }

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            // 底部显示
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        super.onStart();
    }

    /**
     * 描    述：初始化状态
     * 作    者：韦理英
     * 时    间：
     */

    protected void initStatus() {
        // 设置 躲避拥堵
        if (mNavSetting.isCongestion()) {
            selectBackground(congestion);
        }
        // 设置  避免收费
        if (mNavSetting.isCost()) {
            selectBackground(cost);
        }
        // 不走高速
        if (mNavSetting.isAvoidHighSpeed()) {
            selectBackground(avoidHighSpeed);
        }
        // 高速优先
        if (mNavSetting.isHighSpeed()) {
            selectBackground(highSpeed);
        }
        // 车牌
        etChePai.setText(mNavSetting.getChePai());
        // 限行
        cbXianXing.setChecked(mNavSetting.isXianXing());
        // 货车导航
        cbHuoChe.setChecked(mNavSetting.isHuoChe());
        // 货车高度
        etHuoCheHeight.setText(mNavSetting.getHuoCheHeight());
        // 货车最大载重计算
        cbZhaiZhong.setChecked(mNavSetting.isMaxChaiZhong());
        // 货车重量
        etWeight.setText(mNavSetting.getWeight());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置背景为透明
        View view = inflater.inflate(R.layout.fragment_navsetting, container, false);
        // 加载动画
        YoYo.with(Techniques.FadeInUp).duration(500).playOn(view);
        unbinder = ButterKnife.bind(this, view);
        // 初始化 状态
        initStatus();
        // 躲避拥堵
        congestion.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            selectBackground(congestion);
            // 设置 躲避拥堵
            mNavSetting.setCongestion(getViewTag(congestion));
            // 打印 结束
            ULog.i(mNavSetting.toString());
        });

        // 避免收费
        cost.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            selectBackground(cost);
            // 设置 避免收费
            mNavSetting.setCost(getViewTag(cost));
            // 去掉 高速优先
            unselectSetting(highSpeed);
            mNavSetting.setHighSpeed(getViewTag(highSpeed));

            // 打印 结束
            ULog.i(mNavSetting.toString());
        });

        // 不走高速
        avoidHighSpeed.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            selectBackground(avoidHighSpeed);
            // 设置 不走高速
            mNavSetting.setAvoidHighSpeed(getViewTag(avoidHighSpeed));
            // 去掉 高速优先
            unselectSetting(highSpeed);
            mNavSetting.setHighSpeed(getViewTag(highSpeed));

            // 打印 结束
            ULog.i(mNavSetting.toString());
        });

        // 高速优先
        highSpeed.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            selectBackground(highSpeed);
            // 设置 高速优先
            mNavSetting.setHighSpeed(getViewTag(highSpeed));
            // 去掉 避免收费
            unselectSetting(cost);
            mNavSetting.setCost(false);
            // 去掉 不走高速
            unselectSetting(avoidHighSpeed);
            mNavSetting.setAvoidHighSpeed(false);

            // 打印 结束
            ULog.i(mNavSetting.toString());
        });

        // 完成
        tvSubmit.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (getmComeback() != null) {
                mNavSetting.setXianXing(cbXianXing.isChecked());        // 车牌号限行
                mNavSetting.setChePai(etChePai.getText().toString());   // 车辆号
                mNavSetting.setHuoChe(cbHuoChe.isChecked());             // 货车导航
                mNavSetting.setHuoCheHeight(etHuoCheHeight.getText().toString());   //  货车高度
                mNavSetting.setMaxChaiZhong(cbZhaiZhong.isChecked());   //  载重规划线路
                mNavSetting.setWeight(etWeight.getText().toString());   //  货车最大载重

                getmComeback().result(mNavSetting);
            }
            dismiss();
        });

        return view;
    }

    /**
     * 描    述：设置未选择
     * 作    者：韦理英
     * 时    间：
     *
     * @param v view
     */
    private void unselectSetting(TextView v) {
        v.setTag(false);
        v.setBackgroundColor(UiUtils.getColor(R.color.window_background));
        v.setTextColor(UiUtils.getColor(R.color.dark_gray));
    }


    /**
     * 描    述：设置选择的背景色，并设置boolean
     * 作    者：韦理英
     * 时    间：
     *
     * @param v view
     *          [参数说明]
     *          return [return说明]
     */
    private void selectBackground(View v) {
        TextView view = (TextView) v;
        if (getViewTag(v)) {
            v.setTag(false);
            view.setBackgroundColor(UiUtils.getColor(R.color.window_background));
            view.setTextColor(UiUtils.getColor(R.color.dark_gray));
        } else {
            v.setTag(true);
            view.setBackgroundColor(UiUtils.getColor(R.color.orange500));
            view.setTextColor(UiUtils.getColor(R.color.white_text));
        }
        ULog.i("select:" + view.getTag());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public Comeback getmComeback() {
        return mComeback;
    }

    public void setmComeback(Comeback mComeback) {
        this.mComeback = mComeback;
    }

    public void setmNavSetting(NavSetting mNavSetting) {
        this.mNavSetting = mNavSetting;
    }

    /**
     * 描    述：回调结果
     * 作    者：韦理英
     * 时    间：
     */
    interface Comeback {
        void result(NavSetting navSetting);
    }
}
