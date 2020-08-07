package com.smart.journal.module.menu.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.module.menu.bean.SlideMenuBean
import com.smart.journal.module.menu.enums.ItemMenuType

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */
class SlideMenuAdapter : BaseMultiItemQuickAdapter<SlideMenuBean, BaseViewHolder> {


    constructor(itemData: ArrayList<SlideMenuBean>) : super(data = itemData)

    init {
        addItemType(ItemMenuType.MENU_NOTE_BOOK, R.layout.item_menu_note_book)
        addItemType(ItemMenuType.MENU_NOTE_BOOK_ADD, R.layout.item_menu_note_book_add)
    }

    override fun convert(helper: BaseViewHolder, item: SlideMenuBean) {
        when (helper!!.itemViewType) {
            //日记本Item
            ItemMenuType.MENU_NOTE_BOOK -> {
                helper.setText(R.id.noteBookNameTextView, item!!.noteBook!!.name)
                item.noteBook!!.color?.let { helper.setTextColor(R.id.noteBookNameTextView, it) }
            }
            //添加按钮
            ItemMenuType.MENU_NOTE_BOOK_ADD -> {

            }
        }
    }
}