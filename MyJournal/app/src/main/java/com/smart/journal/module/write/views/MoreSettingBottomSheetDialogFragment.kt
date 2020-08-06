package com.smart.journal.module.write.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.KeyboardUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.customview.dialog.BaseBottomSheetDialogFragment
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.map.activity.AMapAdressSearchActivity
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.module.tags.activity.SearchActivity
import com.smart.journal.module.write.adapter.MoreSettingAdapter
import com.smart.journal.module.write.bean.MoreSettingBean
import com.smart.journal.module.write.bean.ToolBean
import com.smart.journal.module.write.bean.WriteSettingBean
import com.smart.journal.tools.eventbus.MessageEvent
import com.smart.journal.tools.eventbus.MessageEvent.NOTE_FAVOURITE_CHANGE
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

    private var writeSetting: WriteSettingBean? = null

    constructor() : super()

    constructor(writeSetting: WriteSettingBean) : super() {
        this.writeSetting = writeSetting
    }


    interface MoreSettingBottomSheetDialogFragmentDelegate {
        fun onItemClick(toolBean: ToolBean)
    }

    private var adapter: MoreSettingAdapter? = null
    private var itemData = ArrayList<MoreSettingBean>()

    private var isFavourite = false


    override fun setOpenState() {
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {

        when (event!!.tag) {
            //地理位置变化
            MessageEvent.NOTE_LOCATION_CHANGE -> {
                val poiItem: MjPoiItem = JSON.parseObject(event.message, MjPoiItem::class.java)
                poiItem?.let {
                    writeSetting!!.location = it
                }
                itemData[0].subTitle = writeSetting?.location?.snippet ?: ""
                adapter!!.notifyDataSetChanged()
            }
            MessageEvent.NOTE_TAG_CHANGE -> {
                var selectedTag: List<String> = Gson().fromJson(event.message, object : TypeToken<List<String>>() {}.type)
                selectedTag?.let { it ->
                    writeSetting!!.tags = it as List<String>
                    writeSetting?.tags?.let {
                        itemData[1].subTitle = it.toString().replace("[", "").replace("]", "")
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)

        KeyboardUtils.hideSoftInput(activity)

        val default = resources.getString(R.string.m_default)

        itemData.apply {
            add(MoreSettingBean(resources.getString(R.string.location), "", ContextCompat.getDrawable(requireContext(), R.drawable.ic_ws_location)))
            add(MoreSettingBean(resources.getString(R.string.tag), default, ContextCompat.getDrawable(requireContext(), R.drawable.ic_ws_tag)))
            add(MoreSettingBean(resources.getString(R.string.journal_note), default, ContextCompat.getDrawable(requireContext(), R.drawable.ic_ws_journal)))
            add(MoreSettingBean(resources.getString(R.string.date), "", ContextCompat.getDrawable(requireContext(), R.drawable.ic_ws_date)))
            add(MoreSettingBean(resources.getString(R.string.m_favourite), resources.getString(R.string.un_favourite), ContextCompat.getDrawable(requireContext(), R.drawable.ic_ws_favourite)))
        }


        itemData[0].subTitle = writeSetting?.location?.snippet ?: ""
        writeSetting?.tags?.let {
            itemData[1].subTitle = it.toString().replace("[", "").replace("]", "")

        }
        itemData[2].subTitle = writeSetting?.journalBook?.name ?: default
        itemData[3].subTitle = writeSetting!!.time?.let { Date(it).toLocaleString() }
        isFavourite = writeSetting!!.isFavourite
        if (isFavourite) {
            itemData[4].subTitle = resources.getString(R.string.favourite)
        } else {
            itemData[4].subTitle = resources.getString(R.string.un_favourite)
        }
        adapter = MoreSettingAdapter(itemData)
        recyclerView.adapter = adapter
        adapter!!.setOnItemClickListener { _, _, position ->
            writeSetting?.isEditable.let {

                if (it!!) {
                    when (position) {
                        //地址
                        0 -> {
                            val locationIntent = Intent(context, AMapAdressSearchActivity::class.java)
                            locationIntent.putExtra(AMapAdressSearchActivity.INTENT_TITLE, resources.getString(R.string.location))
                            startActivity(locationIntent)
                        }
                        //标签
                        1 -> {
                            startActivityForResult(Intent(context, SearchActivity::class.java), 100)
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
                            AlertDialog.Builder(requireActivity())
                                    .setTitle(resources.getString(R.string.choose_default_note))
                                    .setSingleChoiceItems(items, choosedItem) { _, which ->
                                        writeSetting!!.journalBook = allDbNoteList[which]
                                    }
                                    .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                                        // do sth
                                        itemData[2].subTitle = writeSetting?.journalBook?.name ?: ""
                                        adapter!!.notifyDataSetChanged()
                                    }
                                    .setCancelable(false)
                                    .create().show()
                        }
                        //时间
                        3 -> {



                            //获取日历的一个实例，里面包含了当前的年月日
                            //获取日历的一个实例，里面包含了当前的年月日
                            val calendar = Calendar.getInstance()
                            writeSetting!!.time?.let {
                                calendar.time = Date(it)
                            }
                            //构建一个日期对话框，该对话框已经集成了日期选择器
                            //DatePickerDialog的第二个构造参数指定了日期监听器
                            //构建一个日期对话框，该对话框已经集成了日期选择器
                            //DatePickerDialog的第二个构造参数指定了日期监听器
                            val dialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                                    Logger.d(year)
                                    Logger.d(month)
                                    Logger.d(day)
                                    calendar.set(year,month,day)
                                     Logger.d(calendar.time.toLocaleString())
                                writeSetting!!.time = calendar.time.time
                                itemData[3].subTitle = calendar.time.toLocaleString()
                                adapter!!.notifyDataSetChanged()
                                
                            },
                                    calendar[Calendar.YEAR],
                                    calendar[Calendar.MONTH],
                                    calendar[Calendar.DAY_OF_MONTH])
                            //把日期对话框显示在界面上
                            //把日期对话框显示在界面上
                            dialog.show()
                        }
                        //收藏
                        4 -> {
                            isFavourite = !isFavourite
                            if (isFavourite) {
                                itemData[4].subTitle = resources.getString(R.string.favourite)
                            } else {
                                itemData[4].subTitle = resources.getString(R.string.un_favourite)
                            }
                            adapter!!.notifyDataSetChanged()
                            EventBus.getDefault().post(MessageEvent("0", NOTE_FAVOURITE_CHANGE))

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