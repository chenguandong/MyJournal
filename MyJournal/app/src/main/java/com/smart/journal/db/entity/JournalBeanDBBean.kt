package com.smart.journal.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.smart.journal.module.journal.bean.JournalItemBean


/**
 * @author guandongchen
 * @date 2018/1/22
 */

@Entity(tableName = "journal")
class JournalBeanDBBean {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "content")
    var content: String? = null

    @ColumnInfo(name = "weather")
    var weather: String? = null

    @ColumnInfo(name = "tags")
    var tags: String? = null

    @ColumnInfo(name = "date")
    var date: Long = 0

    /**
     * 那年今日使用
     */
    @ColumnInfo(name = "this_date")
    var thisDate: String? = null

    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.toDouble()

    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.toDouble()

    @ColumnInfo(name = "address")
    var address: String? = null

    /**
     * 所属日记本
     */
    @ColumnInfo(name = "book_name")
    var bookName: String? = null

    @ColumnInfo(name = "favourite", index = false)
    var favourite: Boolean? = false

    @Ignore
    var journalItemBean: JournalItemBean? = null

}
