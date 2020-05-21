package com.epro.comp.im.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.epro.comp.im.R
import com.epro.comp.im.mvp.model.bean.Picture
import com.google.gson.reflect.TypeToken
import com.luck.picture.lib.photoview.PhotoView
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_loadImage
import kotlinx.android.synthetic.main.activity_picture.*


class PictureActivity : BaseSimpleActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            ivBack->{
                finish()
            }
        }
    }

    companion object {
        const val TAG = "PictureActivity"
        const val PICTURE_LIST="picture_list"
        fun launchWithPictures(activity: Activity, pictures:ArrayList<Picture>, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, PictureActivity::class.java).putExtra(PICTURE_LIST, AppBusManager.toJson(pictures)),requestCode)
        }
        fun launchWithPictures(activity: Activity,picture: Picture, requestCode: Int) {
            val pictures=ArrayList<Picture>()
            pictures.add(picture)
            launchWithPictures(activity,pictures,requestCode)
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_picture
    }

    override fun initData() {

    }

    override fun initListener() {
        ivBack.setOnClickListener(this)
    }

    override fun initView() {
        super.initView()
        val type = object : TypeToken<ArrayList<Picture>>() {}.type
        val json=intent.getStringExtra(PICTURE_LIST)
        val pics= AppBusManager.fromJson<ArrayList<Picture>>(json,type)!!
        var position=0
        for (i in pics.indices) {
           if(pics[i].judge()){
               position=i
               break
           }
        }
        tvPage.text = (position + 1).toString() + "/" + pics.size
        tvContent.text = (position + 1).toString() + ""
        vpPhotos.adapter = ImgPagerAdapter(pics, this)
        vpPhotos.currentItem = position
        vpPhotos.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tvPage.text = (position + 1).toString() + "/" + pics.size
                tvContent.text = (position + 1).toString() + ""
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    class ImgPagerAdapter(pics: List<Picture>, context: Context) : PagerAdapter() {
        private val list = ArrayList<PhotoView>()

        init {
            for (i in pics.indices) {
                val pv = PhotoView(context)
                pv.ext_loadImage(pics[i].imageUrl)
                list.add(pv)
            }
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {//必须实现，实例化
            container.addView(list[position])
            return list[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {//必须实现，销毁
            container.removeView(list[position])
        }

    }
}