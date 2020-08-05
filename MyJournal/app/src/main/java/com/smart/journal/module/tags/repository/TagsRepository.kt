package com.smart.journal.module.tags.repository

import com.smart.journal.module.tags.bean.TagsDbBean

/**
 *
 * @author guandongchen
 * @date 2020/3/24
 */
interface TagsRepository {
    fun getAllTags(): List<TagsDbBean>
    fun getTagsByName(tagName: String): List<TagsDbBean>
    fun insertOrUpdateTag(tagsDbBean: TagsDbBean)
}