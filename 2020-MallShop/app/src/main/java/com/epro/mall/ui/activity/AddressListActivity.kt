package com.epro.mall.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.AddressListContract
import com.epro.mall.mvp.model.bean.Address
import com.epro.mall.mvp.model.bean.AddressListBean
import com.epro.mall.mvp.model.bean.DeleteAddressBean
import com.epro.mall.mvp.model.bean.ShopInfo
import com.epro.mall.mvp.presenter.AddressListPresenter
import com.epro.mall.ui.adapter.AddressListAdapter
import com.epro.mall.ui.view.CommonDialog
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.activity.BaseTitleBarListActivity
import com.mike.baselib.utils.ext_getFromJsonWithClassKey
import com.mike.baselib.utils.ext_putJsonExtra
import com.mike.baselib.utils.ext_removeFragment
import kotlinx.android.synthetic.main.activity_address_list.*


/**
 * 地址列表
 */

class AddressListActivity : BaseTitleBarListActivity<Address, AddressListContract.View
        , AddressListPresenter, AddressListAdapter>(), AddressListContract.View, View.OnClickListener {
    override fun getListData() {
        mPresenter.getAddressList(MallConst.GET_MY_ADDRESS_LIST, "0")
    }

    override fun getListAdapter(list: ArrayList<Address>): AddressListAdapter {
        return AddressListAdapter(this, list)
    }

    override fun getPresenter(): AddressListPresenter {
        return AddressListPresenter()
    }

    override fun onAddressDeleteSuccess(result: DeleteAddressBean) {
        getListData()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnAdd -> {
                EditAddressActivity.launchWithAddress(this, null, FOR_ADDRESS_ADD)
            }
        }
    }

    override fun layoutCustomContentId(): Int {
        return R.layout.activity_address_list
    }

    companion object {
        const val TAG = "AddressList"
        const val FOR_ADDRESS_ADD = 10
        const val FOR_ADDRESS_EDIT = 11
        fun launchWithAddress(activity: Activity, address: Address?, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, AddressListActivity::class.java).ext_putJsonExtra(address), requestCode)
        }

        fun launchWithShopInfo_Address(activity: Activity, shopInfo: ShopInfo, address: Address?, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, AddressListActivity::class.java)
                    .ext_putJsonExtra(address).ext_putJsonExtra(shopInfo), requestCode)
        }
    }

    override fun getListDataSuccess(list: List<Address>, type: String) {
        if (address != null) {
            val addressList = MallBusManager.filterAddress(intent.ext_getFromJsonWithClassKey(ShopInfo::class.java)!!, list as ArrayList<Address>)
            for(a in addressList){
                if(a.id==address!!.id){
                    a.judgeValue=true
                    break
                }
            }
            super.getListDataSuccess(addressList, type)
        } else {
            super.getListDataSuccess(list, type)
        }

    }

    override fun initData() {
    }

    var address: Address? = null //不为空代表选择地址
    override fun initView() {
        super.initView()
        getListData()
        //getRefreshView().setEnableRefresh(false)
        getRefreshView().setEnableLoadMore(false)
        address = intent.ext_getFromJsonWithClassKey(Address::class.java)
        if (address == null) {
            getTitleView().text = "我的地址列表"
        } else {
            getTitleView().text = "选择地址"
        }
        listDataAdapter?.address = address
        listDataAdapter?.onItemClickListener = object : AddressListAdapter.OnItemClickListener {
            override fun onClick(item: Address) {
                if (address == null) {
                    EditAddressActivity.launchWithAddress(this@AddressListActivity, item, FOR_ADDRESS_EDIT)
                } else {
                    setResult(Activity.RESULT_OK, Intent().ext_putJsonExtra(item))
                    finish()
                }
            }
        }
        listDataAdapter?.onItemEditClickListener = object : AddressListAdapter.OnItemEditClickListener {
            override fun onItemEditClick(item: Address) {
                EditAddressActivity.launchWithAddress(this@AddressListActivity, item, FOR_ADDRESS_EDIT)
            }
        }

        listDataAdapter?.onItemLongClickListener = object : AddressListAdapter.OnLongClickListener {
            override fun onLongClick(item: Address) {
                if (address == null) {
                    showDeleteDailog(item)
                }
            }
        }

    }

    override fun initListener() {
        btnAdd.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                FOR_ADDRESS_ADD -> {
                    getListData()
                }
                FOR_ADDRESS_EDIT -> {
                    getListData()
                }
            }
        }
    }

    private fun showDeleteDailog(item: Address) {
        CommonDialog.Builder(this)
                .setContent(getString(R.string.delete_dialog_info))
                .setCancelText(getString(R.string.delete_dialog_cancel))
                .setConfirmText(getString(R.string.delete_dialog_delete))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        mPresenter.deleteAddress(MallConst.DELETE_ADDRESS, item.id)
                    }
                })
                .create()
                .show()
    }

}


