package com.smart.weather.module.write.Views

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.smart.weather.R
import kotlinx.android.synthetic.main.view_toolbar.view.*
import java.util.*


/**
 * @author guandongchen
 * @date 2018/1/18
 */

class ToolView : LinearLayout {

    private var toolViewAdapter: ToolViewAdapter? = null
    private var delegate: ToolViewDelegate? = null
    private val toolBeans = object : ArrayList<ToolBean>() {

        init {
            add(ToolBean(R.mipmap.fujian, ToolBean.TOOL_IMAGE))
            add(ToolBean(R.drawable.ic_weizhi, ToolBean.TOOL_WEATHER))
        }
    }

    interface ToolViewDelegate {
        fun onItemClick(toolBean: ToolBean)
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.view_toolbar, this)

        toolViewAdapter = ToolViewAdapter(R.layout.item_toolview, toolBeans)

        view.toolbarRecycleView.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)

        view.toolbarRecycleView.adapter = toolViewAdapter

        view.setBackgroundColor(ContextCompat.getColor(context, R.color.write))

        toolViewAdapter!!.setOnItemClickListener { _, _, position ->

            if (delegate != null) {
                delegate!!.onItemClick(toolBeans[position])
            }
        }
    }


    fun setDelegate(delegate: ToolViewDelegate) {
        this.delegate = delegate
    }
}
