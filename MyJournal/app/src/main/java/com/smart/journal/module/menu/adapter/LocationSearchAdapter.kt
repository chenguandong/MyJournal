package com.smart.journal.module.menu.adapter

import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.db.entity.JournalBeanDBBean

/**
 *
 * @author guandongchen
 * @date 2020/4/14
 */
class LocationSearchAdapter : BaseQuickAdapter<Map<String, MutableList<JournalBeanDBBean>>, BaseViewHolder> {
    constructor(data: MutableList<Map<String, MutableList<JournalBeanDBBean>>>?) : super(R.layout.item_location_search, data)

    override fun convert(helper: BaseViewHolder, item: Map<String, MutableList<JournalBeanDBBean>>) {
        helper.getView<AppCompatTextView>(R.id.locationNameTextView).text = item.keys.elementAt(0)
        helper.getView<AppCompatTextView>(R.id.locationSizeTextView).text = "${(item[item.keys.elementAt(0)] ?: error("")).size}"
    }


}