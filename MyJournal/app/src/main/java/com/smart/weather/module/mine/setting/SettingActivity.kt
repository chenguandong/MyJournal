package com.smart.weather.module.mine.setting

import android.os.Bundle
import android.text.TextUtils
import com.smart.weather.R
import com.smart.weather.base.BaseActivity
import com.smart.weather.customview.dialog.PatternLockDialogFragment
import com.smart.weather.tools.user.UserTools
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun initView() {
        initSimpleToolbar("设置")
        switchView.isChecked = !TextUtils.isEmpty(UserTools.getLockCode())

        switchView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                PatternLockDialogFragment.newInstance("","").show(supportFragmentManager,"")
            }
        }
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()
    }
}
