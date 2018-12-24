package com.smart.journal.module.journal.bean

import android.graphics.Bitmap

import java.io.Serializable

/**
 * @author guandongchen
 * @date 2018/1/23
 */

class JournalItemBean : Serializable {
    var bitmap: Bitmap? = null
    var content: String? = null
    var date: String? = null
}
