package com.smart.journal.module.write.db

import com.smart.journal.app.MyApp
import com.smart.journal.db.dao.NoteBookDao
import com.smart.journal.db.entity.NoteBookDBBean

/**
 *
 * @author guandongchen
 * @date 2020/3/23
 */
object NoteBookDBHelper {
    var noteBookDao: NoteBookDao? = null

    init {
        noteBookDao = MyApp.database!!.mNoteBookDao()
    }

    /**
     * 通过ID 查询单条日记本内容
     */
    fun queryNoteBookById(bookId: Int): List<NoteBookDBBean> {
        return noteBookDao!!.getNoteBookById(bookId)
    }

    /**
     * 通过ID 查询单条日记本内容
     */
    fun queryNoteBookByName(bookName: String): List<NoteBookDBBean> {
        return noteBookDao!!.getNoteBookByName(bookName)
    }
}