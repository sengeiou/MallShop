package com.epro.mall.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.epro.mall.R;
import com.epro.mall.mvp.model.bean.AreaPhoneBean;

import java.util.ArrayList;

/**
 * 国家地区列表适配器
 * @author  dlong
 * created at 2019/3/26 11:46 AM
 */
public class AreaCodeListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AreaPhoneBean> mBeans;

    public AreaCodeListAdapter(Context context, ArrayList<AreaPhoneBean> beans){
        mContext = context;
        mBeans = beans;
    }

    //当列表数据发生变化时,用此方法来更新列表
    public void updateListView(ArrayList<AreaPhoneBean> beans){
        this.mBeans = beans;
        notifyDataSetChanged();
    }

    // 获得当前列表数据
    public ArrayList<AreaPhoneBean> getList(){
        return mBeans;
    }

    @Override
    public int getCount() {
        return mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_area, parent, false);
            mHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            mHolder.txtTag = (TextView) convertView.findViewById(R.id.txt_tag);
          //  mHolder.imgLine = (ImageView) convertView.findViewById(R.shopId.img_line);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setId(position);
        mHolder.txtName.setText(mBeans.get(position).name);
        String fisrtSpell = mBeans.get(position).fisrtSpell.toUpperCase();
        if (position == 0){
            mHolder.txtTag.setVisibility(View.VISIBLE);
            if (mBeans.get(position).type == 1) {
                mHolder.txtTag.setText(R.string.common_country_list);
                mHolder.txtTag.setTextColor(Color.parseColor("#666666"));
            } else {
                mHolder.txtTag.setText(fisrtSpell);
            }
          //  mHolder.imgLine.setVisibility(View.VISIBLE);
        }else {
            String lastFisrtSpell = mBeans.get(position-1).fisrtSpell.toUpperCase();
            if (fisrtSpell.equals(lastFisrtSpell)){
                mHolder.txtTag.setVisibility(View.GONE);
               // mHolder.imgLine.setVisibility(View.GONE);
            }else {
                mHolder.txtTag.setVisibility(View.VISIBLE);
                mHolder.txtTag.setText(fisrtSpell);
              //  mHolder.imgLine.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView txtName,txtTag;
       // private ImageView imgLine;
    }
}
