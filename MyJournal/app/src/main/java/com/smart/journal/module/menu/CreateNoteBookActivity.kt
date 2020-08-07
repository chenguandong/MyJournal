package com.smart.journal.module.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.smart.journal.R
import com.smart.journal.app.MyApp
import com.smart.journal.base.BaseActivity
import com.smart.journal.db.entity.NoteBookDBBean
import kotlinx.android.synthetic.main.activity_create_note_book.*
import java.lang.reflect.Array
import java.util.*


class CreateNoteBookActivity : BaseActivity() {
    var color = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note_book)
        init()
    }

    override fun initView() {
        initSimpleToolbar(resources.getString(R.string.create_new_journal_book))
        colorTextView.setOnClickListener {
            ColorPickerDialogBuilder
                    .with(context)
                    .setTitle(R.string.choose_color)
                    .initialColors(intArrayOf(R.color.logo_red,R.color.logo_yellow,R.color.logo_blue,R.color.logo_green))
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setOnColorSelectedListener {

                    }
                    .setPositiveButton(R.string.ok) { dialog, selectedColor, allColors ->
                        this.color = selectedColor
                        toolbar.setBackgroundColor(selectedColor)
                    }
                    .setNegativeButton(R.string.album_cancel) { dialog, which -> }
                    .build()
                    .show()
        }
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
                        MyApp.database!!.mNoteBookDao().saveNoteBook(NoteBookDBBean(editInputEditText.text.toString(),color))
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
