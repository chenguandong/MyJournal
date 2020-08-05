package com.smart.journal.module.tags

import com.smart.journal.app.MyApp
import com.smart.journal.db.dao.TagsDao
import com.smart.journal.module.tags.bean.TagsDbBean

/**
 *
 * @author guandongchen
 * @date 2020/3/24
 */
object TagsDbHelpr {
    var tagsDao: TagsDao? = MyApp.database!!.mTagsDao()

    fun getAllTags(): List<TagsDbBean> {
        return tagsDao!!.getAllTags()
    }

    fun getTagsByName(tagName: String): List<TagsDbBean> {
        return tagsDao!!.getTagsByName(tagName)
    }

    fun insertOrUpdateTag(tagsDbBean: TagsDbBean) {
        tagsDao!!.insertOrUpdateTag(tagsDbBean)
    }
}