package com.smart.journal.module.journal
import androidx.annotation.StringDef
import com.smart.journal.module.journal.SearchEableType.Companion.ALL
import com.smart.journal.module.journal.SearchEableType.Companion.FAVOURITE
import com.smart.journal.module.journal.SearchEableType.Companion.LOCATION
import com.smart.journal.module.journal.SearchEableType.Companion.TAG

/**
 *
 * @author guandongchen
 * @date 2020/4/27
 */
interface SearchEable {
    fun doSerarch (serarchKey:String, @SearchEableType searchType:String)
}

@Retention(AnnotationRetention.SOURCE)
@StringDef(ALL, TAG,LOCATION,FAVOURITE)
annotation class SearchEableType {
    companion object {
        /**
         * 图片
         */
        const val ALL = "all"
        const val TAG = "tag"
        const val LOCATION = "location"
        const val FAVOURITE = "favourite"
    }
}