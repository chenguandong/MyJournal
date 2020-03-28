package com.smart.journal.module.tags.viewmodel

import androidx.lifecycle.*
import com.smart.journal.app.MyApp
import com.smart.journal.db.AppDatabase
import com.smart.journal.module.tags.bean.TagsDbBean
import com.smart.journal.module.tags.repository.TagsRepository
import com.smart.journal.module.tags.repository.TagsRepositoryImpl
import kotlinx.coroutines.launch

class TagViewModel : ViewModel() {
    private val tagsRepository:TagsRepository = TagsRepositoryImpl()
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
        }
        loadUsers()
    }

    fun queryTagByName(tagName:String){
        viewModelScope.launch {
            var tagsData:List<TagsDbBean> = tagsRepository.getTagsByName(tagName)
            if (tagsData.isNullOrEmpty()){
                tags.value = listOf(TagsDbBean("添加 "+tagName,0))
            }else{
                tags.value = tagsData
            }
        }
    }

     fun loadUsers() {
        viewModelScope.launch  {
            val data:List<TagsDbBean> = tagsRepository.getAllTags() // loadUser is a suspend function.
            tags.value = data
        }
    }
}
