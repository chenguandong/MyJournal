package com.smart.journal.module.menu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.journal.R
import com.smart.journal.module.menu.adapter.SlideMenuAdapter
import com.smart.journal.module.menu.bean.NoteBookBean
import com.smart.journal.module.menu.bean.SlideMenuBean
import com.smart.journal.module.menu.enums.ItemMenuType
import kotlinx.android.synthetic.main.fragment_slide_menu.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SlideMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var param1: String? = null
    var param2: String? = null
    /*private var listener: OnFragmentInteractionListener? = null*/

    var menusList: ArrayList<SlideMenuBean>? = ArrayList()
    var menuAdapter:SlideMenuAdapter?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuRecyclerView
        initData();
    }

    private fun initData() {
        menusList!!.add(SlideMenuBean(NoteBookBean("book1", Random(10).nextInt()),ItemMenuType.MENU_NOTE_BOOK));
        menusList!!.add(SlideMenuBean(NoteBookBean("book1", Random(10).nextInt()),ItemMenuType.MENU_NOTE_BOOK));
        menusList!!.add(SlideMenuBean(NoteBookBean("book1", Random(10).nextInt()),ItemMenuType.MENU_NOTE_BOOK));
        menusList!!.add(SlideMenuBean(NoteBookBean("book1", Random(10).nextInt()),ItemMenuType.MENU_NOTE_BOOK));
        menusList!!.add(SlideMenuBean(NoteBookBean("book1", Random(10).nextInt()),ItemMenuType.MENU_NOTE_BOOK));

        menuAdapter = SlideMenuAdapter(menusList!!)
        menuRecyclerView.layoutManager = LinearLayoutManager(context);
        menuRecyclerView.adapter = menuAdapter

    }
    // TODO: Rename method, update argument and hook method into UI event
    /* fun onButtonPressed(uri: Uri) {
         listener?.onFragmentInteraction(uri)
     }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        // listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SlideMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SlideMenuFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
