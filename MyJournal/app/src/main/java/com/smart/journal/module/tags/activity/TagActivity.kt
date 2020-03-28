package com.smart.journal.module.tags.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smart.journal.R
import com.smart.journal.module.tags.fragments.TagFragment

class TagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TagFragment.newInstance())
                    .commitNow()
        }
    }
}
