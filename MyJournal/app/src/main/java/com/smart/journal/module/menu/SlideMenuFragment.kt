package com.smart.journal.module.menu

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.base.BaseFragment
import com.smart.journal.customview.dialog.OnThisDayBottomSheetDialogFragment
import com.smart.journal.db.entity.NoteBookDBBean
import com.smart.journal.module.journal.SearchEableType
import com.smart.journal.module.menu.adapter.SlideMenuAdapter
import com.smart.journal.module.menu.bean.SlideMenuBean
import com.smart.journal.module.menu.enums.ItemMenuType
import com.smart.journal.module.menu.view.SlideMenuHeaderView
import com.smart.journal.module.tags.activity.SearchActivity
import com.smart.journal.tools.eventbus.MessageEvent
import kotlinx.android.synthetic.main.fragment_slide_menu.*
import org.greenrobot.eventbus.EventBus
import kotlin.random.Random

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SlideMenuFragment : BaseFragment() {

    interface SlideMenuFragmentCallBack {
        // TODO: Update argument type and name
        fun onNoteBookItemClick(notebook:NoteBookDBBean)
    }

    var param1: String? = null
    var param2: String? = null

    /*private var listener: OnFragmentInteractionListener? = null*/
    val REQUEST_CODE = 100

    var menusList: ArrayList<SlideMenuBean>? = ArrayList()
    var menuAdapter: SlideMenuAdapter? = null

    var onThisDayFragment:OnThisDayBottomSheetDialogFragment? = null

    private var headerView: SlideMenuHeaderView? = null

     var slideMenuFragmentCallBack:SlideMenuFragmentCallBack ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
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

    override fun getData() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }


    override fun initView() {
        headerView = SlideMenuHeaderView(context = context, delegate = object : SlideMenuHeaderView.SlideMenuHeaderViewDelegate {
            override fun onTagItemClick() {
                SearchActivity.startActivity(context!!, SearchEableType.TAG)
            }

            override fun onLocationItemClick() {
                SearchActivity.startActivity(context!!, SearchEableType.LOCATION)
            }

            override fun onFavouriteItemClick() {
            }

            override fun onThisDayItemClick() {
                onThisDayFragment =  null
                if (onThisDayFragment==null){
                    onThisDayFragment = OnThisDayBottomSheetDialogFragment()
                }
                onThisDayFragment!!.show(activity!!.supportFragmentManager, "$Random(10).nextInt()")
            }

        })

        menuAdapter = SlideMenuAdapter(menusList!!)
        menuRecyclerView.layoutManager = LinearLayoutManager(context)
        headerView?.let { menuAdapter!!.addHeaderView(it) }
        menuRecyclerView.adapter = menuAdapter

        menuAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                var itemType = menusList!!.get(position)!!.itemType
                when(itemType){
                    ItemMenuType.MENU_NOTE_BOOK_ADD->{
                        var intent = Intent(context, CreateNoteBookActivity::class.java)
                        startActivityForResult(intent, REQUEST_CODE)
                    }
                    ItemMenuType.MENU_NOTE_BOOK->{
                        slideMenuFragmentCallBack?.let {
                            it.onNoteBookItemClick(menusList!![position].noteBook!!)
                        }
                    }
                }
            }

        })
    }

    override fun initData() {
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
        menuAdapter!!.notifyDataSetChanged()

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

    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        if (event!!.tag == MessageEvent.NOTE_CHANGE) {
            headerView!!.refresh()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
