package com.smart.journal.module.tags.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.journal.R
import com.smart.journal.module.tags.bean.TagsDbBean
import com.smart.journal.module.tags.repository.TagsRepository
import com.smart.journal.module.tags.repository.TagsRepositoryImpl
import kotlinx.coroutines.launch


class TagViewModel(application: Application) : AndroidViewModel(application) {
    private val tagsRepository: TagsRepository = TagsRepositoryImpl()
    private val tags: MutableLiveData<List<TagsDbBean>> = MutableLiveData()


    init {
        loadUsers()
    }

    fun getTags(): LiveData<List<TagsDbBean>> {
        return tags
    }

    fun inertTag(tagsDbBean: TagsDbBean){
        viewModelScope.launch {
            tagsRepository.insertOrUpdateTag(tagsDbBean)
            loadUsers()
        }

    }

    fun queryTagByName(tagName:String){
        viewModelScope.launch {
            var tagsData:List<TagsDbBean> = tagsRepository.getTagsByName(tagName)
            if (tagsData.isNullOrEmpty()){

                tags.value = listOf(TagsDbBean(getApplication<Application>().resources.getString(R.string.add)+tagName,0))
            }else{
                tags.value = tagsData
            }
        }
    }

    fun queryTagByNameNoAdd(tagName:String){
        viewModelScope.launch {
            var tagsData:List<TagsDbBean> = tagsRepository.getTagsByName(tagName)
            tags.value = tagsData
        }
    }

     fun loadUsers() {
        viewModelScope.launch  {
            val data:List<TagsDbBean> = tagsRepository.getAllTags() // loadUser is a suspend function.
            tags.value = data
        }
    }
}
