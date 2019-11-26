package com.smart.journal.module.journal.repository

import androidx.lifecycle.MutableLiveData

import com.smart.journal.db.entity.JournalBeanDBBean

/**
 * @author guandongchen
 * @date 26/03/2018
 */

interface JournalRepository {

    fun getLiveDataJournalBeans(): MutableLiveData<List<JournalBeanDBBean>>

    fun getJournalBeans() :List<JournalBeanDBBean>

    fun deleteJournal(journalBeanDBBean: JournalBeanDBBean)

    fun onLiveDataCleared()
}
