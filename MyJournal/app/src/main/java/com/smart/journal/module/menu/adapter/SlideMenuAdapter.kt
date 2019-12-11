package com.smart.journal.module.menu.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.module.menu.CreateNoteBookActivity
import com.smart.journal.module.menu.bean.SlideMenuBean
import com.smart.journal.module.menu.enums.ItemMenuType
import com.smart.journal.tools.ActionTools

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */
class SlideMenuAdapter(data: List<SlideMenuBean>) : BaseMultiItemQuickAdapter<SlideMenuBean?, BaseViewHolder?>(data) {


    init {
        addItemType(ItemMenuType.MENU_NOTE_BOOK, R.layout.item_menu_note_book)
        addItemType(ItemMenuType.MENU_NOTE_BOOK_ADD, R.layout.item_menu_note_book_add)
    }

    override fun convert(helper: BaseViewHolder?, item: SlideMenuBean?) {
        when (helper!!.itemViewType){
            //日记本Item
            ItemMenuType.MENU_NOTE_BOOK->{
                helper.setText(R.id.noteBookNameTextView,item!!.noteBook!!.name)
            }
            //添加按钮
            ItemMenuType.MENU_NOTE_BOOK_ADD->{

            }
        }
    }
}