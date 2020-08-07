package com.smart.journal.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */
@Entity(tableName = "notebook")
class NoteBookDBBean constructor() : Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "num", defaultValue = "0")
    var num: Int? = null

    @ColumnInfo(name = "create_time")
    var createTime: Long? = null

    @ColumnInfo(name = "color")
    var color:Int? = null

    @Ignore
    constructor(name: String?,color:Int?) : this() {
        this.name = name
        this.color = color
        this.createTime = System.currentTimeMillis()
    }

    @Ignore
    constructor(name: String?) : this() {
        this.name = name
        this.createTime = System.currentTimeMillis()
    }

}