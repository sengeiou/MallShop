package com.epro.mall.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.epro.mall.MallApplication
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.LanguageSelectBean
import com.epro.mall.ui.MainActivity
import com.epro.mall.ui.adapter.LanguageAdapter
import com.mike.baselib.activity.BaseTitleBarSimpleActivity
import com.mike.baselib.base.BaseApplication
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.Constants
import com.mike.baselib.utils.LocaleManager
import kotlinx.android.synthetic.main.language_change.*
import org.jetbrains.anko.startActivity

class LanguageChangeActivity :BaseTitleBarSimpleActivity(){

    override fun layoutContentId(): Int {
        return R.layout.language_change
    }

    companion object{
        const val TAG = "Language"
        const val LANGUAGE_TRADITIONAL:Int = 0
        const val LANGUAGE_SIMPLE:Int = 1
        const val LANGUAGE_ENGLISH:Int = 2
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, LanguageChangeActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.language_change)
        val linearLayoutManager = LinearLayoutManager(this)
        lvList.layoutManager = linearLayoutManager
        var mTitles = arrayOf(R.string.language_chinese_fan,R.string.language_chinese_simple,R.string.language_english)
        var list = ArrayList<LanguageSelectBean>()
        for (index in mTitles.indices){
            list.add(LanguageSelectBean(getString(mTitles[index])))
        }

        var adapter = LanguageAdapter(this,list)
        lvList.adapter = adapter

        adapter.onItemClickListener = object:LanguageAdapter.OnItemClickListener{
            override fun onClick(item: LanguageSelectBean) {
                when(item.title){
                    getString(R.string.language_chinese_fan)->{
                         languageSelect(LANGUAGE_TRADITIONAL,adapter)
                    }

                    getString(R.string.language_chinese_simple)->{
                        languageSelect(LANGUAGE_SIMPLE,adapter)
                    }

                    getString(R.string.language_english) ->{
                        languageSelect(LANGUAGE_ENGLISH,adapter)
                    }
                }
            }
        }
    }

    fun languageSelect(type:Int,adapter:LanguageAdapter){
        adapter.updateListView(type)
        when(type){
            0 ->{
                BaseApplication.localeManager?.setNewLocale(this,LocaleManager.LANGUAGE_TRADITIONAL)
                AppBusManager.setAppLanguage(Constants.TRADITIONAL_CHINESE)
                resetActivity()
            }
            1->{
                BaseApplication.localeManager?.setNewLocale(this,LocaleManager.LANGUAGE_CHINESE)
                AppBusManager.setAppLanguage(Constants.SIMPLIFIED_CHINESE)
                resetActivity()
            }
            2->{
                BaseApplication.localeManager?.setNewLocale(this,LocaleManager.LANGUAGE_ENGLISH)
                AppBusManager.setAppLanguage(Constants.ENGLISH)
                resetActivity()
            }
        }
    }

    private fun resetActivity() {
        finish()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

        /*startActivity<MainActivity>()
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)*/
    }

    override fun initData() {

    }
    override fun initListener() {

    }

}