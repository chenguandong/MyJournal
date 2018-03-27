package com.smart.weather.module.journal.repository

import android.arch.lifecycle.MutableLiveData

import com.smart.weather.module.write.bean.JournalBeanDBBean

import io.realm.RealmResults

/**
 * @author guandongchen
 * @date 26/03/2018
 */

interface JournalRepository {

    val liveDataJournalBeans: MutableLiveData<RealmResults<JournalBeanDBBean>>

    val journalBeans: List<JournalBeanDBBean>

    fun deleteJournal(journalBeanDBBean: JournalBeanDBBean)

    fun onLiveDataCleared()
}
