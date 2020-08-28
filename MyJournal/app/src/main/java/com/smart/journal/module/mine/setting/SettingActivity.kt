package com.smart.journal.module.mine.setting

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import com.smart.journal.bean.PhotoFileInfo
import com.smart.journal.customview.dialog.PatternLockDialogFragment
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.tools.file.MJFileTools
import com.smart.journal.tools.user.UserTools
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.File


class SettingActivity : BaseActivity() {
    override fun initView() {
        initSimpleToolbar("设置")
        switchView.isChecked = !TextUtils.isEmpty(UserTools.lockCode)

        switchView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                PatternLockDialogFragment.newInstance("", "").show(supportFragmentManager, "")
            } else {
                PatternLockDialogFragment.newInstance(PatternLockDialogFragment.param1closelock, "").show(supportFragmentManager, "")

            }
        }
        val exportPath = MJFileTools.JOURNALDIR_BACK_UP_EXPORT + File.separator + "back.zip"
        exportLayout.setOnClickListener {
            MJFileTools.backUpExportJournal()
            AlertDialog.Builder(this).setTitle(resources.getString(R.string.export_success)+ exportPath)
                    .setIcon(R.drawable.ic_search)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->

                    })/*.setNegativeButton(R.string.send, DialogInterface.OnClickListener { dialogInterface, i ->
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        //传输文件 采用流的方式
                        var uri = FileProvider.getUriForFile(this, "com.smart.journal.fileProvider", File(exportPath))
                        var photoFileInfo = MJFileTools.getAbsoluteFilePath(this,uri)
                         shareIntent.setDataAndType(uri, "application/x-zip-compressed")
                        startActivity(shareIntent)


                    })*/.show()

        }

        inportLayout.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 666)
            } catch (ex: ActivityNotFoundException) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            666 -> if (resultCode == Activity.RESULT_OK) {
                // Get the Uri of the selected file
                val uri: Uri? = data!!.data

                val path: PhotoFileInfo = MJFileTools.getAbsoluteFilePath(this, uri!!)
                Logger.d(path.filePath)
                Logger.d(path.fileName)
                Logger.d(path.fileType)

                MJFileTools.importExportJournal()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
