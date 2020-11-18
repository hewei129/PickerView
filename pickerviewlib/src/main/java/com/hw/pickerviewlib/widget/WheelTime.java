package com.hw.pickerviewlib.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;


import com.hw.pickerviewlib.CustomTimePickerView;
import com.hw.pickerviewlib.R;
import com.hw.pickerviewlib.TimePickerView;
import com.hw.pickerviewlib.adapter.NumericWheelAdapter;
import com.hw.pickerviewlib.listener.OnItemSelectedListener;
import com.hw.pickerviewlib.widget.wheelview.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  @author hewei(David)
 *  @date 2020/9/22  10:12 AM
 *  精仿iOSPickerViewController控件
 */
public class WheelTime {
    public static final int DEFULT_START_YEAR = 1990;
    public static final int DEFULT_END_YEAR = 2100;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    int year_num;
    int month_num;
    int selectDate;
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private Type type;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;
    private int startMonth = 1;
    private int endMonth = 12;
    private int startData = 1;
    private int endData = 30;
    private int nowYear;
    private int isHasMaxMinData = 0;
    private boolean isNoLabel = false;

    public boolean isNoLabel() {
        return isNoLabel;
    }

    public void setNoLabel(boolean noLabel) {
        isNoLabel = noLabel;
    }

    private float mTextSize = TimePickerView.TextSize.DEFAULT;

    public WheelTime(View view) {
        super();
        this.view = view;
        type = Type.ALL;
        setView(view);
    }

    public WheelTime(View view, Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public int getIsHasMaxMinData() {
        return isHasMaxMinData;
    }

    public void setIsHasMaxMinData(int isHasMaxMinData) {
        this.isHasMaxMinData = isHasMaxMinData;
    }

    public int getNowYear() {
        return nowYear;
    }

    public void setNowYear(int nowYear) {
        this.year_num = nowYear;
        this.nowYear = nowYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.month_num = startMonth;
        this.startMonth = startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.month_num = endMonth;
        this.endMonth = endMonth;
    }


    public int getStartData() {
        return startData;
    }

    public void setStartData(int startData) {
        this.startData = startData;
    }

    public int getEndData() {
        return endData;
    }

    public void setEndData(int endData) {
        this.endData = endData;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    public void setPicker(int year, final int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        if(!isNoLabel)
            wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        year_num = year; //初始化时根据传的数据显示

        // 月
        wv_month = view.findViewById(R.id.month);
        if(year == nowYear && endMonth != 0){
            wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
            wv_month.setCurrentItem(endMonth -1);
        }else{
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCurrentItem(month);
        }

        if(!isNoLabel)
            wv_month.setLabel(context.getString(R.string.pickerview_month));
        // 日
        wv_day = view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        if(!isNoLabel)
            wv_day.setLabel(context.getString(R.string.pickerview_day));
        wv_day.setCurrentItem(day - 1);


        wv_hours = view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        if(!isNoLabel)
            wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);

        wv_mins = view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        if(!isNoLabel)
            wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                year_num = index + startYear;
                if(year_num == nowYear && endMonth != 0){
                    wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
                    month_num = endMonth;
                }else{
                    wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                }
                wv_month.setCurrentItem(month_num -1);
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (isHasMaxMinData == 2) {
                    if (year_num > nowYear) {
                        wv_year.setCurrentItem(nowYear - startYear);
                        wv_month.setCurrentItem(endMonth - 1);
                        wv_day.setCurrentItem(endData - 1);
                        year_num = nowYear;
                        return;
                    }
                } else if (isHasMaxMinData == 1) {
                    if (year_num < nowYear) {
                        wv_year.setCurrentItem(nowYear - startYear);
                        wv_month.setCurrentItem(startMonth - 1);
                        wv_day.setCurrentItem(startData - 1);
                        year_num = nowYear;
                        return;
                    }
                }

                int maxItem = 30;
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
                if(mItemSelectListener != null)
                    mItemSelectListener.onItemSelected();
            }


        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                month_num = index + 1;
                if (isHasMaxMinData == 2) {
                    if (year_num == nowYear && month_num >= endMonth) {
                        wv_month.setCurrentItem(endMonth - 1);
                        wv_day.setCurrentItem(endData - 1);
                        month_num = endMonth;
//                        return;      //选择当前月份时，没有监听事件；注释掉return之后，有监听事件。
                    }
                } else if (isHasMaxMinData == 1) {
                    if (year_num == nowYear && month_num <= startMonth) {
                        wv_month.setCurrentItem(startMonth - 1);
                        wv_day.setCurrentItem(startData - 1);
                        month_num = startMonth;
                        return;
                    }
                }
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year
                            .getCurrentItem() + startYear) % 100 != 0)
                            || (wv_year.getCurrentItem() + startYear) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
                if(mItemSelectListener != null)
                    mItemSelectListener.onItemSelected();

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);
        wv_day.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectDate = index + 1;
                if (isHasMaxMinData == 2) {
                    if (year_num == nowYear && month_num == endMonth && selectDate > endData) {
                        wv_day.setCurrentItem(endData - 1);
                    }
                } else if (isHasMaxMinData == 1) {
                    if (year_num == nowYear && month_num == startMonth && selectDate < startData) {
                        wv_day.setCurrentItem(startData - 1);
                    }
                }

                if(mItemSelectListener != null)
                    mItemSelectListener.onItemSelected();
            }
        });



        setTextSize();
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        switch (type){
            case MONTH_DAY_HOUR_MIN:
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                sb.append(year).append("-")
                        .append((wv_month.getCurrentItem() + 1)).append("-")
                        .append((wv_day.getCurrentItem() + 1))
                        .append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());

                break;
            case HOURS_MINS:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String now_string = formatter.format(new Date());
                sb.append(now_string)
                        .append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());
                break;
            default:
                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                        .append((wv_month.getCurrentItem() + 1)).append("-")
                        .append((wv_day.getCurrentItem() + 1)).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());
                break;

        }

        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setTextSize(float size) {
        this.mTextSize = size;
        setTextSize();
    }

    private void setTextSize() {
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        Log.e("test", "mTextSize: " + mTextSize);

        float textSize = mTextSize;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 3;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 3;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
    }



    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分


    public void setOnTimeSelectListener(CustomTimePickerView.OnItemSelectListener timeSelectListener) {
        this.mItemSelectListener = timeSelectListener;
    }
    private CustomTimePickerView.OnItemSelectListener  mItemSelectListener;

}
