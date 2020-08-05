package com.smart.journal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smart.journal.db.dao.JournalDao
import com.smart.journal.db.dao.NoteBookDao
import com.smart.journal.db.dao.TagsDao
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.tags.bean.TagsDbBean


/**
 * @author Administrator
 * @date 2019/9/11
 * @desc
 * @email chenguandong@outlook.com
 */
@Database(entities = arrayOf(JournalBeanDBBean::class, NoteBookDBBean::class, TagsDbBean::class), version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mJournalDao(): JournalDao
    abstract fun mNoteBookDao(): NoteBookDao
    abstract fun mTagsDao(): TagsDao
}