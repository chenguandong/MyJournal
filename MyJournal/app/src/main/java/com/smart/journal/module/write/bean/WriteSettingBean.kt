package com.smart.journal.module.write.bean

import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.map.bean.MjPoiItem
import java.io.Serializable

/**
 *
 * @author guandongchen
 * @date 2020/3/12
 */
class WriteSettingBean : Serializable {
    var journalId: Int? = null
    /**
     * 地理位置
     */
    var location: MjPoiItem? = null
    /**
     * 标签
     */
    var tags: List<String>? = null
    /**
     * 日记本
     */
    var journalBook: NoteBookDBBean? = null
    /**
     * 时间
     */
    var time: Long? = null

    /**
     * 是否允许编辑
     */
    var isEditable: Boolean = false

    /**
     * 是否收藏
     */
    var isFavourite:Boolean = false


}