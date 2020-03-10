package com.smart.journal.module.write.views

import android.os.Bundle
import android.view.View
import com.smart.journal.R
import com.smart.journal.customview.dialog.BaseBottomSheetDialogFragment
import com.smart.journal.module.write.adapter.MoreSettingAdapter
import com.smart.journal.module.write.bean.MoreSettingBean
import kotlinx.android.synthetic.main.view_dialog_preview_bottom_sheet.*

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
class MoreSettingBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {
    private var adapter: MoreSettingAdapter? = null

    private var itemData= object : ArrayList<MoreSettingBean>() {
        init {
            add(MoreSettingBean("位置", "2222", R.drawable.ic_ws_location))
            add(MoreSettingBean("标签", "444", R.drawable.ic_ws_tag))
            add(MoreSettingBean("日记本", "tttt", R.drawable.ic_ws_journal))
            add(MoreSettingBean("时间", "bbbb", R.drawable.ic_ws_date))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter  = MoreSettingAdapter(itemData)
        recyclerView.adapter = adapter

    }

}