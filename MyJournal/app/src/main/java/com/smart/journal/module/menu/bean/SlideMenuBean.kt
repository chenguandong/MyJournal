package com.smart.journal.module.menu.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.smart.journal.module.menu.enums.ItemMenuType

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */

class SlideMenuBean : MultiItemEntity {
    //(var title: String, var subTitle: String, @field:ItemType4CBBDetail var itemType: Int)
    var noteBook: NoteBookBean? = null

    @ItemMenuType
    private var itemType: Int = 0

    constructor(noteBook: NoteBookBean?, @ItemMenuType itemType: Int) {
        this.noteBook = noteBook
        this.itemType = itemType;
    }

    override fun getItemType(): Int {
        return itemType;
    }


}