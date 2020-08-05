package com.smart.journal.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.smart.journal.MainActivity
import com.smart.journal.R
import com.smart.journal.base.BaseActivity

class WelcomeActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initView() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)


    }
}
