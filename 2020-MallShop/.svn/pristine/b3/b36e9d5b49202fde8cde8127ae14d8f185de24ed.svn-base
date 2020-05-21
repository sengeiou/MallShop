package com.epro.mall.mvp.model.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * TODO<json数据源>
 *
 * @author: 小嵩
 * @date: 2017/3/16 15:36
 */

public class JsonBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","title":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private List<CityBean> city;
    private String provinceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvinceId(String provinceId){
        this.provinceId = provinceId;
    }

    public String getProvinceId(){
        return provinceId;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }


    public static class CityBean {
        /**
         * name : 城市
         * title : ["东城区","西城区","崇文区","昌平区"]
         */

        private String name;
        private List<String> area;
        private String id ;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getId(){
            return id;
        }
        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
