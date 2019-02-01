package com.yaoguang.appcommon.publicsearch.addaddress;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.event.DataSynEvent;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.lib.entity.ProvinceBeans;

import org.greenrobot.eventbus.EventBus;


/**
 * 装卸货地址
 * Created by zhongjh on 2017/6/12.
 */
public class AddAddressFragment extends BaseFragment implements AddAddressContact.CAddAddressView {

    InitialView mInitialView;
    AddAddressContact.CAddAddressPresenter mPresenter;
    InfoClientPlace mInfoClientPlace;
    ProvinceBeans mProvinceBeans;

    //区分装卸货  PublicSearchInteractorImpl.TYPE_LOADPLACES
    int mType;
    //区分是否不跟数据库打交道，true-离线 false-在线
    boolean mOffline;

    AppPublicInfoWrapper mAppPublicInfoWrapper;

    String mCodeId;
    String mArea;

    /**
     * 打开新的,这是用于创建
     *
     * @param codeId 托运人id
     */
    public static AddAddressFragment newInstance(String codeId, String area, int type, boolean offline) {
        Bundle args = new Bundle();
        args.putString("codeId", codeId);
        args.putString("area", area);
        args.putInt("type", type);
        args.putBoolean("offline", offline);
        AddAddressFragment fragment = new AddAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 打开新的,这是用于编辑
     *
     * @param appPublicInfoWrapper 主键id
     */
    public static AddAddressFragment newInstance(AppPublicInfoWrapper appPublicInfoWrapper, String codeId, String area, int type, boolean offline) {
        Bundle args = new Bundle();
        args.putParcelable("appPublicInfoWrapper", appPublicInfoWrapper);
        args.putString("codeId", codeId);
        args.putString("area", area);
        args.putInt("type", type);
        args.putBoolean("offline", offline);
        AddAddressFragment fragment = new AddAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCodeId = args.getString("codeId");
            mArea = args.getString("area");
            mAppPublicInfoWrapper = args.getParcelable("appPublicInfoWrapper");
            mType = args.getInt("type");
            mOffline = args.getBoolean("offline");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);
        mInitialView = new InitialView(view);
        mPresenter = new AddAddressPresenterImpl(this, getContext());
        mInfoClientPlace = new InfoClientPlace();
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void addSuccess(String result) {
        EventBus.getDefault().post(new DataSynEvent());
        pop();
        Toast.makeText(BaseApplication.getInstance(), result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addFail(String result) {
        Toast.makeText(BaseApplication.getInstance(), result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProvinceBeans(ProvinceBeans value) {
        mProvinceBeans = value;
        mInitialView.pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mProvinceBeans.getProvinces().get(options1).getPickerViewText() +
                        mProvinceBeans.getCitys().get(options1).get(options2) +
                        mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
                mInitialView.tvValueRegionId.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        mInitialView.pvOptions.setPicker(mProvinceBeans.getProvinces(), mProvinceBeans.getCitys(), mProvinceBeans.getAreas());//三级选择器
    }

    public class InitialView {

        OptionsPickerView pvOptions;

        Toolbar toolbar;
        TextView tvValueRegionId;
        RelativeLayout rlRegionId;
        EditText etValueAddr;
        RelativeLayout rlAddr;
        EditText etValueLinkmanMp;
        RelativeLayout rlLinkmanMp;
        EditText etValueLinkman;
        EditText etValueLinkmanTelOne;
        EditText etValueLinkmanTelTwo;
        TextView tvTips;
        Button btnComit;
        TextView tvRemark;
        EditText etValueRemark;

        InitialView(View view) {
            initView(view);
            initListener();
        }

        @SuppressLint("SetTextI18n")
        void initView(View view) {
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            tvValueRegionId = (TextView) view.findViewById(R.id.tvValueRegionId);
            rlRegionId = (RelativeLayout) view.findViewById(R.id.rlRegionId);
            etValueAddr = (EditText) view.findViewById(R.id.etValueAddr);
            rlAddr = (RelativeLayout) view.findViewById(R.id.rlAddr);
            etValueLinkmanMp = (EditText) view.findViewById(R.id.etValueLinkmanMp);
            rlLinkmanMp = (RelativeLayout) view.findViewById(R.id.rlLinkmanMp);
            etValueLinkman = (EditText) view.findViewById(R.id.etValueLinkman);
            etValueLinkmanTelOne = (EditText) view.findViewById(R.id.etValueLinkmanTelOne);
            etValueLinkmanTelTwo = (EditText) view.findViewById(R.id.etValueLinkmanTelTwo);
            tvTips = (TextView) view.findViewById(R.id.tvTips);
            btnComit = (Button) view.findViewById(R.id.btnComit);
            tvRemark = view.findViewById(R.id.tvRemark);
            etValueRemark = view.findViewById(R.id.etValueRemark);

            String typeStr;
            if (mType == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
                typeStr = "装货";
            } else {
                typeStr = "卸货";
            }
            tvRemark.setText(typeStr + "说明");
            etValueRemark.setHint("请输入" + typeStr + "说明");

            if (mAppPublicInfoWrapper == null) {
                initToolbarNav(toolbar,"新增" + typeStr + "信息",-1,null);
            } else {
                initToolbarNav(toolbar,"编辑" + typeStr + "信息",-1,null);
                tvValueRegionId.setText(mAppPublicInfoWrapper.getFullName());
                etValueAddr.setText(mAppPublicInfoWrapper.getShortName());
                etValueLinkman.setText(mAppPublicInfoWrapper.getRemark1());
                etValueLinkmanMp.setText(mAppPublicInfoWrapper.getRemark2());
                if (!TextUtils.isEmpty(mAppPublicInfoWrapper.getRemark3())) {
                    String[] linkmanTelOne = mAppPublicInfoWrapper.getRemark3().split("-");
                    if (linkmanTelOne.length > 1) {
                        etValueLinkmanTelOne.setText(linkmanTelOne[0]);
                        etValueLinkmanTelTwo.setText(linkmanTelOne[1]);
                    }
                }
                etValueRemark.setText(mAppPublicInfoWrapper.getRemark4());
            }

        }

        void initListener() {
            rlRegionId.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mInitialView.pvOptions != null) {
                        mInitialView.pvOptions.show();
                        hideKeyboard();
                    }
                }
            });
            btnComit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (ObjectUtils.parseString(tvValueRegionId.getText().toString().trim()).equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), "请选择省、市、区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ObjectUtils.parseString(etValueAddr.getText().toString().trim()).equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), etValueAddr.getHint().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ObjectUtils.parseString(etValueLinkman.getText().toString().trim()).equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), etValueLinkman.getHint().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //手机和电话至少要输入其中一个
                    if (ObjectUtils.parseString(etValueLinkmanMp.getText().toString().trim()).equals("") && (ObjectUtils.parseString(etValueLinkmanTelOne.getText().toString().trim()).equals("") || ObjectUtils.parseString(etValueLinkmanTelTwo.getText().toString().trim()).equals(""))) {
                        Toast.makeText(BaseApplication.getInstance(),tvTips.getText(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!ObjectUtils.parseString(etValueLinkmanMp.getText().toString().trim()).equals("")) {
                        if (!RegexValidator.isMobile(etValueLinkmanMp.getText().toString().trim())) {
                            Toast.makeText(BaseApplication.getInstance(),"手机格式错误", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (!ObjectUtils.parseString(etValueLinkmanTelOne.getText().toString().trim()).equals("") || !ObjectUtils.parseString(etValueLinkmanTelTwo.getText().toString().trim()).equals("")) {
                        if (!RegexValidator.isPhone(etValueLinkmanTelOne.getText().toString() + "-" + etValueLinkmanTelTwo.getText().toString())) {
                            Toast.makeText(BaseApplication.getInstance(),"电话格式错误", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    InfoClientPlace infoClientPlace = new InfoClientPlace();
                    infoClientPlace.setRegionid(tvValueRegionId.getText().toString());
                    infoClientPlace.setAddr(etValueAddr.getText().toString());
                    infoClientPlace.setLinkmanMp(etValueLinkmanMp.getText().toString());
                    infoClientPlace.setLinkman(etValueLinkman.getText().toString());
                    if (!ObjectUtils.parseString(etValueLinkmanTelOne.getText().toString().trim()).equals("") && !ObjectUtils.parseString(etValueLinkmanTelTwo.getText().toString().trim()).equals(""))
                        infoClientPlace.setLinkmanTel(etValueLinkmanTelOne.getText().toString() + "-" + etValueLinkmanTelTwo.getText().toString());
                    infoClientPlace.setCodeId(mCodeId);
                    infoClientPlace.setArea(mArea);
                    infoClientPlace.setRemark(etValueRemark.getText().toString());

                    //判断类型，如果类型是true，那么是不需要经过数据库的，脱离了地址表的
                    if (mOffline) {
                        //目前只有编辑
                        AppInfoClientPlace appInfoClientPlace = new AppInfoClientPlace();
                        appInfoClientPlace.setRegionid(infoClientPlace.getRegionid());
                        appInfoClientPlace.setAddress(infoClientPlace.getAddr());
                        appInfoClientPlace.setLinkman(infoClientPlace.getLinkman());
                        appInfoClientPlace.setLinkmanMp(infoClientPlace.getLinkmanMp());
                        appInfoClientPlace.setLinkmanTel(infoClientPlace.getLinkmanTel());
                        appInfoClientPlace.setRemark(infoClientPlace.getRemark());
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("appInfoClientPlace", appInfoClientPlace);
                        setFragmentResult(RESULT_OK, bundle);
                        pop();
                    } else {
                        //判断是编辑还是添加
                        if (mAppPublicInfoWrapper == null) {
                            mPresenter.addLoadPlace(infoClientPlace);
                        } else {
                            infoClientPlace.setId(mAppPublicInfoWrapper.getId());
                            mPresenter.updateLoadPlace(infoClientPlace);
                        }
                    }
                }
            });
        }

    }

}
