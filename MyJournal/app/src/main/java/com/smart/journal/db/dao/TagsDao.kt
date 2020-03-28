package com.smart.journal.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.smart.journal.module.tags.bean.TagsDbBean

/**
 *
 * @author guandongchen
 * @date 2020/3/24
 */
@Dao
abstract class TagsDao {
    @Query("SELECT * FROM tags")
    abstract fun getAllTags(): List<TagsDbBean>

    @Query("SELECT * FROM tags where name like '%'|| :tagName ||'%' ")
    abstract fun getTagsByName(tagName:String):List<TagsDbBean>

    @Insert(onConflict = REPLACE)
    abstract fun insertOrUpdateTag(tag:TagsDbBean)
}