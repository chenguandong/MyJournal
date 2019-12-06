package com.smart.journal.module.write.db

import android.text.TextUtils

import com.smart.journal.app.MyApp
import com.smart.journal.contants.Contancts
import com.smart.journal.module.write.bean.JournalBean
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.tools.location.LocationTools


/**
 * @author guandongchen
 * @date 2018/1/22
 */

object JournalDBHelper {


    /**
     * 获取所有日记
     * @return
     */
    val allJournals: List<JournalBeanDBBean>
        get() = MyApp.database!!.mJournalDao().allJournal

    /**
     * 保存日记
     * @param writeSectionBeans
     */
    fun saveJournal(writeSectionBeans: List<JournalBean>) {
        val journalBeanDBBean = JournalBeanDBBean()
        val contentSb = StringBuilder()
        for (journalBean in writeSectionBeans) {

            if (journalBean.itemType == JournalBean.WRITE_TAG_IMAGE) {

                contentSb.append(Contancts.FILE_TYPE_IMAGE + journalBean.imageURL + Contancts.FILE_TYPE_SPLIT)
            } else {
                if (!TextUtils.isEmpty(journalBean.content.trim { it <= ' ' })) {
                    contentSb.append(Contancts.FILE_TYPE_TEXT + journalBean.content + Contancts.FILE_TYPE_SPLIT)
                }

            }
        }
        journalBeanDBBean.content = contentSb.toString()
        journalBeanDBBean.date = System.currentTimeMillis()
        val locationBean = LocationTools.locationBean
        if (!TextUtils.isEmpty(locationBean!!.adress)) {
            journalBeanDBBean.address = locationBean.adress
            journalBeanDBBean.latitude = locationBean.latitude
            journalBeanDBBean.longitude = locationBean.longitude
        }
        journalBeanDBBean.tags = "默认"
        MyApp.database!!.mJournalDao().saveJournal(journalBeanDBBean)
    }

    /**
     * 删除日记
     * @param journalBeanDBBean
     */
    fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {

        MyApp.database!!.mJournalDao().deleteJournal(journalBeanDBBean)
    }
}