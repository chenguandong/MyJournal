package com.smart.journal.module.journal


import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SizeUtils
import com.gavin.com.library.StickyDecoration
import com.gavin.com.library.listener.GroupListener
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.journal.adapter.JournalAdapter
import com.smart.journal.module.journal.manager.JournalManager
import com.smart.journal.module.journal.viewmodel.JournalViewModel
import com.smart.journal.module.tags.activity.SearchActivity
import com.smart.journal.tools.KeyStoreTools
import com.smart.journal.tools.eventbus.MessageEvent
import kotlinx.android.synthetic.main.fragment_journal.*
import org.apache.commons.collections4.CollectionUtils
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [JournalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

 open class JournalFragment : BaseFragment, SearchEable  {

    private var isLoaded: Boolean? = false
    override fun getData() {
        if (!isLoaded!!) {
            initData()
            isLoaded = true
        }

    }


    private var journalAdapter: JournalAdapter? = null


    // TODO: Rename and change types of parameters
    private var fragmentType: String? = null
    private var param2: String? = null

    private val journalViewModel by viewModels<JournalViewModel>()

    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        arguments?.let {
            fragmentType = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        var sha1 = KeyStoreTools.sHA1(activity)
        Log.i("sha1=", sha1)
    }


    override fun initView() {

        journalAdapter = JournalAdapter(R.layout.item_journal, arrayListOf())

        val groupListener = GroupListener { postion ->
            Date(journalAdapter!!.data[postion].date).toLocaleString()
        }
        val decoration = StickyDecoration.Builder
                .init(groupListener) //重置span（使用GridLayoutManager时必须调用）
                .setGroupHeight(SizeUtils.dp2px(30F))
                .setGroupTextSize(SizeUtils.sp2px(12F))
                .setGroupTextColor(ContextCompat.getColor(context, R.color.black))
                .setGroupBackground(ContextCompat.getColor(context, R.color.gray_bg_light))
                .setDivideColor(ContextCompat.getColor(context, R.color.divider))
                .setDivideHeight(1)
                .build()

        journalRecycleView!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        //journalRecycleView!!.addItemDecoration(DividerItemDecorationTools.getItemDecoration(context))
        journalRecycleView!!.addItemDecoration(decoration)
        journalAdapter!!.setOnItemClickListener { adapter, view, position ->
            JournalManager.preViewJournal(context, journalAdapter!!.data[position])
        }
        journalAdapter!!.setOnItemLongClickListener { adapter, view, position ->

            AlertDialog.Builder(context).setItems(arrayOf<CharSequence>("查看", "删除")) { dialogInterface, i ->
                when (i) {
                    0 -> {
                        // PreViewBottomSheetDialogFragment(journalViewModel!!.getJournalBeans().get(position)).show(fragmentManager!!,"")
                        JournalManager.preViewJournal(context, journalAdapter!!.data[position])
                    }
                    1 -> {
                        journalViewModel!!.deleteJournal(journalAdapter!!.data[position])
                        EventBus.getDefault().post(MessageEvent("", MessageEvent.NOTE_CHANGE))
                    }
                }

            }.create().show()
            false
        }
        journalRecycleView!!.adapter = journalAdapter

        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }


    override fun initData() {


        when (fragmentType) {
            FRAGMENT_TYPE_SEARCH_TAG -> {
                param2?.let {
                    journalViewModel!!.searchJournalByTag(it).observe(viewLifecycleOwner, Observer {

                        it?.let { datas ->
                            journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                        }
                    })
                }

            }
            FRAGMENT_TYPE_SEARCH_LOCATION -> {
                param2?.let {
                    journalViewModel!!.searchJournalByLocationName(it).observe(viewLifecycleOwner, Observer {

                        it?.let { datas ->
                            journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                        }
                    })
                }
            }
            FRAGMENT_EMPTY -> {
            }
            else -> {
                journalViewModel!!.getJournalBeans().observe(viewLifecycleOwner, Observer {
                    journalAdapter!!.setNewData(it as MutableList<JournalBeanDBBean>?)
                })
            }
        }

        swipeRefreshLayout.isRefreshing = false
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    companion object {

        const val FRAGMENT_TYPE_SEARCH_TAG = "fragment_type_search_tag"
        const val FRAGMENT_TYPE_SEARCH_LOCATION = "fragment_type_search_location"

        //初始化时候不做任何事情显示一个空界面
        const val FRAGMENT_EMPTY = "fragment_empty"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                JournalFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    @MainThread
    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        if (event!!.tag == MessageEvent.NOTE_CHANGE) {
            initData()
            doSerarch("", SearchEableType.ALL);
        }
    }

    override fun doSerarch(serarchKey: String,@SearchEableType searchType: String) {
        when (searchType) {
            SearchEableType.ALL -> {
                journalViewModel!!.searchJournalByKeyWord(serarchKey).observe(viewLifecycleOwner, Observer {
                    it?.let { datas ->
                        journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                    }
                })
            }
            SearchEableType.FAVOURITE -> {
                journalViewModel!!.searchJournalByFavourite(serarchKey).observe(viewLifecycleOwner, Observer {
                    it?.let { datas ->
                        journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                    }
                })
            }
            SearchEableType.TAG -> {
                journalViewModel!!.searchJournalByTag(serarchKey).observe(viewLifecycleOwner, Observer {
                    it?.let { datas ->
                        journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                    }
                })
            }

            SearchEableType.LOCATION -> {
                journalViewModel!!.searchJournalByLocationName(serarchKey).observe(viewLifecycleOwner, Observer {
                    it?.let { datas ->
                        journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                    }
                })
            }
            SearchEableType.ON_THIS_DAY -> {
                journalViewModel!!.searchJournalByKeyWord(serarchKey).observe(viewLifecycleOwner, Observer { it ->
                    it?.let { datas ->

                        journalAdapter!!.setNewData(CollectionUtils.select(datas) { it ->
                            DateUtils.isToday(it.date)
                        } as MutableList<JournalBeanDBBean>?)
                    }
                })
            }

            SearchEableType.NOTE_BOOK->{
                journalViewModel!!.searchByNoteBookName(serarchKey).observe(viewLifecycleOwner, Observer { it ->
                    it?.let { datas ->
                        journalAdapter!!.setNewData(datas as MutableList<JournalBeanDBBean>)
                    }
                })
            }
        }

    }


}

