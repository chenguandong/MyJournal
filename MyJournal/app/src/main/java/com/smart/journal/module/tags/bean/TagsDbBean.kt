package com.smart.journal.module.tags.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *
 * @author guandongchen
 * @date 2020/3/24
 */
@Entity(tableName = "tags")
class TagsDbBean : Serializable {
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "color")
    var color: Int? = null


    @Ignore
    constructor(name: String?, color: Int?) {
        this.name = name
        this.color = color
    }

    constructor()
}