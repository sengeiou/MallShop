package com.epro.mall.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.epro.mall.R;
import com.epro.mall.mvp.model.bean.JsonBean;
import com.google.gson.Gson;
import com.mike.baselib.utils.AppUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ThreelevelLinkage  {

    public static List<JsonBean> options1Items = new ArrayList<>();
    public static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public static Thread thread;
    public static String areaId;
    public static boolean isLoaded = false;
    public static OptionsPickerView pvOptions;

    public static void showPickerView(Context mContext, TextView editText) {// 弹出选择器

       pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";
                Log.e("YB","provinceId: "+options1Items.get(options1).getProvinceId()+" id: "+options1Items.get(options1).getCityList().get(options2).getId()+" id2: "+options1Items.get(options1).getCityList().get(options2).getArea().get(0));
                Log.e("YB"," options1Items name: "+options1Items.get(options1).getName());
                Log.e("YB","options1: "+options1+" options2: "+options2+" options3: "+options3);
                Log.e("YB","options1Items:"+options1Items.get(options1).getPickerViewText()+" options2Items: "+options2Items.get(options1).get(options2)+" options3Items: "+options3Items.get(options1).get(options2).get(options3));
                String tx = opt1tx +" "+ opt2tx+" "+ opt3tx;
                editText.setText(tx);
                areaId = options1Items.get(options1).getProvinceId()+","+options1Items.get(options1).getCityList().get(options2).getId()+","+options1Items.get(options1).getCityList().get(options2).getArea().get(0);
                getAreaId();
            }
        })
               .setBgColor(Color.WHITE)
               .setTitleBgColor(Color.WHITE)
                .setCancelText(mContext.getString(R.string.delete_dialog_cancel))
                .setCancelColor(Color.parseColor("#333333"))
                .setTitleText(mContext.getString(R.string.pop_HK_area))
                .setSubmitText(mContext.getString(R.string.submitText_ok))
                .setTitleColor(Color.parseColor("#333333"))
                .setSubmitColor(Color.parseColor("#F31B22"))
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项文字颜色
                .setDividerColor(Color.parseColor("#e5e5e5"))
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public static String getAreaId() {
              return areaId;
    }

    public static void initThreadWork(Context mCotext){
        if (thread == null) {//如果已创建就不再重新创建子线程了
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 子线程中解析省市区数据
                    initJsonData(mCotext);
                }
            });
            thread.start();
        }
    }

    public  static void initJsonData(Context mContext) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = AppUtils.Companion.assetsFileToString(mContext,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getTitle() == null
                        || jsonBean.get(i).getCityList().get(c).getTitle().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getTitle());
                }*/
                ArrayList<String> tmp = new ArrayList<>() ; //临时变量去除第一个id
                tmp.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                tmp.remove(0);

                city_AreaList.addAll(tmp);
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        isLoaded = true ;
    }

    public static ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
