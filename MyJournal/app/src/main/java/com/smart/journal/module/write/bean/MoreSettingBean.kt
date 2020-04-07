package com.smart.journal.module.write.bean

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import java.io.Serializable

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
class MoreSettingBean :Serializable{
    var title:String ? = null
    var subTitle:String ? = null
    var logoImage: Drawable? = null

    constructor(title: String?, subTitle: String?,  logoImage: Drawable?) {
        this.title = title
        this.subTitle = subTitle
        this.logoImage = logoImage
    }
}