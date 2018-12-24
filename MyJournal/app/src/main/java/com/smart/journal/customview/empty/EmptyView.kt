package com.smart.journal.customview.empty

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.smart.journal.R
import kotlinx.android.synthetic.main.view_empty_no_data.view.*

/**
 * Created by guandongchen on 2017/3/2.
 */

class EmptyView : LinearLayout {

    private var view:View? = null
    // 空内容提醒
    fun setTitle(title: String) {

        this.emptyTextView!!.text = title
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
         view = View.inflate(context, R.layout.view_empty_no_data, this)
    }
}
