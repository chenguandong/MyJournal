package com.smart.weather.module.photos


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smart.weather.R
import com.smart.weather.base.BaseFragment
import com.smart.weather.customview.dialog.PreViewBottomSheetDialogFragment
import com.smart.weather.module.journal.tools.JournalTools
import com.smart.weather.module.photos.adapter.PhotoAdapter
import com.smart.weather.module.photos.adapter.PhotoBean
import com.smart.weather.module.write.bean.JournalBeanDBBean
import com.smart.weather.module.write.db.JournalDBHelper
import com.smart.weather.tools.DateTools
import com.smart.weather.tools.decorator.GridDividerItemDecoration
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_photos.*
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotosFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PhotosFragment : BaseFragment() {

    var photoAdapter:PhotoAdapter ?= null
    var photosList:ArrayList<PhotoBean>  = ArrayList()

    private val realm = Realm.getDefaultInstance()
    private var journalBeanDBBeans: RealmResults<JournalBeanDBBean>? = null
    override fun initView() {
        photoAdapter = PhotoAdapter(R.layout.item_photo,photosList)
        recyclerView.layoutManager = GridLayoutManager(getContext(),3)
        recyclerView.addItemDecoration(GridDividerItemDecoration(getContext(),3))
        recyclerView.adapter = photoAdapter
        photoAdapter!!.setOnItemClickListener { adapter, view, position ->

             var predicate: Predicate<JournalBeanDBBean> = Predicate {
                 it.id == journalBeanDBBeans!![position]!!.id
             }
             var result:List<JournalBeanDBBean> = CollectionUtils.select(journalBeanDBBeans,predicate) as List<JournalBeanDBBean>

            PreViewBottomSheetDialogFragment(result[0]).show(fragmentManager,"")
        }
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    override fun initData() {
        journalBeanDBBeans = JournalDBHelper.getAllJournals(realm)
        photosList.clear()
        for (dataBean in journalBeanDBBeans!!) {

            if (!TextUtils.isEmpty(JournalTools.getFistPhoto(dataBean.content))){
                var photoBean = PhotoBean()
                photoBean.photoURL = JournalTools.getFistPhoto(dataBean.content)
                photoBean.photoDate = DateTools.formatTime(dataBean.date.time)
                photoBean.journalID = dataBean.id
                photosList.add(photoBean)
            }


        }

        photoAdapter!!.notifyDataSetChanged()
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PhotosFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}
