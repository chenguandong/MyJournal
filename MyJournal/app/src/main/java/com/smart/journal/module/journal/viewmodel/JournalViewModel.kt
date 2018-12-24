package com.smart.journal.module.journal.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.smart.journal.module.journal.repository.JournalRepository
import com.smart.journal.module.journal.repository.JournalRepositoryImpl
import com.smart.journal.module.write.bean.JournalBeanDBBean

import io.realm.RealmResults

/**
 * @author guandongchen
 * @date 26/03/2018
 */

class JournalViewModel : ViewModel(), JournalRepository {

    private val journalRepository: JournalRepository

    private var listMutableLiveData: MutableLiveData<RealmResults<JournalBeanDBBean>>? = null


    init {

        journalRepository = JournalRepositoryImpl()

        if (this.listMutableLiveData == null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            listMutableLiveData = MutableLiveData()
        }
        listMutableLiveData = journalRepository.getLiveDataJournalBeans()

    }

    override fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {
        journalRepository.deleteJournal(journalBeanDBBean)
    }

    override fun onLiveDataCleared() {

    }

    override fun getLiveDataJournalBeans(): MutableLiveData<RealmResults<JournalBeanDBBean>> {
        return journalRepository.getLiveDataJournalBeans()
    }

    override fun getJournalBeans(): List<JournalBeanDBBean> {
        return journalRepository.getJournalBeans()
    }

    public override fun onCleared() {
        journalRepository.onLiveDataCleared()
        super.onCleared()
    }
}
