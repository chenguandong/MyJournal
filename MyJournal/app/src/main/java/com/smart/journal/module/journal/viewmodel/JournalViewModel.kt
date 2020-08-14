package com.smart.journal.module.journal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.FileUtils
import com.smart.journal.contants.Contancts
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.journal.repository.JournalRepository
import com.smart.journal.module.journal.repository.JournalRepositoryImpl
import java.util.logging.Logger


/**
 * @author guandongchen
 * @date 26/03/2018
 */

class JournalViewModel : ViewModel() {

    private val journalRepository: JournalRepository

    init {

        journalRepository = JournalRepositoryImpl()

    }

    fun deleteJournal(journalBeanDBBean: JournalBeanDBBean) {
        journalRepository.deleteJournal(journalBeanDBBean)
        if (journalBeanDBBean.content != null) {

            val contents = journalBeanDBBean.content!!.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (content in contents) {
                //查看内容里是不是包含图片
                if (content.startsWith(Contancts.FILE_TYPE_IMAGE)) {
                    //删除本地图片
                    var imagePath = content.replace(Contancts.FILE_TYPE_IMAGE,"")
                    com.orhanobut.logger.Logger.d(imagePath)
                    FileUtils.delete(imagePath!!)
                }

            }
        }
    }


    fun searchJournalByKeyWord(keyWord: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchJournalByKeyWord(keyWord)
    }

    fun searchJournalByTag(tagName: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchJournalByTag(tagName)
    }

    fun searchJournalByFavourite(tagName: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchJournalByTag(tagName)
    }

    fun searchJournalByLocationName(locationName: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchJournalByLocationName(locationName)
    }


    fun getJournalBeans(): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.getJournalBeans()
    }

    fun searchByNoteBookName(noteBookName: String): LiveData<List<JournalBeanDBBean>> {
        return journalRepository.searchByNoteBookName(noteBookName)
    }

    public override fun onCleared() {
        journalRepository.onLiveDataCleared()
        super.onCleared()
    }
}
