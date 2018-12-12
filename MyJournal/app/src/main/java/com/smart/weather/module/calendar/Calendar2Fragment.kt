package com.smart.weather.module.calendar

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.smart.weather.R
import com.smart.weather.base.BaseFragment
import com.smart.weather.module.write.db.JournalDBHelper
import com.smart.weather.tools.color.ColorTools
import com.smart.weather.tools.eventbus.MessageEvent
import io.realm.Realm
import kotlinx.android.synthetic.main.calendar2_fragment.*
import org.greenrobot.eventbus.EventBus
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView

class Calendar2Fragment : BaseFragment() {
    override fun getData() {
        init()
    }

    internal lateinit var realm: Realm
    val indicators = mutableListOf<CalendarView.DateIndicator>()

    override fun initView() {
    }

    override fun initData() {
        indicators.clear()
        val journalBeanDBBeans = JournalDBHelper.getAllJournals(realm)
        for (dataBean in journalBeanDBBeans)
        {

            indicators.add(CalendarDateIndicator(
                    date = CalendarDate(dataBean.date),
                    color = ColorTools.getRandomColor(),
                    eventName = dataBean.content
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
                        .setAdapter(DateIndicatorsDialogAdapter(context!!, dateIndicators), null)

                val dialog = builder.create()
                dialog.show()
            }
        }
    }
    class DateIndicatorsDialogAdapter(
            context: Context,
            events: Array<CalendarDateIndicator>
    ) : ArrayAdapter<CalendarDateIndicator>(context, 0, events) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = if (convertView == null) {
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_dialog_date_indicator, parent, false)
            } else {
                convertView
            }

            val event = getItem(position)
            view.findViewById<View>(R.id.color_view).setBackgroundColor(event.color)
            view.findViewById<TextView>(R.id.event_name_view).text = event.eventName

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realm = Realm.getDefaultInstance()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Calendar2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        EventBus.getDefault().unregister(this)
    }

    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        if (event!!.tag==MessageEvent.NOTE_CHANGE){
            initData()
        }
    }
}
