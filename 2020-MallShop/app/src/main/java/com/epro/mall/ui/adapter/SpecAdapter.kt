package com.epro.mall.ui.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Goods
import com.epro.mall.mvp.model.bean.Product
import com.epro.mall.mvp.model.bean.Spec
import com.epro.mall.mvp.model.bean.SpecValue
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_sizeOfTrue
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class SpecAdapter(mContext: Context, goods: Goods?, layoutId: Int = R.layout.item_spec) :
        CommonAdapter<Spec>(mContext, goods?.specificationsList!!, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(spec: Spec)
    }

    var goods: Goods? = null
    val inStock = ArrayList<Product>()//所有库存大于0的产品
    val selectedSvMap = HashMap<String, SpecValue>() //已经被选中的规格
    val routeList = ArrayList<String>() //所有可以点击的路径

    init {
        this.goods = goods
        for (product in goods?.productList!!) {
            if (product.productNumber > 0) {
                inStock.add(product)
            }
        }
        /**
         * 集成所有存在库存的路径
         */
        for (product in inStock) {
            val specValueList = getSpecVauleList(product.specificationsValueIds)
            val subSetList = getSubset(specValueList)
            for (id in subSetList!!) {
                if (!routeList.contains(id)) {
                    routeList.add(id)
                }
            }
        }
        logTools.d(AppBusManager.toJson(routeList))

        //对所有stock>0的产品都不存在的规格进行置灰
        for (spec in mData) {
            for (specValue in spec.specificationsValueList) {
                var flag = true
                for (product in inStock) {
                    val productIdList=getSpecVauleList(product.specificationsValueIds)
                    if (productIdList.contains(specValue.id)) {
                        flag = false
                        break
                    }
                }
                if (flag) {
                    specValue.isLongLock = true
                    specValue.isLock = true
                    specValue.judgeValue = false
                }
            }
            //如果只有一个属性
            if (spec.specificationsValueList.size == 1) {
                if (!spec.specificationsValueList[0].isLongLock) {
                    spec.judgeValue = true
                    spec.specificationsValueList[0].judgeValue = true
                }
            }
        }

        for (spec in mData) {
            for (specValue in spec.specificationsValueList) {
                if (specValue.judge()) {
                    selectedSvMap.put(spec.id, specValue)
                }
            }
        }
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnChildItemSelectListener {
        fun onChildItemSelect(spec: Spec, specValue: SpecValue)
    }

    interface OnProductSelectListener {
        fun onProductSelect(product: Product?)
    }

    var onChildItemSelectListener: OnChildItemSelectListener? = null
    var onProductSelectListener: OnProductSelectListener? = null
    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Spec, position: Int) {

        holder.setText(R.id.tvSpecName, data.name)
        val rvSpecValues = holder.getView<RecyclerView>(R.id.rvSpecValues)
        rvSpecValues.layoutManager = GridLayoutManager(mContext, 3)
        rvSpecValues.adapter = SpecValueAdapter(mContext, data.specificationsValueList)
        (rvSpecValues.adapter as SpecValueAdapter).onItemSelectListener = object : SpecValueAdapter.OnItemSelectListener {
            override fun onItemSelect(specValue: SpecValue) {
                data.judgeValue = ext_sizeOfTrue(data.specificationsValueList) != 0
                //用户点击规格进行匹配
                if (specValue.judge()) {
                    selectedSvMap.put(data.id, specValue)
                } else {
                    selectedSvMap.remove(data.id)
                }
                findProduct()
                //拼接ids
                val selectedSvIdList = ArrayList<String>()
                for (s in selectedSvMap) {
                    selectedSvIdList.add(s.value.id)
                }
                //置灰
                setSpecValueToGray(selectedSvIdList)
                notifyDataSetChanged()
                //onChildItemSelectListener?.onChildItemSelect(data,specValue)
            }
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(data)
                    notifyDataSetChanged()
                }

            }
        })
    }

    /**
     * 匹配sku Product
     */
    fun findProduct() {
        if (selectedSvMap.isNotEmpty()) {
            logTools.d(AppBusManager.toJson(selectedSvMap))
            //拼接ids
            val selectedSvIdList = ArrayList<String>()
            for (s in selectedSvMap) {
                selectedSvIdList.add(s.value.id)
            }

            logTools.d(AppBusManager.toJson(selectedSvIdList))
            val selectedSvIds = getSortSpecValueIdsStr(selectedSvIdList)
            logTools.d(selectedSvIds)
            var selectedProduct: Product? = null
            for (product in goods?.productList!!) {
                if (product.specificationsValueIds.equals(selectedSvIds)) {
                    selectedProduct = product
                    break
                }
            }
            onProductSelectListener?.onProductSelect(selectedProduct)
        }else{
            onProductSelectListener?.onProductSelect(null)
        }
    }


    /**
     * 对无库存的规格进行置灰
     */
    private fun setSpecValueToGray(selectedSvIdList: ArrayList<String>) {
        for (sp in mData) {
            for (spv in sp.specificationsValueList) {
                if (!spv.isLongLock) {
                    spv.isLock = false
                    if (selectedSvIdList.size > 0 && !selectedSvIdList.contains(spv.id)) {
                        val subSetList = getSubset(selectedSvIdList)
                        for (subStr in subSetList!!) {//遍历选中集合的子集,与当前的spv进行有序拼接
                            val subStrList = getSpecVauleList(subStr)
                            var flag = true
                            for (str in subStrList) {
                                if (getParentSpecId(str).equals(spv.parentId)) {//如果是同一个父id,不能拼接
                                    flag = false
                                    break
                                }
                            }
                            if (flag) {
                                subStrList.add(spv.id)
                            }
                            val str = getSortSpecValueIdsStr(subStrList)
                            if (!routeList.contains(str)) {
                                spv.isLock = true
                                spv.judgeValue = false
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 获取一个集合的子集,用 _ 拼接处理过了
     */
    private fun getSubset(L: ArrayList<String>): ArrayList<String>? {
        if (L.size == 0) {
            return null
        }
        val result = ArrayList<String>()
        var i = 0
        while (i < Math.pow(2.0, L.size.toDouble())) {// 集合子集个数=2的该集合长度的乘方
            val subSet = ArrayList<String>()
            var index = i// 索引从0一直到2的集合长度的乘方-1
            for (j in L.indices) {
                // 通过逐一位移，判断索引那一位是1，如果是，再添加此项
                if (index and 1 == 1) {// 位与运算，判断最后一位是否为1
                    subSet.add(L[j])
                }
                index = index shr 1// 索引右移一位
            }
            var subSetStr = ""
            for (j in subSet.indices) {
                if (j == 0) {
                    subSetStr = subSet[j]
                } else {
                    subSetStr = subSetStr + "_" + subSet[j]
                }
            }
            if (!TextUtils.isEmpty(subSetStr)) {
                result.add(subSetStr) // 把子集存储起来
            }
            i++
        }
        return result
    }

    /**
     * 将用 _ 拼接好的字符串转成集合
     */
    private fun getSpecVauleList(specValueIds: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.addAll(specValueIds.split("_"))
        logTools.toJson(list)
        return list
    }

    /**
     * 将集合转成有序的用 _ 拼接的字符串
     */

    private fun getSortSpecValueIdsStr(list: ArrayList<String>): String {
        list.sort()
        var ids = ""
        for (i in list.indices) {
            if (i == 0) ids += list[i] else ids = ids + "_" + list[i]
        }
        return ids
    }

    /**
     * 查找规格的父id
     */

    private fun getParentSpecId(specValue: String): String {
        for (sp in mData) {
            for (spv in sp.specificationsValueList) {
                if (spv.id.equals(specValue)) {
                    return spv.parentId
                }
            }
        }
        return ""
    }
}
