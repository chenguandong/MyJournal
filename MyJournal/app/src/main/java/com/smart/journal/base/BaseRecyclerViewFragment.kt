package com.smart.journal.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.journal.R
import com.smart.journal.tools.DividerItemDecorationTools
import kotlinx.android.synthetic.main.base_recycler_view_fragment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 const val ARG_PARAM1 = "param1"
 const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseRecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseRecyclerViewFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var paramType: String? = null
    private var paramData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramType = it.getString(ARG_PARAM1)
            paramData = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_recycler_view_fragment, container, false)
    }

    override fun initData() {
    }

    override fun getData() {
    }

    override fun initView() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView!!.addItemDecoration(DividerItemDecorationTools.getItemDecoration(context))
        init()
    }



}
