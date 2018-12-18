package com.smart.weather.module.write


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.*
import com.alibaba.fastjson.JSON
import com.smart.weather.R
import com.smart.weather.base.BaseFragment
import com.smart.weather.module.write.Views.ToolBean
import com.smart.weather.module.write.Views.ToolView
import com.smart.weather.module.write.adapter.WriteAdapter
import com.smart.weather.module.write.bean.JournalBean
import com.smart.weather.module.write.db.JournalDBHelper
import com.smart.weather.tools.PermissionTools
import com.smart.weather.tools.eventbus.MessageEvent
import com.smart.weather.tools.logs.LogTools
import com.yanzhenjie.album.Album
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_write.*
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import org.greenrobot.eventbus.EventBus


/**
 * A simple [Fragment] subclass.
 * Use the [WriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteFragment : BaseFragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private val writeSectionBeans = ArrayList<JournalBean>()
    private var adapter: WriteAdapter? = null

    private var realm: Realm? = null

    override fun getData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write, container, false)
        realm = Realm.getDefaultInstance()
        initSimpleToolbar(view, "Typing...")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun initView() {
        writeSectionBeans.add(JournalBean(""))

        adapter = WriteAdapter(writeSectionBeans, WriteAdapter.WriteAdapterModel.WriteAdapterModel_EDIT)
        writeRecycleView!!.layoutManager = LinearLayoutManager(context)
        writeRecycleView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()

        toolView!!.setDelegate(object : ToolView.ToolViewDelegate {
            override fun onItemClick(toolBean: ToolBean) {
                when (toolBean.itemType) {
                    ToolBean.TOOL_IMAGE -> {
                        PermissionTools.checkPermission(activity, PermissionTools.PermissionType.PERMISSION_TYPE_STORAGE, object : PermissionTools.PermissionCallBack {
                            override fun permissionYES() {

                                /* Matisse.from(getActivity())
                                        .choose(MimeType.ofAll())
                                        .countable(true)
                                        .maxSelectable(9)
                                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                        .thumbnailScale(0.85f)
                                        .imageEngine(new PicassoEngine())
                                        .capture(true)
                                        .forResult(REQUEST_CODE_CHOOSE);*/

                                activity?.let {

                                    Album.image(it) // Image selection.
                                            .multipleChoice()
                                            .camera(true)
                                            .columnCount(4)
                                            .onResult {
                                                LogTools.json(JSON.toJSONString(it))
                                            }
                                            .start()

                                }


                            }

                            override fun permissionNO() {

                            }
                        })
                    }
                }
            }
        })


    }

    override fun initData() {

    }

    private fun filterJournalItem() {


        var predicate: Predicate<JournalBean> = Predicate { it ->
            TextUtils.isEmpty(it.getContent()) && TextUtils.isEmpty(it.getImageURL())
        }
        val result = CollectionUtils.select(writeSectionBeans, predicate) as List<JournalBean>
        writeSectionBeans.removeAll(result)
        writeSectionBeans.add(JournalBean(""))
        adapter!!.notifyDataSetChanged()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        /*if (resultCode === RESULT_OK && requestCode === PhotoPicker.REQUEST_CODE) {
            val mSelected:ArrayList<String> = ArrayList()
            mSelected.addAll(data!!.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS))
            for (uri in mSelected) {
               // writeSectionBeans.add(JournalBean("", MJFileTools.saveJournalImageFile2Local(context, uri)))
            }
            filterJournalItem()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_main, menu)
        menu!!.findItem(R.id.toolbar_right_action).title = "保存"


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            R.id.toolbar_right_action -> {

                JournalDBHelper.saveJournal(realm!!, writeSectionBeans)
                EventBus.getDefault().post(MessageEvent("", MessageEvent.NOTE_CHANGE))
                activity!!.finish()
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm!!.close()
    }

    companion object {
        const val REQUEST_CODE_CHOOSE = 9
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): WriteFragment {
            val fragment = WriteFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, "")
            args.putString(ARG_PARAM2, "")
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
