package com.smart.journal.module.journal.manager

import android.content.Context
import android.content.Intent
import com.smart.journal.contants.Contancts
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.module.write.WriteFragment
import com.smart.journal.module.write.activity.WriteActivity
import com.smart.journal.module.write.bean.JournalBean
import com.smart.journal.module.write.bean.WriteSettingBean
import com.smart.journal.module.write.db.NoteBookDBHelper
import java.util.ArrayList

/**
 *
 * @author guandongchen
 * @date 2020/3/31
 */
object JournalManager {

    fun preViewJournal(context: Context,journalBeanDBBean: JournalBeanDBBean){
        var writeSettingBean  = WriteSettingBean()
        journalBeanDBBean.bookId?.let {
            var noteBooks:List<NoteBookDBBean> =   NoteBookDBHelper.queryNoteBook(it)
            if (noteBooks.isNotEmpty()) {
                writeSettingBean!!.journalBook = noteBooks[0]
            }
        }
        writeSettingBean?.time = journalBeanDBBean.date
        writeSettingBean?.location?.let {
            MjPoiItem()
        }
        journalBeanDBBean?.address?.let {
            var locationBean: MjPoiItem = MjPoiItem()
            locationBean.title = it
            locationBean.snippet = it
            locationBean.longitude =journalBeanDBBean.longitude
            locationBean.latitude =journalBeanDBBean.latitude
            writeSettingBean!!.location = locationBean
        }


        val writeSectionBeans = ArrayList<JournalBean>()
        if (journalBeanDBBean.content != null) {

            // val contents = journalBeanDBBean.content!!.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val contents = journalBeanDBBean.content!!.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (content in contents) {
                if (content.startsWith(Contancts.FILE_TYPE_TEXT)) {
                    writeSectionBeans.add(JournalBean(content.replace(Contancts.FILE_TYPE_TEXT, "")))
                } else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)) {
                    writeSectionBeans.add(JournalBean("", content.replace(Contancts.FILE_TYPE_IMAGE, "")))
                }

            }

        }
        journalBeanDBBean.id?.let {
            writeSettingBean.journalId = it
        }
        journalBeanDBBean.tags?.let {
            writeSettingBean.tags = it.split(",")
        }

        writeSettingBean.isFavourite = journalBeanDBBean!!.favourite!!

        context.startActivity(Intent(context, WriteActivity::class.java).putExtra(
                WriteFragment.SHOW_DATA,writeSectionBeans
        ).putExtra(WriteFragment.SHOW_DATA_SETTING,writeSettingBean))
    }
}