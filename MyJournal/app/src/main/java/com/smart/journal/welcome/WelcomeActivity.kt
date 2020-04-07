package com.smart.journal.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.smart.journal.MainActivity
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.smart.journal.customview.dialog.PatternLockDialogFragment
import com.smart.journal.tools.user.UserTools

class WelcomeActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initView() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2000)


    }
}
