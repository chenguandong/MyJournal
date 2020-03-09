package com.smart.journal.module.map.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.module.map.bean.MjPoiItem

/**
 * @author Administrator
 * @date 2019/10/15
 * @desc
 * @email chenguandong@outlook.com
 */

class AMapSearchAdapter(layoutResId: Int, data: MutableList<MjPoiItem>?) : BaseQuickAdapter<MjPoiItem, BaseViewHolder>(layoutResId, data) {
    private var selPosition: Int = -1

    fun setSelPosition(selPosition: Int) {
        this.selPosition = selPosition
        notifyDataSetChanged()
    }

    override fun convert(helper: BaseViewHolder?, mjPoiItem: MjPoiItem?) {

        var adressTitleTextView = helper!!.getView<TextView>(R.id.adressTitleTextView)
        var adressSubTitleTextView = helper.getView<TextView>(R.id.adressSubTitleTextView)
        var arrowImageView = helper.getView<ImageView>(R.id.arrowImageView)

        adressTitleTextView.text = mjPoiItem!!.snippet
        adressSubTitleTextView.text = mjPoiItem.title

        if (selPosition == data.indexOf(mjPoiItem)) {
            arrowImageView.visibility = View.VISIBLE
        } else {
            arrowImageView.visibility = View.GONE
        }

    }

}