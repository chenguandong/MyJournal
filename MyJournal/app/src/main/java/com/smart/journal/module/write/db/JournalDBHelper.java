package com.smart.journal.module.write.db;

import android.text.TextUtils;

import com.smart.journal.contants.Contancts;
import com.smart.journal.db.AppDatabase;
import com.smart.journal.db.dao.JournalDao;
import com.smart.journal.module.write.bean.JournalBean;
import com.smart.journal.module.write.bean.JournalBeanDBBean;
import com.smart.journal.tools.location.LocationTools;
import com.smart.journal.tools.location.bean.LocationBean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author guandongchen
 * @date 2018/1/22
 */

public class JournalDBHelper {

    /**
     * 保存日记
     * @param writeSectionBeans
     */
    public static void saveJournal(List<JournalBean> writeSectionBeans ){
        JournalBeanDBBean journalBeanDBBean = new JournalBeanDBBean();
        StringBuilder contentSb = new StringBuilder();
        for (JournalBean journalBean:
            writeSectionBeans) {

            if (journalBean.getItemType()==JournalBean.WRITE_TAG_IMAGE){

                contentSb.append(Contancts.FILE_TYPE_IMAGE+journalBean.getImageURL()+Contancts.FILE_TYPE_SPLIT);
            }else{
                if (!TextUtils.isEmpty(journalBean.getContent().trim())){
                    contentSb.append(Contancts.FILE_TYPE_TEXT+journalBean.getContent()+Contancts.FILE_TYPE_SPLIT);
                }

            }
        }
        journalBeanDBBean.setContent(contentSb.toString());
        journalBeanDBBean.setDate(System.currentTimeMillis());
        LocationBean locationBean =  LocationTools.getLocationBean();
        if (!TextUtils.isEmpty(locationBean.getAdress())){
            journalBeanDBBean.setAddress(locationBean.getAdress());
            journalBeanDBBean.setLatitude(locationBean.getLatitude());
            journalBeanDBBean.setLongitude(locationBean.getLongitude());
        }
        journalBeanDBBean.setTags("默认");
        AppDatabase.Companion.getInstance().mJournalDao().saveJournal(journalBeanDBBean);
    }


    /**
     * 获取所有日记
     * @return
     */
    public static List<JournalBeanDBBean> getAllJournals(JournalBeanDBBean journalBeanDBBean){
        return AppDatabase.Companion.getInstance().mJournalDao().getAllJournal();
    }

    /**
     * 删除日记
     * @param realm
     * @param journalBeanDBBean
     */
    public static void deleteJournal(Realm realm,JournalBeanDBBean journalBeanDBBean){

        realm.executeTransaction(realm1 -> journalBeanDBBean.deleteFromRealm());
    }
}
