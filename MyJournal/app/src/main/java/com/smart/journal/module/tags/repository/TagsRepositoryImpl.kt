package com.smart.journal.module.tags.repository

import androidx.lifecycle.LiveData
import com.smart.journal.module.tags.TagsDbHelpr
import com.smart.journal.module.tags.bean.TagsDbBean

/**
 *
 * @author guandongchen
 * @date 2020/3/24
 */
class TagsRepositoryImpl : TagsRepository {
    override fun getAllTags(): List<TagsDbBean> {
        return TagsDbHelpr.getAllTags()
    }

    override fun getTagsByName(tagName: String): List<TagsDbBean>{
        return TagsDbHelpr.getTagsByName(tagName)
    }

    override fun insertOrUpdateTag(tagsDbBean: TagsDbBean) {
        return TagsDbHelpr.insertOrUpdateTag(tagsDbBean)
    }


}