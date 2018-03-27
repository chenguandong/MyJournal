package com.smart.weather.module.journal


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smart.weather.R
import com.smart.weather.base.BaseFragment
import com.smart.weather.module.journal.adapter.JournalAdapter
import com.smart.weather.module.journal.viewmodel.JournalViewModel
import com.smart.weather.tools.DividerItemDecorationTools
import kotlinx.android.synthetic.main.fragment_write.*

/**
 * A simple [Fragment] subclass.
 * Use the [JournalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JournalFragment : BaseFragment{


    private var journalAdapter: JournalAdapter? = null


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var journalViewModel: JournalViewModel? = null

    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel::class.java)

        journalViewModel!!
                .getLiveDataJournalBeans().observe(this, Observer {
                    journalBeanDBBeans -> journalAdapter!!.notifyDataSetChanged()
                })
        initView()
        initData()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_journal, container, false);
    }



    override fun onStart() {
        super.onStart()
        journalViewModel!!.getLiveDataJournalBeans()
    }

    override fun initView() {
        journalAdapter = JournalAdapter(R.layout.item_journal, journalViewModel!!.getJournalBeans())
        recycleView!!.layoutManager = LinearLayoutManager(context)
        recycleView!!.addItemDecoration(DividerItemDecorationTools.getItemDecoration(context))
        journalAdapter!!.setOnItemClickListener { adapter, view, position ->

        }
        journalAdapter!!.setOnItemLongClickListener { adapter, view, position ->

            AlertDialog.Builder(context).setItems(arrayOf<CharSequence>("查看", "删除")) { dialogInterface, i ->
                when (i) {
                    0 -> {
                    }
                    1 -> journalViewModel!!.deleteJournal(journalViewModel!!.getJournalBeans()[position])
                }

            }.create().show()
            false
        }
        recycleView!!.adapter = journalAdapter
    }

    override fun initData() {


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
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
}

