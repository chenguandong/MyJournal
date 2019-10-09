package com.smart.journal.module.journal


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.customview.dialog.PreViewBottomSheetDialogFragment
import com.smart.journal.module.journal.adapter.JournalAdapter
import com.smart.journal.module.journal.viewmodel.JournalViewModel
import com.smart.journal.tools.DividerItemDecorationTools
import com.smart.journal.tools.KeyStoreTools
import com.smart.journal.tools.eventbus.MessageEvent
import kotlinx.android.synthetic.main.fragment_journal.*
import org.greenrobot.eventbus.EventBus

/**
 * A simple [Fragment] subclass.
 * Use the [JournalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JournalFragment : BaseFragment{

    private var isLoaded:Boolean?=false
    override fun getData() {
        if (!isLoaded!!){
            initData()
            isLoaded  = true
        }

    }


    private var journalAdapter: JournalAdapter? = null


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var journalViewModel: JournalViewModel? = null

    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_journal, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        var sha1 = KeyStoreTools.sHA1(activity);
        Log.i("sha1=",sha1);
    }


    override fun initView() {
        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel::class.java)

        journalViewModel!!
                .getLiveDataJournalBeans().observe(this, Observer {
                    journalBeanDBBeans -> journalAdapter!!.notifyDataSetChanged()
                })

        journalAdapter = JournalAdapter(R.layout.item_journal, journalViewModel!!.getJournalBeans())
        journalRecycleView!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        journalRecycleView!!.addItemDecoration(DividerItemDecorationTools.getItemDecoration(context))
        journalAdapter!!.setOnItemClickListener { adapter, view, position ->
            PreViewBottomSheetDialogFragment(journalViewModel!!.getJournalBeans().get(position)).show(fragmentManager,"")
        }
        journalAdapter!!.setOnItemLongClickListener { adapter, view, position ->

            AlertDialog.Builder(context).setItems(arrayOf<CharSequence>("查看", "删除")) { dialogInterface, i ->
                when (i) {
                    0 -> {
                        PreViewBottomSheetDialogFragment(journalViewModel!!.getJournalBeans().get(position)).show(fragmentManager,"")
                    }
                    1 -> {
                        journalViewModel!!.deleteJournal(journalViewModel!!.getJournalBeans()[position])
                        EventBus.getDefault().post(MessageEvent("",MessageEvent.NOTE_CHANGE))
                    }
                }

            }.create().show()
            false
        }
        journalRecycleView!!.adapter = journalAdapter

        swipeRefreshLayout.setOnRefreshListener({
            initData()
        })
    }

    override fun initData() {

        journalViewModel!!.getLiveDataJournalBeans()

        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        fun newInstance(): JournalFragment {
            val fragment = JournalFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, "")
            args.putString(ARG_PARAM2, "")
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        if (event!!.tag==MessageEvent.NOTE_CHANGE){
            initData()
        }
    }


}

