package com.smart.journal.db.dao

import androidx.room.*
import com.smart.journal.db.entity.JournalBeanDBBean

/**
 * @author Administrator
 * @date 2019/9/11
 * @desc
 * @email chenguandong@outlook.com
 *
 * 支持boole 注解  取值的时候用 1, 0 代表true  false
 */
@Dao
abstract class JournalDao {
    /*  @Query("SELECT count(*)as b1,SUM(age) as b2,MAX(score) sentTime,* FROM user")
   public abstract List<BaseUser> getAll();*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveJournal(journalBeanDBBeans: JournalBeanDBBean?)

    @get:Query("SELECT * FROM journal ORDER BY date DESC")
    abstract val allJournal: List<JournalBeanDBBean?>?

    @Delete
    abstract fun deleteJournal(journalBeanDBBean: JournalBeanDBBean?)

    @Query("DELETE FROM journal WHERE id = :journalId")
    abstract fun deleteJournalById(journalId:Int)

    /* @Delete



  public abstract void delete(User user);

  @Update
  public abstract void update(User user);

  @Query("UPDATE user SET first_name = :name WHERE first_name = 9")
  public abstract void update(String name);

  @Query("SELECT EXISTS(SELECT * FROM user WHERE first_name LIKE :name LIMIT 1)")
  public abstract boolean checkExists(String name);

  @Query("SELECT * FROM user")
  public abstract List<User> getAll();

  @Query("SELECT * FROM user WHERE score in ('1','3')")
  public abstract List<User> getAllIn();

  @Query("SELECT * FROM User WHERE uid IN (:userIds)")
  public abstract List<User> loadAllByIds(int[] userIds);

  @Query("SELECT * FROM User WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
  public abstract User findByName(String first, String last);*/
}