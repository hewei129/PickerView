package com.hw.pickerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class MainActivity extends Activity implements View.OnClickListener,  TimePickerView.OnTimeSelectListener{
    private TimePickerView timePickerView1, timePickerView2, timePickerView3, timePickerView4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        timePickerView1 = new TimePickerView(this, WheelTime.Type.ALL);
        timePickerView2 = new TimePickerView(this, WheelTime.Type.MONTH_DAY_HOUR_MIN);
        timePickerView3 = new TimePickerView(this, WheelTime.Type.YEAR_MONTH_DAY);
        timePickerView4 = new TimePickerView(this, WheelTime.Type.HOURS_MINS);
        timePickerView1.setOnTimeSelectListener(this);
        timePickerView2.setOnTimeSelectListener(this);
        timePickerView3.setOnTimeSelectListener(this);
        timePickerView4.setOnTimeSelectListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                timePickerView1.show();
                break;
            case R.id.btn2:
                timePickerView2.show();
                break;
            case R.id.btn3:
                timePickerView3.show();
                break;
            case R.id.btn4:
                timePickerView4.show();
                break;
        }
    }

    @Override
    public void onTimeSelect(Date date) {
        Toast.makeText(MainActivity.this, date.toLocaleString(), Toast.LENGTH_SHORT).show();
    }
}
