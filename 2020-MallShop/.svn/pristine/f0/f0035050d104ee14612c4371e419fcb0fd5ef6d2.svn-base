package com.epro.mall.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.TextUtils
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.SearchResultContract
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.mvp.presenter.SearchResultPresenter
import com.epro.mall.ui.fragment.SearchGoodsListFragment
import com.epro.mall.ui.fragment.ShopNewGoodsListFragment
import com.epro.mall.ui.fragment.SearchShopListFragment
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.ext_getFromJsonWithClassKey
import com.mike.baselib.utils.ext_putJsonExtra
import kotlinx.android.synthetic.main.activity_search_result.*
import java.util.*

/**
 * 搜索结果页面
 */
class SearchResultActivity : BaseTitleBarCustomActivity<SearchResultContract.View, SearchResultPresenter>(), SearchResultContract.View,View.OnClickListener {
    override fun onSearchInShopSuccess() {

    }

    override fun getPresenter(): SearchResultPresenter {
        return SearchResultPresenter()
    }

    override fun onSearchAllSuccess() {

    }

    override fun onClick(p0: View?) {

        when(p0){
            etSearch->{
                val shopId=intent.getStringExtra(SHOP_ID)
                val keyword=intent.getStringExtra(KEY_WORD)
                val location=intent.ext_getFromJsonWithClassKey(Location::class.java)
                SearchActivity.launchWithShopId_Keyword_Location(this,shopId,keyword,location)
                finish()
            }
            ivSearch->{
                val shopId=intent.getStringExtra(SHOP_ID)
                val keyword=intent.getStringExtra(KEY_WORD)
                val location=intent.ext_getFromJsonWithClassKey(Location::class.java)
                SearchActivity.launchWithShopId_Keyword_Location(this,shopId,keyword,location)
                finish()
            }
            ivBack->{
                finish()
            }
            tvCancel->{
                finish()
            }
        }
    }

    companion object {
        const val SHOP_ID = "shopId"
        const val KEY_WORD="keyword"
        fun launchWithKeyWord_ShopId_Location(context: Context, keyword: String, shopId:String?,location: Location?) {
            context.startActivity(Intent(context, SearchResultActivity::class.java)
                    .putExtra(KEY_WORD, keyword).putExtra(SHOP_ID,shopId).ext_putJsonExtra(location))
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_search_result
    }

    override fun initData() {
    }


    override fun initListener() {
        etSearch.setOnClickListener(this)
        ivSearch.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
    }

    private val mFragments = ArrayList<Fragment>()
    private var mTitles = arrayOf(R.string.shop, R.string.product)
    private var mAdapter: MyPagerAdapter? = null
    override fun initView() {
        super.initView()
        getTitleBar().visibility=View.GONE
        val shopId=intent.getStringExtra(SHOP_ID)
        val keyword=intent.getStringExtra(KEY_WORD)
        if(!TextUtils.isEmpty(shopId)){
            tlResults.visibility= View.GONE
            mTitles = arrayOf(R.string.product)
            mFragments.add(SearchGoodsListFragment.newInstance(keyword!!,shopId).setViewPageFragment(true))
            etSearch.setText(keyword)
        }else{
            val location=intent.ext_getFromJsonWithClassKey(Location::class.java)
            mFragments.add(SearchShopListFragment.newInstance(keyword!!,location!!).setViewPageFragment(true))
            mFragments.add(SearchGoodsListFragment.newInstance(keyword,shopId).setViewPageFragment(true))
            etSearch.setText(keyword)
        }
        mAdapter = MyPagerAdapter(supportFragmentManager)
        vpResults.adapter = mAdapter
        tlResults.setViewPager(vpResults)
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return getString(mTitles[position])
        }

        override fun getItem(position: Int): Fragment {
            return mFragments.get(position)
        }
    }

}