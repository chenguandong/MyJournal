package com.smart.journal.db

import com.smart.journal.db.dao.JournalDao
import com.smart.journal.db.entity.JournalBeanDBBean

import androidx.room.Database
import androidx.room.RoomDatabase



/**
 * @author Administrator
 * @date 2019/9/11
 * @desc
 * @email chenguandong@outlook.com
 */
@Database(entities = arrayOf(JournalBeanDBBean::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mJournalDao(): JournalDao

}