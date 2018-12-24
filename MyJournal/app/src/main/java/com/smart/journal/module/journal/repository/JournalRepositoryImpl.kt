package com.smart.journal.module.journal.repository

import android.arch.lifecycle.MutableLiveData
import com.smart.journal.module.write.bean.JournalBeanDBBean
import com.smart.journal.module.write.db.JournalDBHelper
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

/**
 * @author guandongchen
 * @date 26/03/2018
 */

class JournalRepositoryImpl : JournalRepository {

    private val journalBeans = ArrayList<JournalBeanDBBean>()

    private val journalBeansLiveData = MutableLiveData<RealmResults<JournalBeanDBBean>>()
    /**
     * 数据库查询出来的数据集合
     */
    private var realmResults: RealmResults<JournalBeanDBBean>? = null

    private val realm = Realm.getDefaultInstance()



    override fun getLiveDataJournalBeans(): MutableLiveData<RealmResults<JournalBeanDBBean>> {
        realmResults = JournalDBHelper.getAllJournals(realm)
        journalBeans.clear()
        journalBeans.addAll(realmResults!!)
        journalBeansLiveData.value = realmResults
        return journalBeansLiveData
    }

    override fun getJournalBeans(): List<JournalBeanDBBean> {
        return journalBeans
    }


    override fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {
        JournalDBHelper.deleteJournal(realm, journalBeanDBBean)
        getLiveDataJournalBeans()
    }

    override fun onLiveDataCleared() {
        realm.close()
    }


}
