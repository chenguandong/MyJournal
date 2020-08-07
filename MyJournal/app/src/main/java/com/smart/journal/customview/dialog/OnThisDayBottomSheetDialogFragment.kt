package com.smart.journal.customview.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.journal.R
import com.smart.journal.module.journal.JournalFragment
import com.smart.journal.tools.DividerItemDecorationTools
import kotlinx.android.synthetic.main.view_dialog_preview_bottom_sheet.*

/**
 *
 * @author guandongchen
 * @date 2020/3/9
 */
open class OnThisDayBottomSheetDialogFragment : BottomSheetDialogFragment() {
    protected var behavior: BottomSheetBehavior<*>? = null

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

    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
            layoutParams.height = ScreenUtils.getScreenHeight()-200
            behavior = BottomSheetBehavior.from(bottomSheet)
            setOpenState()
        }
        KeyboardUtils.hideSoftInput(activity)
    }

    open fun setOpenState() {
        // 初始为展开状态
        behavior!!.state = BottomSheetBehavior.STATE_EXPANDED

    }


}