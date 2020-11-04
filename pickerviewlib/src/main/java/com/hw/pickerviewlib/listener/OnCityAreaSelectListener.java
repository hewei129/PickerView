package com.hw.pickerviewlib.listener;

/**
 * Created by David on 2017/1/10.
 * Class desc: 城市选择回调
 */
public interface OnCityAreaSelectListener {
    void onCitySelect(String str);

    void onCitySelect(String prov, String city, String area);

    void onCitySelect(int prov, int city, int area);
}
