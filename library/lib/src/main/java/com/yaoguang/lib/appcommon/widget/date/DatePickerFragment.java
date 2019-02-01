package com.yaoguang.lib.appcommon.widget.date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.yaoguang.lib.common.ObjectUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期dialog
 * Created by zhongjh on 2017/5/4.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public final static String MINDATE = "minDate";
    public final static String MAXDATE = "maxDate";
    public final static String ISSHOWTIME = "isShowTime";
    public final static String MINUTE_ARITHMETIC = "minute_arithmetic";

    private String mDate;
    private int minuteArithmetic;
    private ComeBack comeBack;

    boolean isShowTime = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //得到Calendar类实例，用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        Bundle args = getArguments();
        //获取最小的日期
        String minDate = null;
        if (args != null) {
            minDate = args.getString(MINDATE, "");
        }
        //获取最大的日期
        String maxDate = null;
        if (args != null) {
            maxDate = args.getString(MAXDATE, "");
        }
        //获取是否显示时间
        if (args != null)
            isShowTime = args.getBoolean(ISSHOWTIME, false);
        if (args != null)
            minuteArithmetic = args.getInt(MINUTE_ARITHMETIC, 0);

        //返回DatePickerDialog对象
        //因为实现了OnDateSetListener接口，所以第二个参数直接传入this
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (minDate != null && !minDate.equals("")) {
            //转换时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(minDate, pos);

            DatePicker picker = datePickerDialog.getDatePicker();
            picker.setMinDate(strtodate.getTime());
        }
        if (maxDate != null && !maxDate.equals("")) {
            //转换时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(maxDate, pos);

            DatePicker picker = datePickerDialog.getDatePicker();
            picker.setMaxDate(strtodate.getTime());
        }
        return datePickerDialog;
    }

    //实现OnDateSetListener接口的onDateSet()方法
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //将用户选择的日期传到TimePickerFragment
        String monthOfYearStr = ObjectUtils.parseString(monthOfYear + 1);
        if (monthOfYearStr.length() == 1)
            monthOfYearStr = "0" + monthOfYearStr;
        String dayOfMonthStr = ObjectUtils.parseString(dayOfMonth);
        if (dayOfMonthStr.length() == 1)
            dayOfMonthStr = "0" + dayOfMonthStr;
        mDate = year + "-" + monthOfYearStr + "-" + dayOfMonthStr;
        DatePickerFragment.this.dismiss();

        if (isShowTime) {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.show(getFragmentManager(), this.getTag() + TimePickerFragment.TAGTIME);
            Bundle args = new Bundle();
            args.putString(TimePickerFragment.DATE, mDate);
            args.putInt(MINUTE_ARITHMETIC, minuteArithmetic);
            timePickerFragment.setArguments(args);
            timePickerFragment.setComeBack((data, tag) -> {
                if (getComeBack() != null) {
                    getComeBack().getData(data, tag);
                }
            });
        } else {
            //将activity强转为DataCallBack
            //调用activity的getData方法将数据传回activity显示
            if (getComeBack() != null) {
                getComeBack().getData(mDate, this.getTag());
            }
        }

    }

    public ComeBack getComeBack() {
        return comeBack;
    }

    public void setComeBack(ComeBack comeBack) {
        this.comeBack = comeBack;
    }

    public interface ComeBack {
        void getData(String data, String tag);
    }
}
