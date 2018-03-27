package com.smart.weather.module.journal.repository;

import android.arch.lifecycle.MutableLiveData;

import com.smart.weather.module.write.bean.JournalBeanDBBean;

import java.util.List;

import io.realm.RealmResults;

/**
 * @author guandongchen
 * @date 26/03/2018
 */

public interface JournalRepository {

     MutableLiveData<RealmResults<JournalBeanDBBean>> getLiveDataJournalBeans();

     List<JournalBeanDBBean> getJournalBeans();

     void deleteJournal(JournalBeanDBBean journalBeanDBBean);
}
