package com.smart.journal.module.menu.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.module.menu.bean.SlideMenuBean
import com.smart.journal.module.menu.enums.ItemMenuType

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */
class SlideMenuAdapter(data: List<SlideMenuBean>) : BaseMultiItemQuickAdapter<SlideMenuBean?, BaseViewHolder?>(data) {

    init {
        addItemType(ItemMenuType.MENU_NOTE_BOOK, R.layout.item_menu_note_book)
    }

    override fun convert(helper: BaseViewHolder?, item: SlideMenuBean?) {
        when (helper!!.itemViewType){
            ItemMenuType.MENU_NOTE_BOOK->{
                helper.setText(R.id.noteBookNameTextView,item!!.noteBook!!.noteName)
            }
        }
    }
}