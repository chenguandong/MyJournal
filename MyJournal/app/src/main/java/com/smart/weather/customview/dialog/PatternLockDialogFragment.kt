package com.smart.weather.customview.dialog

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.*
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.smart.weather.R
import com.smart.weather.tools.user.UserTools
import kotlinx.android.synthetic.main.fragment_pattern_lock_dialog.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PatternLockDialogFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PatternLockDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PatternLockDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var passcode:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        isCancelable = true
        val style = DialogFragment.STYLE_NO_TITLE
        val theme = android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen
        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pattern_lock_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lockView.addPatternLockListener(object: PatternLockViewListener {
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                var pass =  PatternLockUtils.patternToString(lockView, pattern)



                if (pass.length<4){
                    infoTextView.text = "密码过短,请重新绘制"
                    errorLock()
                    clearLockCode()
                }else{
                    //说明已经设置了密码 需要解锁
                    if (!TextUtils.isEmpty(UserTools.getLockCode())){

                        if (pass==UserTools.getLockCode()){
                            dismiss()
                        }else{
                            infoTextView.text = "密码错误,请重新绘制"
                        }
                        return
                    }

                    if (TextUtils.isEmpty(passcode)){
                        infoTextView.text = "再次绘制确认密码"
                        passcode = pass
                        clearLockCode()
                    }else{
                        if (pass == passcode){
                            UserTools.saveLockCode(pass)
                            dismiss()
                        }else{
                            infoTextView.text = "两次输入密码不一致,请重新输入"
                            errorLock()
                        }
                    }
                }


            }

            override fun onCleared() {
            }

            override fun onStarted() {
            }

            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {
            }

        })
    }

    private fun errorLock(){
        lockView.setViewMode(PatternLockView.PatternViewMode.WRONG)
    }

    private fun clearLockCode(){
        Handler().postDelayed({
            lockView.clearPattern()
        },2000)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val win = dialog.window
        dialog.setCanceledOnTouchOutside(true)
        // 显示在底部
        win!!.setGravity(Gravity.CENTER)
        val lp = win.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        win.attributes = lp
        isCancelable = false
        super.onActivityCreated(savedInstanceState)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
      /*  if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
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
         * @return A new instance of fragment PatternLockDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PatternLockDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
