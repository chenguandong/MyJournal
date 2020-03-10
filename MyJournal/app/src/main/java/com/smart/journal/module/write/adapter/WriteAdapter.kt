package com.smart.journal.module.write.adapter

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.journal.R
import com.smart.journal.customview.imageview.AspectRatioRoundedImageView
import com.smart.journal.module.write.bean.JournalBean

/**
 * @author guandongchen
 * @date 2018/1/18
 */
class WriteAdapter(data: List<JournalBean?>?, private val writeAdapterModel: WriteAdapterModel) : BaseMultiItemQuickAdapter<JournalBean?, BaseViewHolder?>(data) {
    enum class WriteAdapterModel {
        WriteAdapterModel_SHOW, WriteAdapterModel_EDIT
    }

    override fun convert(helper: BaseViewHolder?, item: JournalBean?) {
        when (item!!.getItemType()) {
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
                if (writeAdapterModel == WriteAdapterModel.WriteAdapterModel_SHOW) {
                    editText.isEnabled = false
                }
            }
            JournalBean.WRITE_TAG_IMAGE -> {
                val imageView = helper!!.getView<AspectRatioRoundedImageView>(R.id.write_imageview)
                Glide.with(mContext).load(item.imageURL).into(imageView)
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