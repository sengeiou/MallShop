package com.epro.mall.ui.activity;

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.contract.SearchContract
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.mvp.presenter.SearchPresenter
import com.epro.mall.ui.adapter.SearchAssociateAdapter
import com.epro.mall.ui.view.CommonDialog
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.ext_getFromJsonWithClassKey
import com.mike.baselib.utils.ext_putJsonExtra
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_search.*

/**
 * 搜索页面
 */
class SearchActivity : BaseTitleBarCustomActivity<SearchContract.View, SearchPresenter>(), SearchContract.View, View.OnClickListener {

    override fun onSearchAssociateSuccess(list: ArrayList<String>) {
        searchAssociateAdapter!!.setData(list)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            ivBack -> {
                finish()
            }
            tvCancel -> {
                finish()
            }
            tvClear -> {
                showDeleteRecordsDailog()
            }
            tvSwitch -> {
                // mPresenter.getSearchSuggests(MallConst.GET_SEARCH_SUGGESTS)
            }
        }

    }

    companion object {
        const val SHOP_ID = "shopId"
        const val KEY_WORD = "keyword"
        fun launchWithShopId_Keyword_Location(context: Context, shopId: String?, keyword: String?, location: Location?) {
            context.startActivity(Intent(context, SearchActivity::class.java).putExtra(SHOP_ID, shopId).putExtra(KEY_WORD, keyword).ext_putJsonExtra(location))
        }

    }

    override fun getPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun onGetSearchSuggestsSuccess() {
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_search
    }

    var searchAssociateAdapter: SearchAssociateAdapter? = null
    override fun initData() {
        // mPresenter.getSearchSuggests("")
        initRecentRecordAdapter()
        initSuggestRecordAdapter()
        rvAssociate.layoutManager = LinearLayoutManager(this)
        searchAssociateAdapter = SearchAssociateAdapter(this, ArrayList())
        val keyword = intent.getStringExtra(KEY_WORD)
        searchAssociateAdapter?.keyword = keyword
        rvAssociate.adapter = searchAssociateAdapter
        searchAssociateAdapter!!.onItemClickListener = object : SearchAssociateAdapter.OnItemClickListener {
            override fun onClick(item: String) {
                goToSearchResult(item)
            }
        }
    }

    @SuppressLint("NewApi")
    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
//        etSearch.focusable = View.FOCUSABLE
//        etSearch.showSoftInputOnFocus = true
        etSearch.requestFocus()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        val shopId = intent.getStringExtra(SHOP_ID)
        if (TextUtils.isEmpty(shopId)) {
            etSearch.setHint(getString(R.string.search_shop_or_product))
        } else {
            etSearch.setHint("搜索本店商品")
        }
        etSearch.addTextChangedListener(textWatcher)
        val keyword = intent.getStringExtra(KEY_WORD)
        if (!TextUtils.isEmpty(keyword)) {
            etSearch.setText(keyword)
            etSearch.setSelection(keyword!!.length)
        }

    }

    val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if (!TextUtils.isEmpty(p0.toString())) {
                llRecord.visibility = View.GONE
                searchAssociateAdapter?.keyword = p0.toString()
                mPresenter.searchAssociate(p0.toString(), MallConst.SEARCH_ASSOCIATE)
            } else {
                llRecord.visibility = View.VISIBLE
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        etSearch.removeTextChangedListener(textWatcher)
    }

    override fun initListener() {
        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    AppUtils.closeKeyboard(this@SearchActivity)
                    val content = etSearch.text.toString()
                    if (!TextUtils.isEmpty(content)) {
                        goToSearchResult(content)
                        return true
                    }
                }
                return false
            }
        })

        tvClear.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        tvSwitch.setOnClickListener(this)

        val data = ArrayList<String>()
        data.add(getString(R.string.liqueur))
        data.add(getString(R.string.smoke))
        flSuggest.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener {
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                goToSearchResult(data.get(position))
                return true
            }
        })

    }

    private fun initRecentRecordAdapter() {
        val records = MallBusManager.getSearchRecords()
        val shopId = intent.getStringExtra(SHOP_ID)
        if (TextUtils.isEmpty(shopId)) { //首页搜索
            if(records.isEmpty()){
                flRecent.visibility=View.GONE
                rlRecentHeader.visibility=View.GONE
            }else{
                flRecent.visibility=View.VISIBLE
                rlRecentHeader.visibility=View.VISIBLE
            }
        }
        flRecent.adapter = object : TagAdapter<String>(records) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(this@SearchActivity).inflate(R.layout.item_tag_search, parent, false) as TextView
                tv.text = s
                tv.setOnLongClickListener(View.OnLongClickListener {
                    showDeleteRecordDailog(position, records)
                    return@OnLongClickListener true
                })
                tv.setOnClickListener(View.OnClickListener {
                    goToSearchResult(records.get(position))
                })
                return tv
            }
        }
    }

    private fun showDeleteRecordDailog(position: Int, records: ArrayList<String>) {
        CommonDialog.Builder(this)
                .setContent("确定删除这条记录吗?")
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        records.removeAt(position)
                        MallBusManager.setSearchRecords(records)
                        initRecentRecordAdapter()
                    }
                })
                .create()
                .show()
    }
    private fun showDeleteRecordsDailog() {
        CommonDialog.Builder(this)
                .setContent("确认删除全部历史记录?")
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        MallBusManager.setSearchRecords(ArrayList())
                        initRecentRecordAdapter()
                    }
                })
                .create()
                .show()
    }

    private fun initSuggestRecordAdapter() {
        val data = ArrayList<String>()
        data.add(getString(R.string.liqueur))
        data.add(getString(R.string.smoke))
        flSuggest.adapter = object : TagAdapter<String>(data) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(this@SearchActivity).inflate(R.layout.item_tag_search, parent, false) as TextView
                tv.text = s
                return tv
            }
        }
    }

    private fun goToSearchResult(keyword: String) {
        val shopId = intent.getStringExtra(SHOP_ID)
        MallBusManager.setOneSearchRecord(keyword)
        val location = intent.ext_getFromJsonWithClassKey(Location::class.java)
        SearchResultActivity.launchWithKeyWord_ShopId_Location(this, keyword, shopId, location)
        finish()
    }

}


