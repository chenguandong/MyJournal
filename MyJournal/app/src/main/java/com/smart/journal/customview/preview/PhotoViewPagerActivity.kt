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
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.smart.journal.module.journal.JournalFragment
import com.smart.journal.module.journal.SearchEableType
import com.wingsofts.dragphotoview.DragPhotoView
import kotlinx.android.synthetic.main.act_photo.*
import kotlinx.android.synthetic.main.activity_global_search.*
import kotlinx.android.synthetic.main.item_journal.*
import uk.co.senab.photoview.PhotoView
import java.util.*


open class PhotoViewPagerActivity : BottomSheetDialogFragment {

    private var choosedIndex: Int = 0

    private var samplePagerAdapter: SamplePagerAdapter? = null

    protected var behavior: BottomSheetBehavior<*>? = null

    private var photoUrl: List<String>? = ArrayList()

    constructor(choosedIndex: Int, photoUrl: List<String>?) : super() {
        this.choosedIndex = choosedIndex
        this.photoUrl = photoUrl
    }

    internal inner class SamplePagerAdapter : androidx.viewpager.widget.PagerAdapter() {


        override fun getCount(): Int {
            return photoUrl!!.size
        }

        @SuppressLint("ResourceType")
        override fun instantiateItem(container: ViewGroup, position: Int): View {

            val photoView = PhotoView(container.context)

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

            photoView.setBackgroundColor(ContextCompat.getColor(container.context, android.R.color.black))

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
            return POSITION_NONE
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.act_photo, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (photoUrl == null) {
            ToastUtils.showShort("无可显示图片")
            return
        }

        samplePagerAdapter = SamplePagerAdapter()
        photoViewPage.adapter = samplePagerAdapter
        photoViewPage.currentItem = choosedIndex
        photoViewPage.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (context == null) {
            super.onCreateDialog(savedInstanceState)
        } else BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetStyle)
    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        bottomSheet!!.setBackgroundColor(Color.TRANSPARENT)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
            //layoutParams.height = getPeekHeight()
            behavior = BottomSheetBehavior.from(bottomSheet)
            behavior!!.peekHeight = getPeekHeight()
            setOpenState()
        }
        KeyboardUtils.hideSoftInput(requireActivity())
    }

    open fun setOpenState() {
        // 初始为展开状态
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    /**
     * 弹窗高度，默认为屏幕高度的四分之三
     * 子类可重写该方法返回peekHeight
     *
     * @return height
     */
    protected open fun getPeekHeight(): Int {
        val peekHeight = resources.displayMetrics.heightPixels
        //设置弹窗高度为屏幕高度的3/4
        return peekHeight - 0//peekHeight / 10
    }
}
