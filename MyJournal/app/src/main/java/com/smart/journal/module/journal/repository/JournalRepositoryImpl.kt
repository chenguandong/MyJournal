package com.smart.journal.module.journal.repository

import androidx.lifecycle.MutableLiveData
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.write.db.JournalDBHelper
import java.util.*

/**
 * @author guandongchen
 * @date 26/03/2018
 */

class JournalRepositoryImpl : JournalRepository {

    private val journalBeans = ArrayList<JournalBeanDBBean>()

    private val journalBeansLiveData = MutableLiveData<List<JournalBeanDBBean>>()
    /**
     * 数据库查询出来的数据集合
     */
    private var realmResults: List<JournalBeanDBBean>? = null


    override fun getLiveDataJournalBeans(): MutableLiveData<List<JournalBeanDBBean>> {
        realmResults = JournalDBHelper.allJournals
        journalBeans.clear()
        journalBeans.addAll(realmResults!!)
        journalBeansLiveData.value = realmResults
        return journalBeansLiveData
    }

    override fun getJournalBeans(): List<JournalBeanDBBean> {
        return journalBeans
    }


    override fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {
        JournalDBHelper.deleteJournal( journalBeanDBBean)
        getLiveDataJournalBeans()
    }

    override fun onLiveDataCleared() {

    }


}
