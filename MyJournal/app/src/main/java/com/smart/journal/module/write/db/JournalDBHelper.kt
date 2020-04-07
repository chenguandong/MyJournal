package com.smart.journal.module.write.db

import android.text.TextUtils
import com.smart.journal.app.MyApp
import com.smart.journal.contants.Contancts
import com.smart.journal.db.dao.JournalDao
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.write.bean.JournalBean
import com.smart.journal.module.write.bean.WriteSettingBean
import com.smart.journal.tools.location.LocationTools


/**
 * @author guandongchen
 * @date 2018/1/22
 */

object JournalDBHelper {

    private var journalDao: JournalDao? = MyApp.database!!.mJournalDao()

    /**
     * 获取所有日记
     * @return
     */
    val allJournals: List<JournalBeanDBBean>
        get() = journalDao!!.allJournal() as List<JournalBeanDBBean>

    /**
     * 保存日记
     * @param writeSectionBeans
     */
    fun saveJournal(writeSectionBeans: List<JournalBean>, settingBean: WriteSettingBean?) {
        val journalBeanDBBean = JournalBeanDBBean()
        val contentSb = StringBuilder()
        for (journalBean in writeSectionBeans) {

            if (journalBean.itemType == JournalBean.WRITE_TAG_IMAGE) {

                contentSb.append(Contancts.FILE_TYPE_IMAGE + journalBean.imageURL + Contancts.FILE_TYPE_SPLIT)
            } else {
                contentSb.append(Contancts.FILE_TYPE_TEXT + journalBean.content + Contancts.FILE_TYPE_SPLIT)

            }
        }
        journalBeanDBBean.content = contentSb.toString()
        journalBeanDBBean.date = settingBean!!.time!!
        if (settingBean.journalBook != null) {
            journalBeanDBBean.bookId = settingBean!!.journalBook!!.id
        }
        if (settingBean!!.location != null) {
            val locationBean = settingBean!!.location
            if (!TextUtils.isEmpty(locationBean!!.title)) {
                journalBeanDBBean.address = locationBean.title
                journalBeanDBBean.latitude = locationBean.latitude
                journalBeanDBBean.longitude = locationBean.longitude
            }
        } else {
            val locationBean = LocationTools.locationBean
            if (!TextUtils.isEmpty(locationBean!!.adress)) {
                journalBeanDBBean.address = locationBean.adress
                journalBeanDBBean.latitude = locationBean.latitude
                journalBeanDBBean.longitude = locationBean.longitude
            }
        }

        settingBean.tags?.let {
            journalBeanDBBean.tags =it.toString().replace("[","").replace("]","")
        }
        settingBean.journalId?.let {
            journalBeanDBBean.id = it
        }
        journalBeanDBBean.favourite = settingBean.favourite
        journalDao!!.saveJournal(journalBeanDBBean)
    }


    /**
     * 删除日记
     * @param journalBeanDBBean
     */
    fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {

        journalDao!!.deleteJournal(journalBeanDBBean)
    }

    fun deleteJournalById(journalId: Int) {
        journalDao!!.deleteJournalById(journalId)
    }

    fun queryJournalByDate(date:Long):List<JournalBeanDBBean> {
       return journalDao!!.queryJournalByDate(date)
    }

    fun queryJournalById(id:Int):List<JournalBeanDBBean> {
        return journalDao!!.queryJournalById(id)
    }
}

