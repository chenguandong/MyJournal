package com.smart.journal.module.menu

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.menu.adapter.SlideMenuAdapter
import com.smart.journal.module.menu.bean.SlideMenuBean
import com.smart.journal.module.menu.enums.ItemMenuType
import com.smart.journal.module.menu.view.SlideMenuHeaderView
import com.smart.journal.module.tags.activity.TagActivity
import kotlinx.android.synthetic.main.fragment_slide_menu.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SlideMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var param1: String? = null
    var param2: String? = null
    /*private var listener: OnFragmentInteractionListener? = null*/
    val REQUEST_CODE = 100

    var menusList: ArrayList<SlideMenuBean>? = ArrayList()
    var menuAdapter: SlideMenuAdapter? = null

    private var headerView: SlideMenuHeaderView? = null

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
        initView()
        initData()

    }

    private fun initView() {
        headerView = SlideMenuHeaderView(context = context, delegate = object :SlideMenuHeaderView.SlideMenuHeaderViewDelegate{
            override fun onTagItemClick() {
                startActivity(Intent(context,TagActivity::class.java).putExtra(TagActivity.ACTIVITY_TYPE,TagActivity.TagActivityType.SEARCH))
            }

            override fun onLocationItemClick() {
            }

            override fun onFavouriteItemClick() {
            }

            override fun onThisDayItemClick() {
            }

        })
    }

    private fun initData() {
        var allDbNoteList: List<NoteBookDBBean> = MyApp.database!!.mNoteBookDao().allNoteBook() as List<NoteBookDBBean>
        if (allDbNoteList.isEmpty()) {
            MyApp.database!!.mNoteBookDao().saveNoteBook(NoteBookDBBean("默认"))
        }
        allDbNoteList = MyApp.database!!.mNoteBookDao().allNoteBook() as List<NoteBookDBBean>
        menusList!!.clear()
        allDbNoteList.forEach {
            menusList!!.add(SlideMenuBean(it, ItemMenuType.MENU_NOTE_BOOK))
        }
        menusList!!.add(SlideMenuBean(NoteBookDBBean(""), ItemMenuType.MENU_NOTE_BOOK_ADD))
        menuAdapter = SlideMenuAdapter(menusList!!)


        menuRecyclerView.layoutManager = LinearLayoutManager(context)
        headerView?.let { menuAdapter!!.addHeaderView(it) }
        menuRecyclerView.adapter = menuAdapter
        menuAdapter!!.notifyDataSetChanged()

        menuAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                if (menusList!!.get(position)!!.itemType == ItemMenuType.MENU_NOTE_BOOK_ADD) {
                    var intent = Intent(context, CreateNoteBookActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE) {
            initData()
        }
    }
    /* fun onButtonPressed(uri: Uri) {
         listener?.onFragmentInteraction(uri)
     }*/


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
