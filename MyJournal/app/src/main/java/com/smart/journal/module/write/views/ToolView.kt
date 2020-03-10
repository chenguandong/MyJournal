package com.smart.journal.module.write.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.journal.R
import com.smart.journal.module.write.bean.ToolBean
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
            add(ToolBean(R.drawable.ic_ws_image, ToolBean.ToolBeanType.TOOL_IMAGE))
            add(ToolBean(R.drawable.ic_ws_location, ToolBean.ToolBeanType.TOOL_LOCATION))
            add(ToolBean(R.drawable.ic_more, ToolBean.ToolBeanType.TOOL_MORE))
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
         View.inflate(context, R.layout.view_toolbar, this)

        toolViewAdapter = ToolViewAdapter(R.layout.item_toolview, toolBeans)

        toolbarRecycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        toolbarRecycleView.adapter = toolViewAdapter

        setBackgroundColor(ContextCompat.getColor(context, R.color.write))

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
