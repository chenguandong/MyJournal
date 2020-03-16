package com.smart.journal.module.write.bean

import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.map.bean.MjPoiItem

/**
 *
 * @author guandongchen
 * @date 2020/3/12
 */
 class WriteSettingBean{
    /**
     * 地理位置
     */
    var location: MjPoiItem ?= null
    /**
     * 标签
     */
    var tags: List<String> ?=null
    /**
     * 日记本
     */
    var journalBook: NoteBookDBBean ?=null
    /**
     * 时间
     */
    var time: Long ?=null
}