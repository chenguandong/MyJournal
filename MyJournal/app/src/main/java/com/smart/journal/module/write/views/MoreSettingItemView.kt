package com.smart.journal.module.write.views

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.smart.journal.R
import com.smart.journal.module.write.bean.MoreSettingBean
import kotlinx.android.synthetic.main.item_more_setting.view.*

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
class MoreSettingItemView : LinearLayout {
    var itemBean: MoreSettingBean? = null
        set(value) {
            field = value
            itemTitleTextView.text = itemBean!!.title
            itemSubTitleTextView.text = itemBean!!.subTitle
            itemBean!!.logoImage?.let {
                logoImageView.setImageDrawable(it)
            }
            refreshColor()

        }

    constructor(context: Context?) : super(context) {

    }

    private fun refreshColor() {

        when (itemBean!!.subTitle) {
            resources.getString(R.string.favourite) -> DrawableCompat.setTint(logoImageView.drawable, ContextCompat.getColor(context, R.color.default_red))//logoImageView.setColorFilter(R.color.default_red, PorterDuff.Mode.SRC_ATOP)
            resources.getString(R.string.un_favourite) -> logoImageView.setColorFilter(ContextCompat.getColor(context, R.color.default_yellow), PorterDuff.Mode.SRC_ATOP)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, itemBean: MoreSettingBean?) : super(context) {
        this.itemBean = itemBean
    }


    init {
        initView(context)
    }

    fun initView(context: Context) {
        View.inflate(context, R.layout.item_more_setting, this)
    }


}