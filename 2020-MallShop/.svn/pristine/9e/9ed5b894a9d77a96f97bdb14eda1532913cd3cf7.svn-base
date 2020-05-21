package com.epro.mall.mvp.model.bean;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.view.View;

import com.epro.mall.R;
import com.mike.baselib.utils.AppContext;

/**
 * 类说明:蓝牙设备的实体类
 * 阳（360621904@qq.com）  2017/4/27  19:57
 */
public class PrinterBean {
    public static final int PRINT_TYPE = 1664;
    //蓝牙-名称
    public String name;
    //蓝牙-地址
    public String address;
    //蓝牙-设备类型
    public int type;
    //蓝牙-是否已经匹配
    public boolean isConnect;

    BluetoothDevice device;

    /**
     * @param devicee 蓝牙设备对象
     */
    public PrinterBean(BluetoothDevice devicee) {
        this.name = TextUtils.isEmpty(devicee.getName()) ? AppContext.getInstance().getString(R.string.unknown) : devicee.getName();
        this.address = devicee.getAddress();
        this.isConnect = devicee.getBondState() == BluetoothDevice.BOND_BONDED;
        this.type = devicee.getBluetoothClass().getDeviceClass();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    /**
     * 260-电脑
     * 1664-打印机
     * 524-智能手机
     *
     * @return
     */
    public int getTypeIcon() {
        if (type == 260) {
            return R.drawable.ic_computer_black_24dp;
        } else if (type == PRINT_TYPE) {
            return R.drawable.ic_local_printshop_black_24dp;
        } else if (type == 524) {
            return R.drawable.ic_phone_android_black_24dp;
        } else {
            return R.drawable.ic_issue;
        }
    }

    public String getDeviceType(View view) {
        if (type == PRINT_TYPE) {
            view.setSelected(true);
            return isConnect ? AppContext.getInstance().getString(R.string.choose_print) : AppContext.getInstance().getString(R.string.point_connection);
        } else {
            view.setSelected(false);
            return AppContext.getInstance().getString(R.string.not_print_device);
        }
    }

    public int getType() {
        return type;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public BluetoothDevice getDevice() {

        return device;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

}
