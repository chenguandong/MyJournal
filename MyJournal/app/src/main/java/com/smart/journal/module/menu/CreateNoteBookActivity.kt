package com.smart.journal.module.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.blankj.utilcode.util.ToastUtils
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.base.BaseActivity
import com.smart.journal.db.entity.NoteBookDBBean
import kotlinx.android.synthetic.main.activity_create_note_book.*

class CreateNoteBookActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note_book)
        init()
    }

    override fun initView() {
        initSimpleToolbar("创建新的日记本")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu!!.findItem(R.id.toolbar_right_action).title = "保存"
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.toolbar_right_action -> {

                var newBookName = editInputEditText.text.toString()
                if (TextUtils.isEmpty(newBookName)) {
                    ToastUtils.showShort("日记名称不能为空")
                    return false
                } else {
                    if (MyApp.database!!.mNoteBookDao().checkExists(newBookName)) {
                        ToastUtils.showShort("已存在名称日记本")
                        return false
                    } else {
                        MyApp.database!!.mNoteBookDao().saveNoteBook(NoteBookDBBean(editInputEditText.text.toString()))
                    }
                }

                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
    }
}
