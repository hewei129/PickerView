package com.hw.pickerviewlib;

import android.content.Context;
import android.content.res.AssetManager;


import com.hw.pickerviewlib.listener.OnCityAreaSelectListener;
import com.hw.pickerviewlib.listener.OnCitySelectListener;
import com.hw.pickerviewlib.model.CityBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by David on 2016/12/5.
 * Class desc:
 */
public class CityPickerView extends OptionsPickerView {

    private final Context mContext;
    public OnCitySelectListener mOnCitySelectListener;
    public OnCityAreaSelectListener mOnCityAreaSelectListener;
    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<>();
    // 市数据集合
    private ArrayList<ArrayList<String>> mListCity = new ArrayList<>();
    // 区数据集合
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<>();
    // 省数据集合
    private ArrayList<CityBean> mListProvince1 = new ArrayList<>();
    // 市数据集合
    private ArrayList<ArrayList<CityBean>> mListCity1 = new ArrayList<>();
    // 区数据集合
    private ArrayList<ArrayList<ArrayList<CityBean>>> mListArea1 = new ArrayList<>();
    private JSONObject mJsonObj;
    private CityBean cityBean;
    private boolean isHasArea = true;

    public CityPickerView(Context context) {
        super(context);
        mContext = context;
        // 初始化Json对象
        initJsonData();
        // 初始化Json数据
        initJsonDatas();
        initCitySelect();
    }

    public CityPickerView(Context context, boolean isHasArea) {
        super(context);
        this.isHasArea = isHasArea;
        mContext = context;
        // 初始化Json对象
        initJsonData();
        // 初始化Json数据
        initJsonDatas();
        if (!isHasArea)
            initSelect();
        else
            initCitySelect();
    }

    private void initSelect() {
//        setFragment_title("选择城市");
        setPicker(mListProvince1, mListCity1, true);
        setCyclic(false, false, false);
        setSelectOptions(0, 0);
        setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                if (!isHasArea) {
                    if (mOnCitySelectListener != null) {
                        String prov = mListProvince1.get(option1).getName();
                        String city = mListCity1.get(option1).get(option2).getName();
//                    String area = mListArea1.get(option1).get(option2).get(option3).getName();
                        int provId = mListProvince1.get(option1).getId();
                        int cityId = mListCity1.get(option1).get(option2).getId();
//                    int areaId = mListArea1.get(option1).get(option2).get(option3).getId();
                        mOnCitySelectListener.onCitySelect(prov.concat(city));
                        mOnCitySelectListener.onCitySelect(prov, city);
                        mOnCitySelectListener.onCitySelect(provId, cityId);
                        mOnCitySelectListener.onCitySelect(prov,city,provId,cityId);
//                        }
//                    }
                    }
                }
            }

        });
    }

    private void initCitySelect() {
//        setFragment_title("选择城市");
        setPicker(mListProvince1, mListCity1, mListArea1, true);
        setCyclic(false, false, false);
        setSelectOptions(0, 0, 0);
        setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                try {
                    if (isHasArea) {
                        if (mOnCityAreaSelectListener != null) {

                            String prov = mListProvince1.get(option1).getName();
                            String city = mListCity1.get(option1).get(option2).getName();
                            String area = mListArea1.get(option1).get(option2).get(option3).getName();
                            int provId = mListProvince1.get(option1).getId();
                            int cityId = mListCity1.get(option1).get(option2).getId();
                            int areaId = mListArea1.get(option1).get(option2).get(option3).getId();
                            mOnCityAreaSelectListener.onCitySelect(prov.concat(city).concat(area));
                            mOnCityAreaSelectListener.onCitySelect(prov, city, area);
                            mOnCityAreaSelectListener.onCitySelect(provId, cityId, areaId);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        AssetManager assets = mContext.getAssets();
        try {
            InputStream is = assets.open("city.json");
            byte[] buf = new byte[is.available()];
            is.read(buf);
            String json = new String(buf, StandardCharsets.UTF_8);

//            Gson gson = new Gson();
//            cityBean = gson.fromJson(json, CityBean.class);
            mJsonObj = new JSONObject(json);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Json数据，并释放Json对象
     */
    private void initJsonDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 获取每个省的Json对象
                String province = jsonP.getString("name");
                int province_id = jsonP.getInt("id");
                CityBean cb_province = new CityBean();
                cb_province.setId(province_id);
                cb_province.setName(province);

                ArrayList<CityBean> options2Items_01 = new ArrayList<CityBean>();
                ArrayList<ArrayList<CityBean>> options3Items_01 = new ArrayList<>();
                JSONArray jsonCs = jsonP.getJSONArray("areaList");
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
                    String city = jsonC.getString("name");
                    int city_id = jsonC.getInt("id");
                    CityBean cb_city = new CityBean();
                    cb_city.setId(city_id);
                    cb_city.setName(city);
                    options2Items_01.add(cb_city);// 添加市数据

                    ArrayList<CityBean> options3Items_01_01 = new ArrayList<>();
                    JSONArray jsonAs = jsonC.getJSONArray("areaList");
                    if (jsonAs != null && jsonAs.length() > 0) {
                        for (int k = 0; k < jsonAs.length(); k++) {
                            JSONObject jsonD = jsonAs.getJSONObject(k);// 获取每个市的Json对象
                            String area = jsonD.getString("name");
                            int area_id = jsonD.getInt("id");
                            CityBean cb_area = new CityBean();
                            cb_area.setId(area_id);
                            cb_area.setName(area);
                            options3Items_01_01.add(cb_area);// 添加区数据
                        }
                        options3Items_01.add(options3Items_01_01);
                    }

                }
                mListProvince1.add(cb_province);// 添加省数据
                mListCity1.add(options2Items_01);
                mListArea1.add(options3Items_01);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;

    }

    public void setOnCitySelectListener(OnCitySelectListener listener) {
        this.mOnCitySelectListener = listener;
    }

    public void setOnCityAreaSelectListener(OnCityAreaSelectListener listener) {
        this.mOnCityAreaSelectListener = listener;
    }
}
