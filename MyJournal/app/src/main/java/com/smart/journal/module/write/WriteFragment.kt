package com.smart.journal.module.write


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.customview.preview.PhotoViewTools
import com.smart.journal.module.map.activity.AMapAdressSearchActivity
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.module.write.adapter.WriteAdapter
import com.smart.journal.module.write.bean.JournalBean
import com.smart.journal.module.write.bean.ToolBean
import com.smart.journal.module.write.bean.WriteSettingBean
import com.smart.journal.module.write.db.JournalDBHelper
import com.smart.journal.module.write.viewmodel.WriteFragmentViewModel
import com.smart.journal.module.write.views.MoreSettingBottomSheetDialogFragment
import com.smart.journal.module.write.views.ToolView
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
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [WriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteFragment : BaseFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var writeSectionBeans = ArrayList<JournalBean>()
    private var adapter: WriteAdapter? = null
    private var writeSetting: WriteSettingBean? = WriteSettingBean()

    private var isEditable: Boolean = false
        set(value) {
            field = value
            writeSetting!!.isEditable = true
            adapter?.isEditable = field

        }

    private var isShowDataModel = false
    override fun getData() {

    }

    val viewModel by viewModels<WriteFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }

        viewModel.getJounalData().observe(this, Observer<List<JournalBean>> { journal ->
            // update UI
        })

        viewModel.getLocation().observe(this, Observer<MjPoiItem> {

        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write, container, false)
        initSimpleToolbar(view, writeSetting?.time?.let { Date(it).toLocaleString() })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showData = activity!!.intent.getSerializableExtra(SHOW_DATA)
        if (showData != null) {
            isShowDataModel = true
            writeSectionBeans = showData as ArrayList<JournalBean>
            activity!!.intent.getSerializableExtra(SHOW_DATA_SETTING)?.let { it ->
                writeSetting = it as WriteSettingBean
            }
            fab.setImageResource(R.drawable.ic_journal_edit)
        } else {
            writeSectionBeans.add(JournalBean(""))
            writeSetting!!.time = System.currentTimeMillis()
            isEditable = true

        }

        init()

    }

    override fun initView() {

        var model: WriteAdapter.WriteAdapterModel? = null
        if (isShowDataModel) {
            model = WriteAdapter.WriteAdapterModel.WriteAdapterModel_SHOW
        } else {
            model = WriteAdapter.WriteAdapterModel.WriteAdapterModel_EDIT
        }
        adapter = WriteAdapter(writeSectionBeans, model!!)
        writeRecycleView!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        writeRecycleView!!.adapter = adapter
        adapter!!.isEditable = isEditable
        writeSetting!!.isEditable = isEditable
        adapter!!.notifyDataSetChanged()

        adapter!!.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                if (writeSectionBeans[position].itemType == JournalBean.WRITE_TAG_IMAGE) {
                    PhotoViewTools.showPhotos(object : java.util.ArrayList<String>() {
                        init {
                            writeSectionBeans[position].imageURL?.let { add(it) }
                        }
                    }, 0, context)
                }
            }

        })

        fab.setOnClickListener {
            if (isEditable) {
                saveJournal()
                fab.setImageResource(R.drawable.ic_journal_save)
                KeyboardUtils.hideSoftInput(activity)
            } else {
                isEditable = true
                fab.setImageResource(R.drawable.ic_journal_save)
            }

        }

        toolView!!.setDelegate(object : ToolView.ToolViewDelegate {
            override fun onItemClick(toolBean: ToolBean) {
                if (isEditable) {
                    when (toolBean.itemType) {

                        //位置
                        ToolBean.ToolBeanType.TOOL_LOCATION -> {
                            val locationIntent = Intent(context, AMapAdressSearchActivity::class.java)
                            locationIntent.putExtra(AMapAdressSearchActivity.INTENT_TITLE, "位置")
                            startActivity(locationIntent)
                        }

                        //图片
                        ToolBean.ToolBeanType.TOOL_IMAGE -> {
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

                if (toolBean.itemType == ToolBean.ToolBeanType.TOOL_MORE) {
                    MoreSettingBottomSheetDialogFragment(writeSetting).show(childFragmentManager, "")
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

        if (isShowDataModel) {
            inflater.inflate(R.menu.menu_journal_detail, menu)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.toolbar_right_action -> {
            }
            R.id.toolbar_edit -> { //编辑
                isEditable = true

            }

            R.id.toolbar_delete -> {//删除

                writeSetting!!.journalId?.let {
                    JournalDBHelper!!.deleteJournalById(it)
                    finish()
                }
            }

        }


        return false
    }

    private fun saveJournal() {
        JournalDBHelper.saveJournal(writeSectionBeans, settingBean = writeSetting)
        finish()
    }

    private fun finish() {
        EventBus.getDefault().post(MessageEvent("", MessageEvent.NOTE_CHANGE))
        activity!!.finish()
    }


    @MainThread
    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)

        when (event!!.tag) {
            MessageEvent.NOTE_LOCATION_CHANGE -> {
                val poiItem: MjPoiItem = JSON.parseObject(event.message, MjPoiItem::class.java)
                poiItem?.let {
                    writeSetting!!.location = it
                }
            }
            MessageEvent.NOTE_TAG_CHANGE -> {
                var selectedTag: List<String> = Gson().fromJson(event.message, object : TypeToken<List<String>>() {}.type)
                selectedTag?.let { it ->
                    writeSetting!!.tags = it as List<String>
                }
            }
            MessageEvent.NOTE_FAVOURITE_CHANGE->{
                writeSetting!!.isFavourite = event.message.toInt()==1
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_LOCATION_CODE -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        const val REQUEST_LOCATION_CODE = 9

        //日记内容
        const val SHOW_DATA = "showData"

        //日记的设置的内容
        const val SHOW_DATA_SETTING = "showDataSetting"

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
