package com.smart.journal.db

import com.smart.journal.app.MyApp
import com.smart.journal.db.dao.JournalDao
import com.smart.journal.module.write.bean.JournalBeanDBBean

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @author Administrator
 * @date 2019/9/11
 * @desc
 * @email chenguandong@outlook.com
 */
@Database(entities = [JournalBeanDBBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mJournalDao(): JournalDao

    companion object {
        private var database: AppDatabase? = null


        val instance: AppDatabase?
            get() {
                if (database == null) {
                    synchronized(AppDatabase::class.java) {
                        if (database == null) {
                            database = Room.databaseBuilder(MyApp.instance!!, AppDatabase::class.java, "database-name")
                                    .allowMainThreadQueries()
                                    //.addMigrations(MIGRATION_2_3)
                                    .build()
                        }
                    }
                }
                return database
            }
    }
    /*  //public abstract BookDao bookDao();
  */
    /**
     * 数据库版本 1->2 user表格新增了age列
     *//*
  static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    @Override
    public void migrate(SupportSQLiteDatabase database) {
      database.execSQL("ALTER TABLE user " + " ADD COLUMN age INTEGER");
    }
  };
  */
    /**
     * 数据库版本 2->3 新增book表格
     *//*
  static final Migration MIGRATION_2_3 = new Migration(2, 3) {
    @Override
    public void migrate(SupportSQLiteDatabase database) {
      database.execSQL(
          "CREATE TABLE IF NOT EXISTS `book` (`uid` INTEGER PRIMARY KEY autoincrement, `name` TEXT , `user_id` INTEGER, 'time' "
              + "INTEGER)");
    }
  };*/
}