package com.epro.mall.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.epro.mall.R;
import com.epro.mall.mvp.model.bean.LocationBean;

import java.util.ArrayList;

public class LocationListAdapter  extends BaseAdapter {

    public Context mContext;
    public ArrayList<LocationBean> mArrayList;
    public int selectPosition;
    public LocationListAdapter(Context mContext, ArrayList<LocationBean> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    //当列表数据发生变化时,用此方法来更新列表
    public void updateListView(int select){
        this.selectPosition = select;
        notifyDataSetChanged();
    }

    // 获得当前列表数据
    public ArrayList<LocationBean> getList(){
        return mArrayList;
    }
    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      final ViewHolder mHolder;
      if(convertView == null){
          mHolder = new LocationListAdapter.ViewHolder();
          convertView = LayoutInflater.from(mContext).inflate(R.layout.item_area, parent, false);
          mHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
          mHolder.txtTag = (TextView) convertView.findViewById(R.id.txt_tag);
          mHolder.imageView = (ImageView)convertView.findViewById(R.id.img_ok);
          convertView.setTag(mHolder);
      }else{
         mHolder = (ViewHolder) convertView.getTag();
      }
        convertView.setId(position);
        mHolder.txtName.setText(mArrayList.get(position).location);
       int type = mArrayList.get(position).type;
        if(0==position){
            mHolder.txtTag.setVisibility(View.GONE);
        }else{
            int lastType = mArrayList.get(position-1).type;
            if (type==lastType){
                mHolder.txtTag.setVisibility(View.GONE);
            }else {
                mHolder.txtTag.setVisibility(View.VISIBLE);
                mHolder.txtTag.setText(mArrayList.get(position).region);
            }
        }
        if (selectPosition ==position){
            mHolder.imageView.setVisibility(View.VISIBLE);
        }else {
            mHolder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView txtName,txtTag;
        private ImageView imageView;
    }
}
