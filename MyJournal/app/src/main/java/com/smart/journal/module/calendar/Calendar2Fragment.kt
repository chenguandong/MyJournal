package com.smart.journal.module.calendar

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.contants.Contancts
import com.smart.journal.module.journal.bean.JournalItemBean
import com.smart.journal.module.journal.manager.JournalManager
import com.smart.journal.module.write.db.JournalDBHelper
import com.smart.journal.tools.DateTools
import com.smart.journal.tools.StringTools
import com.smart.journal.tools.color.ColorTools
import com.smart.journal.tools.eventbus.MessageEvent
import kotlinx.android.synthetic.main.calendar2_fragment.*
import org.greenrobot.eventbus.EventBus
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.*

class Calendar2Fragment : BaseFragment() {
    override fun getData() {
        init()
    }

    val indicators = mutableListOf<CalendarView.DateIndicator>()

    override fun initView() {
    }

    override fun initData() {
        indicators.clear()
        JournalDBHelper.allJournals().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            for (dataBean in it) {

                indicators.add(CalendarDateIndicator(
                        date = CalendarDate(dataBean.date),
                        color = ColorTools.getRandomColor(),
                        eventName = "${dataBean.id}" //dataBean.content!!.split(Contancts.FILE_TYPE_SPLIT)[0].replace(Contancts.FILE_TYPE_TEXT, "")
                )
                )
            }
            calendar_view2.datesIndicators = indicators
            calendar_view2.setupCalendar(selectionMode = CalendarView.SelectionMode.NON)

            calendar_view2.onDateClickListener = { date ->
                val dateIndicators = calendar_view2.getDateIndicators(date)
                        .filterIsInstance<CalendarDateIndicator>()
                        .toTypedArray()

                if (dateIndicators.isNotEmpty()) {
                    val builder = AlertDialog.Builder(context!!)
                            .setTitle("$date")
                            .setAdapter(DateIndicatorsDialogAdapter(context!!, dateIndicators), object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, postion: Int) {
                                    val item = JournalDBHelper.queryJournalById(dateIndicators[postion].eventName.toInt())[0]
                                    JournalManager.preViewJournal(context, item)
                                }

                            })

                    val dialog = builder.create()
                    dialog.show()
                }
            }
        })

    }

    class DateIndicatorsDialogAdapter(
            context: Context,
            events: Array<CalendarDateIndicator>
    ) : ArrayAdapter<CalendarDateIndicator>(context, 0, events) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = if (convertView == null) {
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_journal, parent, false)
            } else {
                convertView
            }

            val event = getItem(position)
            val item = JournalDBHelper.queryJournalById(event!!.eventName.toInt())[0]
            item?.let {
                val imageView = view.findViewById<ImageView>(R.id.imageView)
                val contentTextview = view.findViewById<TextView>(R.id.contentView)
                val infoTextview = view.findViewById<TextView>(R.id.infoTextView)
                val weekTextview = view.findViewById<TextView>(R.id.weekTextView)
                val dayTextview = view.findViewById<TextView>(R.id.dayTextView)
                var imageURL = ""
                if (item.journalItemBean == null) {
                    val itemBean = JournalItemBean()
                    val contentBuilder = StringBuilder()

                    if (item.content != null) {

                        val contents = item.content!!.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                        for (content in contents) {
                            if (content.startsWith(Contancts.FILE_TYPE_TEXT)) {
                                contentBuilder.append(content.substring(Contancts.FILE_TYPE_TEXT.length, content.length))
                            } else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)) {
                                if (TextUtils.isEmpty(imageURL)) {
                                    imageURL = content.substring(Contancts.FILE_TYPE_IMAGE.length, content.length)
                                }

                            }

                        }


                        itemBean.content = contentBuilder.toString()
                        item.journalItemBean = itemBean
                    }
                }

                contentTextview.text = item.journalItemBean!!.content
                if (item.address != null) {
                    infoTextview.text = DateTools.formatTime(item.date) + "." + StringTools.getNotNullString(item.address)
                }
                weekTextview.text = DateTools.getChineseWeek(Date(item.date))
                dayTextview.text = DateTools.getYMd(Date(item.date))[2].toString() + ""

                if (!TextUtils.isEmpty(imageURL)) {
                    Glide.with(context).load(imageURL).into(imageView)
                    imageView.visibility = View.VISIBLE
                } else {
                    imageView.visibility = View.GONE
                }
            }
            return view
        }
    }

    class CalendarDateIndicator(
            override val date: CalendarDate,
            override val color: Int,
            val eventName: String

    ) : CalendarView.DateIndicator


    companion object {
        fun newInstance() = Calendar2Fragment()
    }

    private lateinit var viewModel: Calendar2ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        return inflater.inflate(R.layout.calendar2_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Calendar2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        if (event!!.tag == MessageEvent.NOTE_CHANGE) {
            initData()
        }
    }
}
