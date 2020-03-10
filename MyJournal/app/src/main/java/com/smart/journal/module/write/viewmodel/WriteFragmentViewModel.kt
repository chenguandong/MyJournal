package com.smart.journal.module.write.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.module.write.bean.JournalBean

/**
 *
 * @author guandongchen
 * @date 2020/3/10
 */
class WriteFragmentViewModel : ViewModel() {

    private val location: MutableLiveData<MjPoiItem> by lazy {
        MutableLiveData<MjPoiItem>().also {

        }
    }
    private val journalData: MutableLiveData<List<JournalBean>> by lazy {
        MutableLiveData<List<JournalBean>>().also {
            loadUsers()
        }
    }

    fun getJounalData(): LiveData<List<JournalBean>> {
        return journalData
    }

    fun getLocation(): LiveData<MjPoiItem> {
        return location
    }


    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}