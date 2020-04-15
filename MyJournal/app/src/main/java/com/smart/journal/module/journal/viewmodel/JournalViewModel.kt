package com.smart.journal.module.journal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

import com.smart.journal.module.journal.repository.JournalRepository
import com.smart.journal.module.journal.repository.JournalRepositoryImpl
import com.smart.journal.db.entity.JournalBeanDBBean


/**
 * @author guandongchen
 * @date 26/03/2018
 */

class JournalViewModel : ViewModel() {

    private val journalRepository: JournalRepository

    init {

        journalRepository = JournalRepositoryImpl()

    }

     fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {
        journalRepository.deleteJournal(journalBeanDBBean)
    }


     fun searchJournalByKeyWord(keyWord: String): LiveData<List<JournalBeanDBBean>> {
       return journalRepository.searchJournalByKeyWord(keyWord)
    }

     fun searchJournalByTag(tagName: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchJournalByTag(tagName)
    }

    fun searchJournalByLocationName(locationName: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchJournalByLocationName(locationName)
    }


     fun getJournalBeans():LiveData<List<JournalBeanDBBean>>{
         return journalRepository.getJournalBeans()
    }

    public override fun onCleared() {
        journalRepository.onLiveDataCleared()
        super.onCleared()
    }
}
