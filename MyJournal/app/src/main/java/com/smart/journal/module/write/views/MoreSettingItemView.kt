package com.smart.journal.module.write.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
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
            itemBean!!.logoImage?.let { logoImageView.setImageDrawable(it) }
        }

    constructor(context: Context?) : super(context) {

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