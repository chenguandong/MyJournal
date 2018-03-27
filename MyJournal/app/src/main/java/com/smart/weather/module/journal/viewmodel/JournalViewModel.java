package com.smart.weather.module.journal.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smart.weather.module.journal.repository.JournalRepository;
import com.smart.weather.module.journal.repository.JournalRepositoryImpl;
import com.smart.weather.module.write.bean.JournalBeanDBBean;

import java.util.List;

import io.realm.RealmResults;

/**
 * @author guandongchen
 * @date 26/03/2018
 */

public class JournalViewModel extends ViewModel implements JournalRepository{

    private JournalRepository journalRepository;

    private MutableLiveData<RealmResults<JournalBeanDBBean>> listMutableLiveData;


    public JournalViewModel() {

        journalRepository = new JournalRepositoryImpl();

        if (this.listMutableLiveData == null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            listMutableLiveData = new MutableLiveData<>();
        }
        listMutableLiveData = journalRepository.getLiveDataJournalBeans();

    }


    @Override
    public MutableLiveData<RealmResults<JournalBeanDBBean>> getLiveDataJournalBeans() {
        return journalRepository.getLiveDataJournalBeans();
    }

    @Override
    public List<JournalBeanDBBean> getJournalBeans() {
        return journalRepository.getJournalBeans();
    }

    @Override
    public void deleteJournal(JournalBeanDBBean journalBeanDBBean) {
        journalRepository.deleteJournal(journalBeanDBBean);
    }
}
