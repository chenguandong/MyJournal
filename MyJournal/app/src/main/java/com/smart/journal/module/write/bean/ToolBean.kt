package com.smart.journal.module.write.bean

import androidx.annotation.StringDef
import com.smart.journal.module.write.bean.ToolBean.ToolBeanType.Companion.TOOL_IMAGE
import com.smart.journal.module.write.bean.ToolBean.ToolBeanType.Companion.TOOL_LOCATION
import java.io.Serializable


/**
 * @author guandongchen
 * @date 2018/1/18
 */
class ToolBean : Serializable {
    @Retention(AnnotationRetention.SOURCE)
    @StringDef(TOOL_IMAGE, TOOL_LOCATION)
    annotation class ToolBeanType {
        companion object {
            /**
             * 图片
             */
            const val TOOL_IMAGE = "Image"
            const val TOOL_LOCATION = "Location"
            const val TOOL_MORE = "More"
        }
    }

    var icon = 0
    var isSelected = false

    @ToolBeanType
    var itemType: String? = null

    constructor(icon: Int, selected: Boolean) {
        this.icon = icon
        isSelected = selected
    }

    constructor(icon: Int, @ToolBeanType itemType: String?) {
        this.icon = icon
        this.itemType = itemType
    }

    constructor(icon: Int) {
        this.icon = icon
    }

    constructor() {}

}