package com.smart.weather.module.write.db;

import android.text.TextUtils;

import com.smart.weather.module.write.bean.JournalBean;
import com.smart.weather.module.write.bean.JournalBeanDBBean;
import com.smart.weather.module.write.bean.JournalLocationDBBean;
import com.smart.weather.tools.location.LocationTools;
import com.smart.weather.tools.location.bean.LocationBean;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author guandongchen
 * @date 2018/1/22
 */

public class DBHelper {

    public static void saveJournal(Realm realm,List<JournalBean> writeSectionBeans ){
        realm.executeTransaction(realm1 -> {
            JournalBeanDBBean journalBeanDBBean = realm1.createObject(JournalBeanDBBean.class);

            StringBuilder contentSb = new StringBuilder();
            for (JournalBean journalBean:
                    writeSectionBeans) {

                if (journalBean.getItemType()==JournalBean.WRITE_TAG_IMAGE){
                    contentSb.append("image://"+journalBean.getImageBase64()+"~~~");
                }else{
                    contentSb.append("text://"+journalBean.getContent()+"~~~");
                }
            }
            journalBeanDBBean.setContent(contentSb.toString());
            journalBeanDBBean.setDate(new Date());
            LocationBean locationBean =  LocationTools.getLocationBean();
            if (!TextUtils.isEmpty(locationBean.getAdress())){
                JournalLocationDBBean locationDBBean = realm1.createObject(JournalLocationDBBean.class);
                locationDBBean.setAdress(locationBean.getAdress());
                locationDBBean.setLatitude(locationBean.getLatitude());
                locationDBBean.setLongitude(locationBean.getLongitude());
                journalBeanDBBean.setLocation(locationDBBean);
            }
            journalBeanDBBean.setTags("默认");

        });
    }



    public static RealmResults<JournalBeanDBBean> getAllJournals(Realm realm){
        return realm.where(JournalBeanDBBean.class).findAll();
    }
}
