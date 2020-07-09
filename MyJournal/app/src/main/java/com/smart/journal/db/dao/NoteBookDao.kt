package com.smart.journal.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart.journal.db.entity.NoteBookDBBean

/**
 * @author chenguandong
 * @date 2019/12/11
 * @desc
 * @email chenguandong@outlook.com
 */
@Dao
abstract class NoteBookDao {
    @Query("SELECT * FROM notebook")
    abstract fun allNoteBook(): List<NoteBookDBBean?>?

    @Query("SELECT * FROM notebook where id = :bookId")
    abstract fun getNoteBookById(bookId:Int): List<NoteBookDBBean>

    @Query("SELECT * FROM notebook where id = :bookName")
    abstract fun getNoteBookByName(bookName:String): List<NoteBookDBBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNoteBook(vararg noteBookDBBean: NoteBookDBBean?)

    @Query("SELECT EXISTS(SELECT * FROM notebook WHERE name LIKE :noteBookName LIMIT 1)")
    abstract fun checkExists(noteBookName: String?): Boolean
}