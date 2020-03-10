package com.smart.journal.module.write.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.module.write.bean.MoreSettingBean
import com.smart.journal.module.write.views.MoreSettingItemView

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
class MoreSettingAdapter:BaseQuickAdapter<MoreSettingBean,BaseViewHolder> {
    constructor(data: MutableList<MoreSettingBean>?) : super(R.layout.item_write_more, data)
    override fun convert(helper: BaseViewHolder?, item: MoreSettingBean?) {
       var itemView:MoreSettingItemView =  helper!!.getView(R.id.mItemView)
        itemView.itemBean = item
    }
}