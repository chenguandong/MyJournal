package com.smart.journal.customview.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.journal.R
import com.smart.journal.module.journal.JournalFragment
import com.smart.journal.module.journal.SearchEableType
import kotlinx.android.synthetic.main.act_search_adress.*

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
open class OnThisDayBottomSheetDialogFragment : BottomSheetDialogFragment() {
    protected var behavior: BottomSheetBehavior<*>? = null

    private var journalFragment:JournalFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_this_day_dialog_bottom_sheet, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (context == null) {
            super.onCreateDialog(savedInstanceState)
        } else BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        journalFragment =JournalFragment.newInstance("","")
        childFragmentManager.beginTransaction().replace(R.id.journalLayout, journalFragment!!).commit()
        Handler().postDelayed({
            journalFragment!!.doSerarch("", SearchEableType.ON_THIS_DAY)
        },100)
    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        bottomSheet!!.setBackgroundColor(Color.TRANSPARENT)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
            layoutParams.height = getPeekHeight()
            behavior = BottomSheetBehavior.from(bottomSheet)
            behavior!!.peekHeight = getPeekHeight()
            setOpenState()
        }
        KeyboardUtils.hideSoftInput(requireActivity())
    }

    open fun setOpenState() {
        // 初始为展开状态
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    /**
     * 弹窗高度，默认为屏幕高度的四分之三
     * 子类可重写该方法返回peekHeight
     *
     * @return height
     */
    protected open fun getPeekHeight(): Int {
        val peekHeight = resources.displayMetrics.heightPixels
        //设置弹窗高度为屏幕高度的3/4
        return peekHeight - peekHeight / 10
    }

}