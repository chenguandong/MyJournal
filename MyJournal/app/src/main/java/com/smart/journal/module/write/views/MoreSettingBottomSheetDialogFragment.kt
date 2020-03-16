package com.smart.journal.module.write.views

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.smart.journal.R
import com.smart.journal.customview.dialog.BaseBottomSheetDialogFragment
import com.smart.journal.module.write.adapter.MoreSettingAdapter
import com.smart.journal.module.write.bean.MoreSettingBean
import com.smart.journal.module.write.bean.ToolBean
import com.smart.journal.module.write.bean.WriteSettingBean
import kotlinx.android.synthetic.main.view_dialog_preview_bottom_sheet.*
import java.util.*

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
class MoreSettingBottomSheetDialogFragment : BaseBottomSheetDialogFragment {

    val delegate: MoreSettingBottomSheetDialogFragmentDelegate? = null

    var writeSetting: WriteSettingBean? = null

    constructor() : super()

    constructor(writeSettingBean: WriteSettingBean?):super(){
        this.writeSetting = writeSettingBean
    }


    interface MoreSettingBottomSheetDialogFragmentDelegate {
        fun onItemClick(toolBean: ToolBean)
    }

    private var adapter: MoreSettingAdapter? = null
    private var itemData = object : ArrayList<MoreSettingBean>() {
        init {
            add(MoreSettingBean("位置", "", R.drawable.ic_ws_location))
            add(MoreSettingBean("标签", "默认", R.drawable.ic_ws_tag))
            add(MoreSettingBean("日记本", "默认", R.drawable.ic_ws_journal))
            add(MoreSettingBean("时间", "", R.drawable.ic_ws_date))
        }
    }


    override fun setOpenState(){
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemData[0].subTitle =writeSetting?.location?.snippet?:""
        itemData[3].subTitle = writeSetting!!.time?.let { Date(it).toLocaleString() }
        adapter = MoreSettingAdapter(itemData)
        recyclerView.adapter = adapter
        adapter!!.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when(position){
                    //地址
                    0->{

                    }
                    //标签
                    1->{

                    }
                    //日记本
                    2->{

                    }
                    //时间
                    3->{
                    }
                }
            }

        })

    }

}