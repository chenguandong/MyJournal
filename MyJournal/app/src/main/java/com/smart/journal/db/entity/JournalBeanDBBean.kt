package com.smart.journal.db.entity

import android.os.Parcel
import android.os.Parcelable
import com.smart.journal.module.journal.bean.JournalItemBean

import java.util.Date

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


/**
 * @author guandongchen
 * @date 2018/1/22
 */

@Entity(tableName = "journal")
 class JournalBeanDBBean {
    @PrimaryKey(autoGenerate = true)
    var id: String? = null
    @ColumnInfo(name = "content")
    var content: String? = null
    @ColumnInfo(name = "weather")
    var weather: String? = null
    @ColumnInfo(name = "tags")
    var tags: String? = null
    @ColumnInfo(name = "date")
    var date: Long = 0
    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.toDouble()
    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.toDouble()
    @ColumnInfo(name = "address")
    var address: String? = null

    @Ignore
    var journalItemBean: JournalItemBean? = null

}
