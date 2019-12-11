package com.smart.journal.db.dao;

import com.smart.journal.db.entity.NoteBookDBBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author chenguandong
 * @date 2019/12/11
 * @desc
 * @email chenguandong@outlook.com
 */
@Dao
public abstract class NoteBookDao {

    @Query("SELECT * FROM notebook")
    public abstract List<NoteBookDBBean> getAllNoteBook();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveNoteBook(NoteBookDBBean ...noteBookDBBean);

    @Query("SELECT EXISTS(SELECT * FROM notebook WHERE name LIKE :noteBookName LIMIT 1)")
    public abstract boolean checkExists(String noteBookName);
}
