package com.smart.journal.module.journal.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.contants.Contancts
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.journal.bean.JournalItemBean
import com.smart.journal.tools.DateTools
import com.smart.journal.tools.StringTools
import java.util.*

/**
 * @author guandongchen
 * @date 2018/1/22
 */

class JournalAdapter : BaseQuickAdapter<JournalBeanDBBean, BaseViewHolder> {
    constructor(layoutResId: Int, data: List<JournalBeanDBBean>?) : super(layoutResId, data as MutableList<JournalBeanDBBean>?)

    override fun convert(helper: BaseViewHolder, item: JournalBeanDBBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)
        val contentTextview = helper.getView<TextView>(R.id.contentView)
        val infoTextview = helper.getView<TextView>(R.id.infoTextView)
        val weekTextview = helper.getView<TextView>(R.id.weekTextView)
        val dayTextview = helper.getView<TextView>(R.id.dayTextView)

        var imageURL = ""
        if (item.journalItemBean == null) {
            val itemBean = JournalItemBean()
            val contentBuilder = StringBuilder()

            if (item.content != null) {

                val contents = item.content!!.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                for (content in contents) {
                    if (content.startsWith(Contancts.FILE_TYPE_TEXT)) {
                        contentBuilder.append(content.substring(Contancts.FILE_TYPE_TEXT.length, content.length))
                    } else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)) {
                        if (TextUtils.isEmpty(imageURL)) {
                            imageURL = content.substring(Contancts.FILE_TYPE_IMAGE.length, content.length)
                        }

                    }

                }


                itemBean.content = contentBuilder.toString()
                item.journalItemBean = itemBean
            }
        }

        contentTextview.text = item.journalItemBean!!.content
        if (item.address != null) {
            infoTextview.text = item.bookName + "." + StringTools.getNotNullString(item.address)
        }
        weekTextview.text = DateTools.getChineseWeek(Date(item.date))
        dayTextview.text = DateTools.getYMd(Date(item.date))[2].toString() + ""

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(context).load(imageURL).into(imageView)
            imageView.visibility = View.VISIBLE
        } else {
            imageView.visibility = View.GONE
        }
    }
}
