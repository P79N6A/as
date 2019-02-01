package com.yaoguang.lib.appcommon.widget.date;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.yaoguang.lib.common.ObjectUtils;

import java.util.Calendar;

/**
 * 时间dialog 里面调用 SpinnerDatePickerDialog
 * Created by zhongjh on 2017/5/4.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public final static String TAGTIME = "tagtime";
    public final static String DATE = "date";
    public final static String MINUTE_ARITHMETIC = "minute_arithmetic";
    String mDate = "";

    private String mTime = "";
    private int minuteArithmetic;
    private ComeBack comeBack;

    private int minHour = -1;
    private int minMinute = -1;
    private int maxHour = 25;
    private int maxMinute = 61;

    public int getMinHour() {
        return minHour;
    }

    public void setMinHour(int minHour) {
        this.minHour = minHour;
    }

    public int getMinMinute() {
        return minMinute;
    }

    public void setMinMinute(int minMinute) {
        this.minMinute = minMinute;
    }

    public int getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(int maxHour) {
        this.maxHour = maxHour;
    }

    public int getMaxMinute() {
        return maxMinute;
    }

    public void setMaxMinute(int maxMinute) {
        this.maxMinute = maxMinute;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        //获取传递过来的日期
        if (args != null) {
            mDate = args.getString(DATE, "");
            minuteArithmetic = args.getInt(MINUTE_ARITHMETIC, 0);
        }

        //新建日历类用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minuteArithmetic);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //返回TimePickerDialog对象
        //因为实现了OnTimeSetListener接口，所以第二个参数直接传入this
//        RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(getActivity(), this, hour, minute, true);
//        SpinnerDatePickerDialog timePickerDialog = new SpinnerDatePickerDialog(getActivity(), calendar).onCreateDialog();
        return new SpinnerDatePickerDialog(getActivity(), this, calendar, minHour, minMinute, maxHour, maxMinute).onCreateDialog(savedInstanceState);
    }

    //实现OnTimeSetListener的onTimeSet方法
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TimePickerFragment.this.dismiss();

        String hourOfDayStr = ObjectUtils.parseString(hourOfDay);
        if (hourOfDayStr.length() == 1)
            hourOfDayStr = "0" + hourOfDayStr;

        String minuteStr = ObjectUtils.parseString(minute);
        if (minuteStr.length() == 1)
            minuteStr = "0" + minute;

        if (mDate.equals("")) {
            mTime = hourOfDayStr + ":" + minuteStr;
        } else {
            mTime = mDate + " " + hourOfDayStr + ":" + minuteStr;
        }
        //调用activity的getData方法将数据传回activity显示
        if (getComeBack() != null) {
            getComeBack().getData(mTime, this.getTag());
        }
    }

    public ComeBack getComeBack() {
        return comeBack;
    }

    public void setComeBack(ComeBack comeBack) {
        this.comeBack = comeBack;
    }

    /**
     * 赋值时间
     */
    public void setTime(String time) {
        Bundle bundle = new Bundle();
        bundle.putString("time", time);
        setArguments(bundle);
    }

    public interface ComeBack {
        void getData(String data, String tag);
    }

}
