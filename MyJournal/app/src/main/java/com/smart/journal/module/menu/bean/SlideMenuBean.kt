package com.smart.journal.module.menu.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.menu.enums.ItemMenuType

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */

class SlideMenuBean(override val itemType: Int) : MultiItemEntity {
    //(var title: String, var subTitle: String, @field:ItemType4CBBDetail var itemType: Int)
    var noteBook: NoteBookDBBean? = null

    constructor(noteBook: NoteBookDBBean?, @ItemMenuType itemType: Int) : this(itemType) {
        this.noteBook = noteBook
    }

}