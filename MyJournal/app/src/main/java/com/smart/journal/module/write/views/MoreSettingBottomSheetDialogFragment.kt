package com.smart.journal.module.write.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.alibaba.fastjson.JSON
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.customview.dialog.BaseBottomSheetDialogFragment
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.map.activity.AMapAdressSearchActivity
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.module.tags.activity.TagActivity
import com.smart.journal.module.tags.bean.TagsDbBean
import com.smart.journal.module.write.adapter.MoreSettingAdapter
import com.smart.journal.module.write.bean.MoreSettingBean
import com.smart.journal.module.write.bean.ToolBean
import com.smart.journal.module.write.bean.WriteSettingBean
import com.smart.journal.tools.eventbus.MessageEvent
import kotlinx.android.synthetic.main.view_dialog_preview_bottom_sheet.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
class MoreSettingBottomSheetDialogFragment : BaseBottomSheetDialogFragment {

    val delegate: MoreSettingBottomSheetDialogFragmentDelegate? = null

    var writeSetting: WriteSettingBean? = null

    constructor() : super()

    constructor(writeSettingBean: WriteSettingBean?) : super() {
        this.writeSetting = writeSettingBean
    }


    interface MoreSettingBottomSheetDialogFragmentDelegate {
        fun onItemClick(toolBean: ToolBean)
    }

    private var adapter: MoreSettingAdapter? = null
    private var itemData = object : ArrayList<MoreSettingBean>() {
        init {
            add(MoreSettingBean("位置", "", R.drawable.ic_ws_location))
            add(MoreSettingBean("标签", "默认", R.drawable.ic_ws_tag))
            add(MoreSettingBean("日记本", "默认", R.drawable.ic_ws_journal))
            add(MoreSettingBean("时间", "", R.drawable.ic_ws_date))
        }
    }


    override fun setOpenState() {
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {

        when(event!!.tag){
            //地理位置变化
            MessageEvent.NOTE_LOCATION_CHANGE->{
                val poiItem: MjPoiItem = JSON.parseObject(event.message, MjPoiItem::class.java)
                poiItem?.let {
                    writeSetting!!.location = it
                }
                itemData[0].subTitle = writeSetting?.location?.snippet ?: ""
                adapter!!.notifyDataSetChanged()
            }
            MessageEvent.NOTE_TAG_CHANGE->{
                var selectedTag:List<String> = Gson().fromJson(event.message, object : TypeToken<List<String>>() {}.type)
                selectedTag?.let { it ->
                    writeSetting!!.tags = it as List<String>
                    writeSetting?.tags?.let {
                        itemData[1].subTitle = it.toString().replace("[","").replace("]","")
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        itemData[0].subTitle = writeSetting?.location?.snippet ?: ""
        writeSetting?.tags?.let {
            itemData[1].subTitle = it.toString()
        }
        itemData[2].subTitle = writeSetting?.journalBook?.name ?: "默认"
        itemData[3].subTitle = writeSetting!!.time?.let { Date(it).toLocaleString() }
        adapter = MoreSettingAdapter(itemData)
        recyclerView.adapter = adapter
        adapter!!.setOnItemClickListener { _, _, position ->
            writeSetting?.isEditable.let {

                if (it!!){
                    when (position) {
                        //地址
                        0 -> {
                            val locationIntent = Intent(context, AMapAdressSearchActivity::class.java)
                            locationIntent.putExtra(AMapAdressSearchActivity.INTENT_TITLE, "位置")
                            startActivity(locationIntent)
                        }
                        //标签
                        1 -> {
                            startActivityForResult(Intent(context, TagActivity::class.java),100)
                        }
                        /* 日记本 */
                        2 -> {

                            var allDbNoteList: List<NoteBookDBBean> = MyApp.database!!.mNoteBookDao().allNoteBook() as List<NoteBookDBBean>
                            val items: Array<String?> = arrayOfNulls(allDbNoteList.size)

                            var choosedItem: Int = 0
                            for (i in allDbNoteList.indices) {
                                items[i] = allDbNoteList[i]!!.name.toString()
                                if (writeSetting!!.journalBook != null) {
                                    if (writeSetting!!.journalBook!!.name!! == allDbNoteList[i]!!.name) {
                                        choosedItem = i
                                    }
                                }
                            }
                            AlertDialog.Builder(activity!!)
                                    .setTitle("请选择日记本")
                                    .setSingleChoiceItems(items, choosedItem) { _, which ->
                                        writeSetting!!.journalBook = allDbNoteList[which]
                                    }
                                    .setPositiveButton("OK") { _, _ ->
                                        // do sth
                                        itemData[2].subTitle = writeSetting?.journalBook?.name ?: ""
                                        adapter!!.notifyDataSetChanged()
                                    }
                                    .setCancelable(false)
                                    .create().show()
                        }
                        //时间
                        3 -> {
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}