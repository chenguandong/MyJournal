package com.smart.journal.module.tags.adapter

import android.R.color
import android.graphics.PorterDuff
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.module.tags.bean.TagsDbBean


/**
 *
 * @author guandongchen
 * @date 2020/3/24
 */
class TagAdapter : BaseQuickAdapter<TagsDbBean, BaseViewHolder> {

    var selectedTags: List<String>? = listOfNotNull()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(data: MutableList<TagsDbBean>?) : super(R.layout.item_tag, data)

    override fun setNewData(data: MutableList<TagsDbBean>?) {
        super.setNewData(data)

    }

    override fun convert(helper: BaseViewHolder, item: TagsDbBean) {
        var tagTextView: AppCompatTextView = helper.getView(R.id.tagTextView)
        tagTextView.text = item.name

        if (selectedTags!!.contains(item.name)) {
            tagTextView!!.compoundDrawables[0].setColorFilter(context.resources.getColor(color.holo_orange_dark), PorterDuff.Mode.SRC_ATOP)
        } else {
            tagTextView!!.compoundDrawables[0].setColorFilter(context.resources.getColor(color.black), PorterDuff.Mode.SRC_ATOP)
        }


    }
}