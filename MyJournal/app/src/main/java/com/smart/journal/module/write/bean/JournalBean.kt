package com.smart.journal.module.write.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * @author guandongchen
 * @date 2018/1/18
 */
class JournalBean : Serializable, MultiItemEntity {
     var itemType:Int ?= 0
     var content: String? = null
     var imageURL: String? = null

    constructor(content: String?, imageURL: String?) {
        this.content = content
        this.imageURL = imageURL
        if (!TextUtils.isEmpty(content)) {
            setItemType(WRITE_TAG_TEXT)
        }
        if (!TextUtils.isEmpty(imageURL)) {
            setItemType(WRITE_TAG_IMAGE)
        }
    }

    constructor(content: String?) {
        this.content = content
        setItemType(WRITE_TAG_TEXT)
    }

    constructor() {}

    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    override fun getItemType(): Int {
        return itemType!!
    }

    companion object {
        const val WRITE_TAG_TEXT = 0
        const val WRITE_TAG_IMAGE = 1
    }
}