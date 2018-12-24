package com.smart.journal.customview.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.blankj.utilcode.util.ScreenUtils
import com.smart.journal.R
import com.smart.journal.contants.Contancts
import com.smart.journal.customview.preview.PhotoViewTools
import com.smart.journal.module.write.adapter.WriteAdapter
import com.smart.journal.module.write.bean.JournalBean
import com.smart.journal.module.write.bean.JournalBeanDBBean
import kotlinx.android.synthetic.main.view_dialog_preview_bottom_sheet.*
import java.util.*


/**
 * @author guandongchen
 * @date 2018/9/4
 */
@SuppressLint("ValidFragment")
class PreViewBottomSheetDialogFragment : android.support.design.widget.BottomSheetDialogFragment {


    private var behavior: BottomSheetBehavior<*>? = null
    private val writeSectionBeans = ArrayList<JournalBean>()
    private var adapter: WriteAdapter? = null
    private var journalBeanDBBean: JournalBeanDBBean? = null


    constructor(journalBeanDBBean: JournalBeanDBBean) {
        this.journalBeanDBBean = journalBeanDBBean

        if (journalBeanDBBean.content != null) {

            val contents = journalBeanDBBean.content.split(Contancts.FILE_TYPE_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (content in contents) {
                if (content.startsWith(Contancts.FILE_TYPE_TEXT)) {
                    writeSectionBeans.add(JournalBean(content.replace(Contancts.FILE_TYPE_TEXT, "")))
                } else if (content.startsWith(Contancts.FILE_TYPE_IMAGE)) {
                    writeSectionBeans.add(JournalBean("", content.replace(Contancts.FILE_TYPE_IMAGE, "")))
                }

            }

        }
    }

    constructor() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_dialog_preview_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WriteAdapter(writeSectionBeans, WriteAdapter.WriteAdapterModel.WriteAdapterModel_SHOW)
        recyclerView.setLayoutManager(LinearLayoutManager(activity))
        recyclerView.setAdapter(adapter)
        adapter!!.notifyDataSetChanged()
        adapter!!.setOnItemClickListener { adapter, view1, position ->
            if (writeSectionBeans[position].itemType == JournalBean.WRITE_TAG_IMAGE) {
                PhotoViewTools.showPhotos(object : ArrayList<String>() {
                    init {
                        add(writeSectionBeans[position].imageURL)
                    }
                }, 0, context)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (context == null) {
            super.onCreateDialog(savedInstanceState)
        } else BottomSheetDialog(context!!, R.style.TransparentBottomSheetStyle)
    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.delegate.findViewById<FrameLayout>(android.support.design.R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = ScreenUtils.getScreenHeight()
            behavior = BottomSheetBehavior.from(bottomSheet)
            // 初始为展开状态
            behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun doclick(v: View) {
        //点击任意布局关闭
        behavior!!.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
