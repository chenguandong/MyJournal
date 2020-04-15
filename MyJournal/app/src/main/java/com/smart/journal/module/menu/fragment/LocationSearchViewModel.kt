package com.smart.journal.module.menu.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smart.journal.app.MyApp
import com.smart.journal.db.dao.JournalDao
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.write.db.JournalDBHelper

class LocationSearchViewModel : ViewModel() {
     val journalData: LiveData<List<JournalBeanDBBean>> = JournalDBHelper.allJournals()

     fun searchLocationByLocationName(adress:String): LiveData<List<JournalBeanDBBean>>{
          return JournalDBHelper.searchJournalByLocationName(adress)
     }
}
