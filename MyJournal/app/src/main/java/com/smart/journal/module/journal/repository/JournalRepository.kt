package com.smart.journal.module.journal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.smart.journal.db.entity.JournalBeanDBBean

/**
 * @author guandongchen
 * @date 26/03/2018
 */

interface JournalRepository {

    fun getJournalBeans() :LiveData<List<JournalBeanDBBean>>

    fun deleteJournal(journalBeanDBBean: JournalBeanDBBean)

    fun onLiveDataCleared()

    fun searchJournalByKeyWord(keyWord:String): LiveData<List<JournalBeanDBBean>>

    fun searchJournalByTag(tagName:String): LiveData<List<JournalBeanDBBean>>

    fun searchJournalByFavourite(keyWord: String): LiveData<List<JournalBeanDBBean>>

    fun searchJournalByLocation(addressName:String): LiveData<List<JournalBeanDBBean>>

    fun searchJournalByLocationName(locationName:String): LiveData<List<JournalBeanDBBean>>
}
