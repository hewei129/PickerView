package com.hw.pickerviewlib;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hw.pickerviewlib.widget.BasePickerView;
import com.hw.pickerviewlib.widget.WheelTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *  @author hewei(David)
 *  @date 2020/9/22  10:12 AM
 *  时间选择器
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {

    private Context mContext;
    private View mHeadView;
    private TextView mTxtTitle;
    private WheelTime mWheelTime;
    private Button mBtnSubmit, mBtnCancel;
    private OnTimeSelectListener mTimeSelectListener;
    private Date maxDate, minDate;

    public TimePickerView(Context context) {
        super(context);
        mContext = context;
        initView();
        initSelectTime(WheelTime.Type.ALL);
    }


    public TimePickerView(Context context, WheelTime.Type type) {
        super(context);
        mContext = context;
        initView();
        initSelectTime(type);
    }

    /**
     * 初始化 View
     */
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.pickerview_time, contentContainer);
        mHeadView = findViewById(R.id.rlt_head_view);
        mTxtTitle = (TextView) findViewById(R.id.tvTitle);
        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnSubmit.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    /**
     * 初始化默认选中当前时间
     */
    private void initSelectTime(WheelTime.Type type) {
        View timePickerView = findViewById(R.id.timepicker);
        mWheelTime = new WheelTime(timePickerView, type);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Calendar rangeCalendar = Calendar.getInstance();
        setRange(rangeCalendar.get(Calendar.YEAR) - 10, rangeCalendar.get(Calendar.YEAR) + 10);
        mWheelTime.setPicker(year, month, day, hours, minute);
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
     * 设置选中时间
     *
     * @param date 时间
     */
    public void initTime(Date date,int type) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
        if (type == 1){
            mWheelTime.setPicker(year, month, day, 0, 0);
        }else if (type == 2){
            mWheelTime.setPicker(year, month, day, 23, 59);
        }

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
     * 设置头部背景颜色
     */
    public void setHeadBackgroundColor(int color) {
        mHeadView.setBackgroundColor(color);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTxtTitle.setText(title);
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int resId) {
        mTxtTitle.setTextColor(resId);
    }

    /**
     * 设置标题大小
     */
    public void setTitleSize(float size) {
        mTxtTitle.setTextSize(size);
    }

    /**
     * 设置取消文字
     */
    public void setCancelText(String text) {
        mBtnCancel.setText(text);
    }

    /**
     * 设置取消文字颜色
     */
    public void setCancelTextColor(int resId) {
        mBtnCancel.setTextColor(resId);
    }

    /**
     * 设置取消文字大小
     */
    public void setCancelTextSize(float size) {
        mBtnCancel.setTextSize(size);
    }

    /**
     * 设置确认文字
     */
    public void setSubmitText(String text) {
        mBtnSubmit.setText(text);
    }

    /**
     * 设置确认文字颜色
     */
    public void setSubmitTextColor(int resId) {
        mBtnSubmit.setTextColor(resId);
    }

    /**
     * 设置确认文字大小
     */
    public void setSubmitTextSize(float size) {
        mBtnSubmit.setTextSize(size);
    }

    /**
     * 设置滚动文字大小
     */
    public void setTextSize(float size) {
        mWheelTime.setTextSize(size);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnSubmit) {
            if (mTimeSelectListener != null) {
                try {

                    Date date = WheelTime.dateFormat.parse(mWheelTime.getTime());

                    Log.e("David","date111===="+mWheelTime.getTime());
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
            dismiss();
        } else if (id == R.id.btnCancel) {
            dismiss();
        }

    }



    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.mTimeSelectListener = timeSelectListener;
    }

//    public enum Type {
//        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
//    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public class TextSize {
        public static final float BIG = 6f;
        public static final float DEFAULT = 5f;
        public static final float SMALL = 4f;
    }
}
