package com.hw.pickerviewlib.listener;

/**
 * Created by David on 2017/1/10.
 * Class desc: 城市选择回调
 */
public interface OnCitySelectListener {
    void onCitySelect(String str);

    void onCitySelect(String prov, String city);

    void onCitySelect(int prov, int city);

    void onCitySelect(String prov, String city, int provId, int cityId);
}
