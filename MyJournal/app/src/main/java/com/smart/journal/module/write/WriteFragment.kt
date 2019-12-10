package com.smart.journal.module.write


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.module.map.activity.AMapAdressSearchActivity
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.module.write.Views.ToolBean
import com.smart.journal.module.write.Views.ToolView
import com.smart.journal.module.write.adapter.WriteAdapter
import com.smart.journal.module.write.bean.JournalBean
import com.smart.journal.module.write.db.JournalDBHelper
import com.smart.journal.tools.PermissionTools
import com.smart.journal.tools.eventbus.MessageEvent
import com.smart.journal.tools.file.MJFileTools
import com.smart.journal.tools.image.ImageLoader
import com.smart.journal.tools.logs.LogTools
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import com.yanzhenjie.album.api.widget.Widget
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

    override fun getData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write, container, false)
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
        writeRecycleView!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        writeRecycleView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()

        toolView!!.setDelegate(object : ToolView.ToolViewDelegate {
            override fun onItemClick(toolBean: ToolBean) {
                when (toolBean.itemType) {

                    ToolBean.TOOL_LOCATION -> {
                        val locationIntent = Intent(context, AMapAdressSearchActivity::class.java)
                        locationIntent.putExtra(AMapAdressSearchActivity.INTENT_TITLE, "位置")
                        startActivityForResult(locationIntent, REQUEST_LOCATION_CODE)
                    }

                    ToolBean.TOOL_IMAGE -> {
                        PermissionTools.checkPermission(activity, PermissionTools.PermissionType.PERMISSION_TYPE_STORAGE, object : PermissionTools.PermissionCallBack {
                            override fun permissionYES() {


                                activity?.let { it ->

                                    Album.initialize(
                                            AlbumConfig.newBuilder(it)
                                                    .setAlbumLoader(ImageLoader())
                                                    .build()
                                    )


                                    Album.image(it) // Image selection.
                                            .multipleChoice()
                                            .camera(true)
                                            .columnCount(4)
                                            .widget(Widget.newDarkBuilder(it).title("选择文件")
                                                    .build())
                                            .onResult {
                                                LogTools.json(JSON.toJSONString(it))
                                                val mSelected: ArrayList<String> = ArrayList()
                                                for (album in it) {

                                                    writeSectionBeans.add(JournalBean("", MJFileTools.saveJournalImageFile2Local(album)))
                                                    filterJournalItem()
                                                }
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
            TextUtils.isEmpty(it.content) && TextUtils.isEmpty(it.imageURL)
        }
        val result = CollectionUtils.select(writeSectionBeans, predicate) as List<JournalBean>
        writeSectionBeans.removeAll(result)
        writeSectionBeans.add(JournalBean(""))
        adapter!!.notifyDataSetChanged()


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_main, menu)
        menu!!.findItem(R.id.toolbar_right_action).title = "保存"


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {

            R.id.toolbar_right_action -> {

                JournalDBHelper.saveJournal(writeSectionBeans)
                EventBus.getDefault().post(MessageEvent("", MessageEvent.NOTE_CHANGE))
                activity!!.finish()
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                val jsonStr = data!!.getStringExtra(AMapAdressSearchActivity.INTENT_LOCATION)
                        ?: return
                val poiItem = Gson().fromJson<Any>(jsonStr, MjPoiItem::class.java) ?: return

            }
        }
    }

    companion object {
        const val REQUEST_LOCATION_CODE = 9
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
