package com.smart.journal.module.write.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * @author guandongchen
 * @date 2018/1/18
 */
class JournalBean(override var itemType: Int) : Serializable, MultiItemEntity {
    var content: String? = null
    var imageURL: String? = null


    constructor(content: String, imageURL: String) : this(itemType=WRITE_TAG_TEXT) {
        this.content = content
        this.imageURL = imageURL
        if (!TextUtils.isEmpty(content)) {
            itemType= WRITE_TAG_TEXT
        }
        if (!TextUtils.isEmpty(imageURL)) {
            itemType = WRITE_TAG_IMAGE
        }
    }

    constructor(content: String) : this(itemType=WRITE_TAG_TEXT) {
        this.content = content
    }

    companion object {
        const val WRITE_TAG_TEXT = 0
        const val WRITE_TAG_IMAGE = 1
    }
}