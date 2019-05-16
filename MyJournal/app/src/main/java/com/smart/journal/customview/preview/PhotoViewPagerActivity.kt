/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smart.journal.customview.preview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.wingsofts.dragphotoview.DragPhotoView
import kotlinx.android.synthetic.main.act_photo.*
import java.util.*


class PhotoViewPagerActivity : BaseActivity() {

    private var choosedIndex: Int = 0

    private var samplePagerAdapter: SamplePagerAdapter? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_photo)
        photoUrl = intent.getSerializableExtra(URLS) as List<String>

        if (photoUrl == null) {
            ToastUtils.showShort("无可显示图片")
            return
        }
        choosedIndex = intent.getIntExtra(URLS_CHOOSE_INDEX, 0)

        samplePagerAdapter = SamplePagerAdapter()
        photoViewPage.setAdapter(samplePagerAdapter)
        photoViewPage.setCurrentItem(choosedIndex)
        photoViewPage.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


    }

    override fun initView() {

    }

    override fun initData() {

    }


    internal inner class SamplePagerAdapter : androidx.viewpager.widget.PagerAdapter() {


        override fun getCount(): Int {
            return photoUrl!!.size
        }

        @SuppressLint("ResourceType")
        override fun instantiateItem(container: ViewGroup, position: Int): View {


            val photoView = DragPhotoView(container.context)

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            photoView.setBackgroundColor(android.R.color.transparent)

            photoView.setOnExitListener { dragPhotoView, v, v1, v2, v3 -> finish() }
            photoView.setOnTapListener { dragPhotoView -> finish() }

            photoView.setBackgroundColor(ContextCompat.getColor(container.context, R.color.black))

            Glide.with(container.context).load(photoUrl!![position]).into(photoView)

            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getItemPosition(`object`: Any): Int {
            return androidx.viewpager.widget.PagerAdapter.POSITION_NONE
        }

    }

    companion object {

        val URLS = "photoURls"//图片集合
        val URLS_CHOOSE_INDEX = "photoURls_choose_index"// 当前选中
        val URLS_SHOW_TAG = "URLS_SHOW_TAG"// 自定义字段


        private var photoUrl: List<String>? = ArrayList()
    }
}
