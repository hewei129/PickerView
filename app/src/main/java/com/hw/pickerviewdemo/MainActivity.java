package com.hw.pickerviewdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hw.pickerviewlib.TimePickerView;
import com.hw.pickerviewlib.widget.WheelTime;

import java.util.Date;


/**
 * @author hewei(David)
 * @date 2020/11/4  5:14 PM
 * @Copyright ©  Shanghai Xinke Digital Technology Co., Ltd.
 * @description
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimePickerView timePickerView = new TimePickerView(this, WheelTime.Type.HOURS_MINS);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {

            }
        });
        timePickerView.show();
    }
}
