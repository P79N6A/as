package com.yaoguang.lib.appcommon.widget.date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TimePicker;

import com.yaoguang.lib.R;
import com.yaoguang.lib.common.ObjectUtils;

import java.util.Calendar;

/**
 * Created by zhongjh on 2018/8/22.
 */
@SuppressLint("ValidFragment")
public class SpinnerDatePickerDialog extends DialogFragment implements
        TimePicker.OnTimeChangedListener {

    private TimePicker mTimePicker;
    private int mHourOfDay;
    private int mMinute;
    private Activity mActivity;
    TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    private int minHour = -1;
    private int minMinute = -1;
    private int maxHour = 25;
    private int maxMinute = 61;

    public SpinnerDatePickerDialog(Activity activity, TimePickerDialog.OnTimeSetListener onTimeSetListener, Calendar c,
                                   int minHour, int minMinute, int maxHour, int maxMinute) {
        super();
        mHourOfDay = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mActivity = activity;
        mOnTimeSetListener = onTimeSetListener;
        this.minHour = minHour;
        this.minMinute = minMinute;
        this.maxHour = maxHour;
        this.maxMinute = maxMinute;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_time_picker, null);
        mTimePicker = view.findViewById(R.id.timepicker);

        // 获取传递过来的时间
        Bundle args = getArguments();
        if (args != null) {
            String time = args.getString("time");
            if (!TextUtils.isEmpty(time)) {
                mHourOfDay = ObjectUtils.parseInt(time.split(":")[0]);
                mMinute = ObjectUtils.parseInt(time.split(":")[1]);
            }
        }

        mTimePicker.setCurrentHour(mHourOfDay);
        mTimePicker.setCurrentMinute(mMinute);
        mTimePicker.setOnTimeChangedListener(this);
        builder.setView(view);
        builder.setPositiveButton("确认", (dialog, id) -> mOnTimeSetListener.onTimeSet(mTimePicker, mHourOfDay, mMinute))
                .setNegativeButton("取消", (dialog, id) -> {
                    // Send the negative button event back to the host activity
//                        listener.onSpinnerDateDialogNegativeClick(SpinnerDatePickerDialog.this);
                });
        return builder.create();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.mTimePicker = view;

        boolean validTime = true;
        if (hourOfDay < minHour || (hourOfDay == minHour
                && minute < minMinute)) {
            validTime = false;
        }

        if (hourOfDay > maxHour || (hourOfDay == maxHour
                && minute > maxMinute)) {
            validTime = false;
        }

        if (validTime) {
            this.mHourOfDay = hourOfDay;
            this.mMinute = minute;
        }

        mTimePicker.setCurrentHour(mHourOfDay);
        mTimePicker.setCurrentMinute(mMinute);



    }
}
