package com.smart.weather.module.journal.repository;

import android.arch.lifecycle.MutableLiveData;

import com.smart.weather.module.write.bean.JournalBeanDBBean;
import com.smart.weather.module.write.db.JournalDBHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author guandongchen
 * @date 26/03/2018
 */

public class JournalRepositoryImpl implements JournalRepository{

    private List<JournalBeanDBBean> journalBeans = new ArrayList<>();

    private MutableLiveData<RealmResults<JournalBeanDBBean>> journalBeansLiveData = new MutableLiveData<>();
    /**
     * 数据库查询出来的数据集合
     */
    private RealmResults<JournalBeanDBBean> realmResults;

    private Realm realm = Realm.getDefaultInstance();

    public JournalRepositoryImpl() {

    }

    @Override
    public MutableLiveData<RealmResults<JournalBeanDBBean>> getLiveDataJournalBeans() {
        realmResults= JournalDBHelper.getAllJournals(realm);
        journalBeans.clear();
        journalBeans.addAll(realmResults);
        journalBeansLiveData.setValue(realmResults);
        return journalBeansLiveData;
    }

    @Override
    public List<JournalBeanDBBean> getJournalBeans() {
        return journalBeans;
    }

    @Override
    public void deleteJournal(JournalBeanDBBean journalBeanDBBean) {
        JournalDBHelper.deleteJournal(realm,journalBeanDBBean);
        getLiveDataJournalBeans();
    }

    @Override
    public void onLiveDataCleared() {
        realm.close();
    }


}
