package com.smart.journal.module.journal.repository

import androidx.lifecycle.LiveData
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.write.db.JournalDBHelper

/**
 * @author guandongchen
 * @date 26/03/2018
 */

class JournalRepositoryImpl : JournalRepository {


    override fun getJournalBeans(): LiveData<List<JournalBeanDBBean>> {
        return JournalDBHelper.allJournals()
    }


    override fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {
        JournalDBHelper.deleteJournal(journalBeanDBBean)
    }

    override fun onLiveDataCleared() {

    }

    override fun searchJournalByKeyWord(keyWord: String): LiveData<List<JournalBeanDBBean>> {
        return JournalDBHelper.searchJournalByKeyWord(keyWord)
    }

    override fun searchJournalByTag(tagName: String): LiveData<List<JournalBeanDBBean>> {
        return JournalDBHelper.searchJournalByTag(tagName)
    }

    override fun searchJournalByFavourite(keyWord: String): LiveData<List<JournalBeanDBBean>> {
        return JournalDBHelper.searchJournalFromFavourite(keyWord)
    }

    override fun searchJournalByLocation(addressName: String): LiveData<List<JournalBeanDBBean>> {
        return JournalDBHelper.searchJournalByLocationName(addressName)
    }

    override fun searchJournalByLocationName(locationName: String): LiveData<List<JournalBeanDBBean>> {
        return JournalDBHelper.searchJournalByLocationName(locationName)
    }


}
