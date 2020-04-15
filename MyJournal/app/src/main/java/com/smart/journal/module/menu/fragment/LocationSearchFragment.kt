package com.smart.journal.module.menu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.smart.journal.R
import com.smart.journal.base.ARG_PARAM1
import com.smart.journal.base.ARG_PARAM2
import com.smart.journal.base.BaseRecyclerViewFragment
import com.smart.journal.db.entity.JournalBeanDBBean
import com.smart.journal.module.menu.adapter.LocationSearchAdapter
import kotlinx.android.synthetic.main.base_recycler_view_fragment.*

class LocationSearchFragment : BaseRecyclerViewFragment() {

    interface LocationSearchFragmentDelegate {
        fun onItemClick(address: String)
    }

    private lateinit var viewModel: LocationSearchViewModel

    private var locationSearchAdapter: LocationSearchAdapter? = null

    var delegate: LocationSearchFragmentDelegate? = null
        set(value) {
            field = value
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_recycler_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationSearchViewModel::class.java)
        getData()
    }

    override fun initView() {
        super.initView()
        locationSearchAdapter = LocationSearchAdapter(ArrayList())
        recycleView.adapter = locationSearchAdapter
        locationSearchAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                delegate?.let {
                    var journalMap: Map<String, List<JournalBeanDBBean>> = adapter.data[position] as Map<String, List<JournalBeanDBBean>>
                    journalMap[journalMap!!.keys!!.elementAt(0)]?.get(0)!!.address?.let { it1 -> it.onItemClick(it1) }
                }
            }

        })
    }

    fun searchText(keyWord: String) {
        viewModel!!.searchLocationByLocationName(keyWord).observe(viewLifecycleOwner, Observer {
            filterList(it)
        })
    }

    override fun getData() {
        super.getData()
        viewModel.journalData.observe(viewLifecycleOwner, Observer { it ->
            filterList(it)
        })
    }

    private fun filterList(it: List<JournalBeanDBBean>) {
        var locationDataMap: MutableMap<String, MutableList<JournalBeanDBBean>> = mutableMapOf()
        for (journal in it) {
            journal.address?.let {
                if (locationDataMap.containsKey(it)) {
                    locationDataMap[it]!!.add(journal)
                } else {
                    locationDataMap.put(it, mutableListOf(journal))
                }
            }
        }

        var locationDataList: MutableList<Map<String, MutableList<JournalBeanDBBean>>> = mutableListOf()

        for ((k, v) in locationDataMap) {
            locationDataList.add(mapOf(k to v))
        }

        locationSearchAdapter!!.setNewData(locationDataList)
    }

    companion object {


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LocationSearchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

}
