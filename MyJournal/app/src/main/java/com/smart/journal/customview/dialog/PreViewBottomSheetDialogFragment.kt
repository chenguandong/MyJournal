package com.smart.journal.customview.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.smart.journal.contants.Contancts
import com.smart.journal.customview.preview.PhotoViewTools
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.write.adapter.WriteAdapter
import com.smart.journal.module.write.bean.JournalBean
import kotlinx.android.synthetic.main.view_dialog_preview_bottom_sheet.*
import java.util.*


/**
 * @author guandongchen
 * @date 2018/9/4
 */
@SuppressLint("ValidFragment")
class PreViewBottomSheetDialogFragment : BaseBottomSheetDialogFragment {

    private val writeSectionBeans = ArrayList<JournalBean>()
    private var adapter: WriteAdapter? = null
    private var journalBeanDBBean: JournalBeanDBBean? = null


    constructor(journalBeanDBBean: JournalBeanDBBean) {
        this.journalBeanDBBean = journalBeanDBBean

        if (journalBeanDBBean.content != null) {

            val contents = journalBeanDBBean.content!!.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (content in contents) {
                if (content.startsWith(Contancts.FILE_TYPE_TEXT)) {
                    writeSectionBeans.add(JournalBean(content.replace(Contancts.FILE_TYPE_TEXT, "")))
                } else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)) {
                    writeSectionBeans.add(JournalBean("", content.replace(Contancts.FILE_TYPE_IMAGE, "")))
                }

            }

        }
    }

    constructor()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WriteAdapter(writeSectionBeans, WriteAdapter.WriteAdapterModel.WriteAdapterModel_SHOW)
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
        adapter!!.setOnItemClickListener { adapter, view1, position ->
            if (writeSectionBeans[position].itemType == JournalBean.WRITE_TAG_IMAGE) {
                PhotoViewTools.showPhotos(object : ArrayList<String>() {
                    init {
                        writeSectionBeans[position].imageURL?.let { add(it) }
                    }
                }, 0, context)
            }
        }
    }


}
