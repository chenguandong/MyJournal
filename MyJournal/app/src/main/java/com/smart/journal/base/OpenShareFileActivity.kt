package com.smart.journal.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smart.journal.R
import com.smart.journal.tools.file.MJFileTools


class OpenShareFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_share_file)

        val intent = intent
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("application/zip" == type) {

                // Gets the first item from the clipboard data
                val item = intent.clipData.getItemAt(0)

                // Tries to get the item's contents as a URI pointing to a note
                val uri = item.getUri()

                if (uri!=null){
                   var fileInfo   =  MJFileTools.saveJournalFile2Local(this,uri,true)
                    var ss = ""
                }
            }
        }

    }
}
