package com.smart.journal.module.menu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.smart.journal.R
import kotlinx.android.synthetic.main.header_slide_menu_view.view.*

/**
 *
 * @author guandongchen
 * @date 2020/4/7
 */
class SlideMenuHeaderView : LinearLayout ,View.OnClickListener{
    constructor(context: Context?) : super(context){
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

    }

    private fun initView() {
            View.inflate(context, R.layout.header_slide_menu_view,this)
            tagTextView.setOnClickListener(this)
            locationTextView.setOnClickListener(this)
            favouriteTextView.setOnClickListener(this)
            onThisDayTextView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}