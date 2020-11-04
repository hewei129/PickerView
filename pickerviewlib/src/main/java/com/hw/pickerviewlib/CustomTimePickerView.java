package com.hw.pickerviewlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import com.hw.pickerviewlib.widget.WheelTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 * Created by David on 20/01/02.
 *
 * 此为非弹框的日期选择自定义view
 */
public class CustomTimePickerView{

    private Context mContext;
    private WheelTime mWheelTime;
    private OnTimeSelectListener mTimeSelectListener;
    private Date maxDate, minDate;
    private String yearMonth;

    public CustomTimePickerView(Context context, WheelTime.Type type) {
        mContext = context;
        initSelectTime(type);
    }

    public CustomTimePickerView(Context context, WheelTime.Type type, String yearMonth) {
        mContext = context;
        this.yearMonth = yearMonth;
        initSelectTime(type);
    }

    public View getRootView(){
        if(rootView == null)
            rootView = LayoutInflater.from(mContext).inflate(R.layout.pickerview_time2, null);
        return rootView;
    }
    /**
     * 初始化默认选中当前时间
     */
    public View rootView;
    private void initSelectTime(WheelTime.Type type) {
        View timePickerView = getRootView().findViewById(R.id.timepicker);
        mWheelTime = new WheelTime(timePickerView, type);
        mWheelTime.setOnTimeSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelected() {
                if (mTimeSelectListener != null) {
                    try {
                        Date date = WheelTime.dateFormat.parse(mWheelTime.getTime());
                        if (null != getMaxDate()) {
                            if (date.after(getMaxDate()))
                                date = getMaxDate();
                        }
                        if (null != getMinDate()) {
                            if (date.before(getMinDate()))
                                date = getMinDate();
                        }
                        mTimeSelectListener.onTimeSelect(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Calendar rangeCalendar = Calendar.getInstance();
        setRange(2019, rangeCalendar.get(Calendar.YEAR));
        setMaxDate(new Date(System.currentTimeMillis()));
        if(yearMonth != null && !"".equals(yearMonth)) {
            String[] now_array = yearMonth.split("-");
            int year = Integer.valueOf(now_array[0]);
            int month = Integer.valueOf(now_array[1]);
            mWheelTime.setPicker(year, month-1, 1, 0, 0);
        }else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            mWheelTime.setPicker(year, month, day, hours, minute);
        }

        mWheelTime.setCyclic(false);

//        setMinData("2019-07-01");
    }



    public interface OnItemSelectListener {
        void onItemSelected();
    }

    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     */
    public void setRange(int startYear, int endYear) {
        mWheelTime.setStartYear(startYear);
        mWheelTime.setEndYear(endYear);
    }

    public void setMinData(Date minDate) {
        this.minDate = minDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String now_string = formatter.format(minDate);
        String[] now_array = now_string.split("-");
        mWheelTime.setNowYear(Integer.valueOf(now_array[0]));
        mWheelTime.setStartMonth(Integer.valueOf(now_array[1]));
        mWheelTime.setStartData(Integer.valueOf(now_array[2]));
        mWheelTime.setIsHasMaxMinData(1);
    }

    public void setMinData(String minDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.minDate = formatter.parse(minDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] now_array = minDate.split("-");
        mWheelTime.setNowYear(Integer.valueOf(now_array[0]));
        mWheelTime.setStartMonth(Integer.valueOf(now_array[1]));
        mWheelTime.setStartData(Integer.valueOf(now_array[2]));
        mWheelTime.setIsHasMaxMinData(1);
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String now_string = formatter.format(maxDate);
        String[] now_array = now_string.split("-");
        mWheelTime.setNowYear(Integer.valueOf(now_array[0]));
        mWheelTime.setEndMonth(Integer.valueOf(now_array[1]));
        mWheelTime.setEndData(Integer.valueOf(now_array[2]));
        mWheelTime.setIsHasMaxMinData(2);
    }

    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        mWheelTime.setCyclic(cyclic);
    }


    /**
     * 设置滚动文字大小
     */
    public void setTextSize(float size) {
        mWheelTime.setTextSize(size);
    }


    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.mTimeSelectListener = timeSelectListener;
    }


    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public class TextSize {
        public static final float BIG = 6f;
        public static final float DEFAULT = 5f;
        public static final float SMALL = 4f;
    }
}
