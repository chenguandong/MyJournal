package com.smart.journal.module.write.adapter

import android.app.Activity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.widget.EditText
import com.blankj.utilcode.util.KeyboardUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.customview.imageview.AspectRatioRoundedImageView
import com.smart.journal.module.write.bean.JournalBean

/**
 * @author guandongchen
 * @date 2018/1/18
 */
class WriteAdapter : BaseMultiItemQuickAdapter<JournalBean, BaseViewHolder> {
    enum class WriteAdapterModel {
        WriteAdapterModel_SHOW, WriteAdapterModel_EDIT
    }

    var writeAdapterModel: WriteAdapterModel? = null

    var isEditable:Boolean = false
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    constructor(data: MutableList<JournalBean>?, writeAdapterModel: WriteAdapterModel) : super(data) {
        this.writeAdapterModel = writeAdapterModel
    }


    override fun convert(helper: BaseViewHolder, item: JournalBean) {
        when (item!!.itemType) {
            JournalBean.WRITE_TAG_TEXT -> {
                val editText = helper!!.getView<EditText>(R.id.write_edit_text)
                if (data.indexOf(item) == 0) {
                    editText.hint = "请输入"
                } else {
                    editText.hint = "继续输入..."
                }
                editText.setText(if (TextUtils.isEmpty(item.content)) "" else item.content)
                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        item.content = editText.text.toString()
                    }

                    override fun afterTextChanged(s: Editable) {}
                })
                editText.isEnabled = isEditable
                editText.setSelection(editText.text.length)
                editText.requestFocus()
                KeyboardUtils.showSoftInput(context as Activity)

                editText.setOnKeyListener(object :OnKeyListener{

                    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                        if (p1== KeyEvent.KEYCODE_DEL&&editText.text.isNullOrEmpty()){
                            if (helper.adapterPosition>=1){
                                val preItem = data[helper.adapterPosition-1]
                                when(preItem.itemType){
                                    JournalBean.WRITE_TAG_IMAGE->{
                                        data.remove(preItem)
                                        notifyDataSetChanged()
                                        return true
                                    }
                                    JournalBean.WRITE_TAG_TEXT->{
                                        data.remove(item)
                                        notifyDataSetChanged()
                                        return true
                                    }
                                }
                            }
                        }
                        return false
                    }
                })



            }
            JournalBean.WRITE_TAG_IMAGE -> {
                val imageView = helper!!.getView<AspectRatioRoundedImageView>(R.id.write_imageview)
                Glide.with(context).load(item.imageURL).into(imageView)
            }
            else -> {
            }
        }
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    init {
        addItemType(JournalBean.WRITE_TAG_TEXT, R.layout.item_write_text)
        addItemType(JournalBean.WRITE_TAG_IMAGE, R.layout.item_write_image)
    }


}