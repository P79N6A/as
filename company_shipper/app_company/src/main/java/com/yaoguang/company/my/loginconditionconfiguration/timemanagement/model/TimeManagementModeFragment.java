package com.yaoguang.company.my.loginconditionconfiguration.timemanagement.model;

import android.os.Bundle;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentTimeManagementModeBinding;
import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.appcommon.widget.date.TimePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/12/7.
 */

public class TimeManagementModeFragment extends BaseFragmentDataBind<FragmentTimeManagementModeBinding> {

    public static final String MODE = "mode";// 存储数据的常量
    UserLoginTime mUserLoginTime;

    TimePickerFragment mTimeBeginPickerFragment = new TimePickerFragment();
    TimePickerFragment mTimeEndPickerFragment = new TimePickerFragment();

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    MemberDataSource mMemberDataSource = new MemberDataSource();

    public static TimeManagementModeFragment newInstance(UserLoginTime userLoginTime) {
        TimeManagementModeFragment timeManagementModeFragment = new TimeManagementModeFragment();
        Bundle bundle = new Bundle();
        if (userLoginTime != null) {
            bundle.putParcelable(MODE, userLoginTime);
        }
        timeManagementModeFragment.setArguments(bundle);
        return timeManagementModeFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_time_management_mode;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void init() {
        // 判断是否有数据
        if (getArguments() != null)
            mUserLoginTime = getArguments().getParcelable(MODE);
        if (mDataBinding.toolbarCommon != null) {
            if (mUserLoginTime != null)
                initToolbarNav(mToolbarCommonBinding.toolbar, "修改时间方案", -1, null);
            else
                initToolbarNav(mToolbarCommonBinding.toolbar, "登录时间方案管理", -1, null);
        }

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mUserLoginTime != null) {
            mDataBinding.etNameValue.setText(mUserLoginTime.getName());

            mDataBinding.tvOneValue.setTag(R.id.timeBegin, mUserLoginTime.getMonTime().split(",")[0]);
            mDataBinding.tvOneValue.setTag(R.id.timeEnd, mUserLoginTime.getMonTime().split(",")[1]);
            mDataBinding.tvTwoValue.setTag(R.id.timeBegin, mUserLoginTime.getTueTime().split(",")[0]);
            mDataBinding.tvTwoValue.setTag(R.id.timeEnd, mUserLoginTime.getTueTime().split(",")[1]);
            mDataBinding.tvThreeValue.setTag(R.id.timeBegin, mUserLoginTime.getWesTime().split(",")[0]);
            mDataBinding.tvThreeValue.setTag(R.id.timeEnd, mUserLoginTime.getWesTime().split(",")[1]);
            mDataBinding.tvFourValue.setTag(R.id.timeBegin, mUserLoginTime.getThiTime().split(",")[0]);
            mDataBinding.tvFourValue.setTag(R.id.timeEnd, mUserLoginTime.getThiTime().split(",")[1]);
            mDataBinding.tvFiveValue.setTag(R.id.timeBegin, mUserLoginTime.getFriTime().split(",")[0]);
            mDataBinding.tvFiveValue.setTag(R.id.timeEnd, mUserLoginTime.getFriTime().split(",")[1]);
            mDataBinding.tvSixValue.setTag(R.id.timeBegin, mUserLoginTime.getSatTime().split(",")[0]);
            mDataBinding.tvSixValue.setTag(R.id.timeEnd, mUserLoginTime.getSatTime().split(",")[1]);
            mDataBinding.tvServenValue.setTag(R.id.timeBegin, mUserLoginTime.getSunTime().split(",")[0]);
            mDataBinding.tvServenValue.setTag(R.id.timeEnd, mUserLoginTime.getSunTime().split(",")[1]);

            mDataBinding.cbOne.setChecked(mUserLoginTime.getIsMon() == 1);
            mDataBinding.cbTwo.setChecked(mUserLoginTime.getIsTue() == 1);
            mDataBinding.cbThree.setChecked(mUserLoginTime.getIsWes() == 1);
            mDataBinding.cbFour.setChecked(mUserLoginTime.getIsThi() == 1);
            mDataBinding.cbFive.setChecked(mUserLoginTime.getIsFri() == 1);
            mDataBinding.cbSix.setChecked(mUserLoginTime.getIsSat() == 1);
            mDataBinding.cbServen.setChecked(mUserLoginTime.getIsSun() == 1);

            formatData();
        } else {
            mDataBinding.tvOneValue.setTag(R.id.timeBegin, mDataBinding.tvOneValue.getText().toString().split("~")[0]);
            mDataBinding.tvOneValue.setTag(R.id.timeEnd, mDataBinding.tvOneValue.getText().toString().split("~")[1]);
            mDataBinding.tvTwoValue.setTag(R.id.timeBegin, mDataBinding.tvTwoValue.getText().toString().split("~")[0]);
            mDataBinding.tvTwoValue.setTag(R.id.timeEnd, mDataBinding.tvTwoValue.getText().toString().split("~")[1]);
            mDataBinding.tvThreeValue.setTag(R.id.timeBegin, mDataBinding.tvThreeValue.getText().toString().split("~")[0]);
            mDataBinding.tvThreeValue.setTag(R.id.timeEnd, mDataBinding.tvThreeValue.getText().toString().split("~")[1]);
            mDataBinding.tvFourValue.setTag(R.id.timeBegin, mDataBinding.tvFourValue.getText().toString().split("~")[0]);
            mDataBinding.tvFourValue.setTag(R.id.timeEnd, mDataBinding.tvFourValue.getText().toString().split("~")[1]);
            mDataBinding.tvFiveValue.setTag(R.id.timeBegin, mDataBinding.tvFiveValue.getText().toString().split("~")[0]);
            mDataBinding.tvFiveValue.setTag(R.id.timeEnd, mDataBinding.tvFiveValue.getText().toString().split("~")[1]);
            mDataBinding.tvSixValue.setTag(R.id.timeBegin, mDataBinding.tvSixValue.getText().toString().split("~")[0]);
            mDataBinding.tvSixValue.setTag(R.id.timeEnd, mDataBinding.tvSixValue.getText().toString().split("~")[1]);
            mDataBinding.tvServenValue.setTag(R.id.timeBegin, mDataBinding.tvServenValue.getText().toString().split("~")[0]);
            mDataBinding.tvServenValue.setTag(R.id.timeEnd, mDataBinding.tvServenValue.getText().toString().split("~")[1]);
        }
    }

    /**
     * 格式化时间
     */
    private void formatData() {
        mDataBinding.tvOneValue.setText(mDataBinding.tvOneValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvOneValue.getTag(R.id.timeEnd));
        mDataBinding.tvTwoValue.setText(mDataBinding.tvTwoValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvTwoValue.getTag(R.id.timeEnd));
        mDataBinding.tvThreeValue.setText(mDataBinding.tvThreeValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvThreeValue.getTag(R.id.timeEnd));
        mDataBinding.tvFourValue.setText(mDataBinding.tvFourValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvFourValue.getTag(R.id.timeEnd));
        mDataBinding.tvFiveValue.setText(mDataBinding.tvFiveValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvFiveValue.getTag(R.id.timeEnd));
        mDataBinding.tvSixValue.setText(mDataBinding.tvSixValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvSixValue.getTag(R.id.timeEnd));
        mDataBinding.tvServenValue.setText(mDataBinding.tvServenValue.getTag(R.id.timeBegin) + "~" + mDataBinding.tvServenValue.getTag(R.id.timeEnd));
    }

    @Override
    public void initListener() {
        // 先显示第一个，再显示第二个
        mDataBinding.rlOne.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvOneValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvOneValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlOne");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvOneValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlOne")) {
                    mDataBinding.tvOneValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvOneValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvOneValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlOne");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvOneValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlOne")) {
                            mDataBinding.tvOneValue.setTag(R.id.timeEnd, data2+ ":59");
                            formatData();
                        }
                    });
                }
            });
        });
        mDataBinding.rlTwo.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvTwoValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvTwoValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlTwo");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvTwoValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlTwo")) {
                    mDataBinding.tvTwoValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvTwoValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvTwoValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlTwo");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvTwoValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlTwo")) {
                            mDataBinding.tvTwoValue.setTag(R.id.timeEnd, data2 + ":59");
                            formatData();
                        }
                    });
                }
            });
        });
        mDataBinding.rlThree.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvThreeValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvThreeValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlThree");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvThreeValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlThree")) {
                    mDataBinding.tvThreeValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvThreeValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvThreeValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlThree");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvThreeValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlThree")) {
                            mDataBinding.tvThreeValue.setTag(R.id.timeEnd, data2 + ":59");
                            formatData();
                        }
                    });
                }
            });
        });
        mDataBinding.rlFour.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvFourValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvFourValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlFour");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvFourValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlFour")) {
                    mDataBinding.tvFourValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvFourValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvFourValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlFour");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvFourValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlFour")) {
                            mDataBinding.tvFourValue.setTag(R.id.timeEnd, data2 + ":59");
                            formatData();
                        }
                    });
                }
            });
        });
        mDataBinding.rlFive.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvFiveValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvFiveValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlFive");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvFiveValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlFive")) {
                    mDataBinding.tvFiveValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvFiveValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvFiveValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlFive");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvFiveValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlFive")) {
                            mDataBinding.tvFiveValue.setTag(R.id.timeEnd, data2 + ":59");
                            formatData();
                        }
                    });
                }
            });
        });
        mDataBinding.rlSix.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvSixValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvSixValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlSix");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvSixValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlSix")) {
                    mDataBinding.tvSixValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvSixValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvSixValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlSix");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvSixValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlSix")) {
                            mDataBinding.tvSixValue.setTag(R.id.timeEnd, data2 + ":59");
                            formatData();
                        }
                    });
                }
            });
        });
        mDataBinding.rlServen.setOnClickListener(v -> {
            mTimeBeginPickerFragment.setMaxHour(ObjectUtils.parseInt(mDataBinding.tvServenValue.getText().toString().split("~")[1].split(":")[0]));
            mTimeBeginPickerFragment.setMaxMinute(ObjectUtils.parseInt(mDataBinding.tvServenValue.getText().toString().split("~")[1].split(":")[1]));
            mTimeBeginPickerFragment.show(getFragmentManager(), "rlServen");
            mTimeBeginPickerFragment.setTime(mDataBinding.tvServenValue.getText().toString().split("~")[0]);
            mTimeBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlServen")) {
                    mDataBinding.tvServenValue.setTag(R.id.timeBegin, data + ":00");
                    mTimeEndPickerFragment.setMinHour(ObjectUtils.parseInt(mDataBinding.tvServenValue.getText().toString().split("~")[1].split(":")[0]));
                    mTimeEndPickerFragment.setMinMinute(ObjectUtils.parseInt(mDataBinding.tvServenValue.getText().toString().split("~")[1].split(":")[1]));
                    mTimeEndPickerFragment.show(getFragmentManager(), "rlServen");
                    mTimeEndPickerFragment.setTime(mDataBinding.tvServenValue.getText().toString().split("~")[1]);
                    mTimeEndPickerFragment.setComeBack((data2, tag2) -> {
                        if (tag2.equals("rlServen")) {
                            mDataBinding.tvServenValue.setTag(R.id.timeEnd, data2 + ":59");
                            formatData();
                        }
                    });
                }
            });
        });

        // 保存
        mDataBinding.btnComit.setOnClickListener(v -> {
            if (mUserLoginTime == null)
                mUserLoginTime = new UserLoginTime();
            mUserLoginTime.setName(mDataBinding.etNameValue.getText().toString());
            mUserLoginTime.setMonTime(mDataBinding.tvOneValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvOneValue.getTag(R.id.timeEnd));
            mUserLoginTime.setTueTime(mDataBinding.tvTwoValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvTwoValue.getTag(R.id.timeEnd));
            mUserLoginTime.setWesTime(mDataBinding.tvThreeValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvThreeValue.getTag(R.id.timeEnd));
            mUserLoginTime.setThiTime(mDataBinding.tvFourValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvFourValue.getTag(R.id.timeEnd));
            mUserLoginTime.setFriTime(mDataBinding.tvFiveValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvFiveValue.getTag(R.id.timeEnd));
            mUserLoginTime.setSatTime(mDataBinding.tvSixValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvSixValue.getTag(R.id.timeEnd));
            mUserLoginTime.setSunTime(mDataBinding.tvServenValue.getTag(R.id.timeBegin) + "," + mDataBinding.tvServenValue.getTag(R.id.timeEnd));
            mUserLoginTime.setIsMon(mDataBinding.cbOne.isChecked() ? 1 : 0);
            mUserLoginTime.setIsTue(mDataBinding.cbTwo.isChecked() ? 1 : 0);
            mUserLoginTime.setIsWes(mDataBinding.cbThree.isChecked() ? 1 : 0);
            mUserLoginTime.setIsThi(mDataBinding.cbFour.isChecked() ? 1 : 0);
            mUserLoginTime.setIsFri(mDataBinding.cbFive.isChecked() ? 1 : 0);
            mUserLoginTime.setIsSat(mDataBinding.cbSix.isChecked() ? 1 : 0);
            mUserLoginTime.setIsSun(mDataBinding.cbServen.isChecked() ? 1 : 0);

            mMemberDataSource.userLoginTimeEdit(mUserLoginTime)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, TimeManagementModeFragment.this) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                            setFragmentResult(RESULT_OK, new Bundle());
                            pop();
                        }
                    });
        });

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
