package com.smart.journal.tools.file

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ZipUtils
import com.smart.journal.app.MyApp
import com.smart.journal.bean.PhotoFileInfo
import com.yanzhenjie.album.AlbumFile
import java.io.File
import java.io.IOException
import java.util.*

/**
 * @author guandongchen
 * @date 2018/11/19
 */
object MJFileTools {
    //日记图片存放路径
    var JOURNALDIR = MyApp.instance!!.filesDir.absolutePath + File.separator + "/MyJournal"
    //日记图片导出导入路径
    var JOURNALDIR_EXPORT = File(Environment.getExternalStorageDirectory().absolutePath + "/MyJournal/export").absolutePath
    //备份导出路径
    var JOURNALDIR_BACK_UP_EXPORT = File(Environment.getExternalStorageDirectory().absolutePath + "/JournalBackUp").absolutePath
    fun saveJournalImageFile2Local(albumFile: AlbumFile): String {
        val fileInfo = getPhotoInfoFromAlbum(albumFile)
        val fileName = UUID.randomUUID().toString() + "." + fileInfo.fileType
        val file = File(JOURNALDIR, fileName)
        FileUtils.copyFile(File(fileInfo.filePath), file) { false }
        return file.absolutePath
    }

    fun saveJournalFile2Local(context: Context?, fileUri: Uri, unzip: Boolean): String {
        //获取外部分享文件SD 路径
        val shareFilePath = MJFileUtils.fileProviderPath(fileUri)
        val fileName = UUID.randomUUID().toString() + "." + MJFileUtils.getFileEndType(fileUri.toString())
        val file = File(JOURNALDIR_EXPORT, fileName)
        FileUtils.copyFile(File(shareFilePath), file) { false }
        if (unzip) {
            try {
                val exportFile = File(JOURNALDIR_EXPORT + "/" + fileName.substring(0, fileName.indexOf(".")))
                if (!exportFile.exists()) {
                    exportFile.mkdir()
                }
                ZipUtils.unzipFile(file, exportFile)
                file.delete()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file.absolutePath
    }

    fun createJournalPath() {
        val journalPath = File(JOURNALDIR)
        val journalExpPath = File(JOURNALDIR_EXPORT)
        if (!journalPath.exists()) {
            journalPath.mkdir()
        }
        if (!journalExpPath.exists()) {
            journalExpPath.mkdir()
        }
    }

    /**
     * 从URI获取本地路径
     *
     * @return
     */
    fun getAbsoluteFilePath(activity: Context?, contentUri: Uri): PhotoFileInfo {

        //如果是对媒体文件，在android开机的时候回去扫描，然后把路径添加到数据库中。
        //由打印的contentUri可以看到：2种结构。正常的是：content://那么这种就要去数据库读取path。
        //另外一种是Uri是 file:///那么这种是 Uri.fromFile(File file);得到的
        val fileInfo = PhotoFileInfo()
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var urlpath: String?
        val loader = CursorLoader(activity!!, contentUri, projection, null, null, null)
        val cursor = loader.loadInBackground()
        try {
            val column_index = cursor!!.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            urlpath = cursor.getString(column_index)
            fileInfo.fileName = urlpath.substring(urlpath.lastIndexOf("/") + 1)
            fileInfo.fileType = urlpath.substring(urlpath.lastIndexOf(".") + 1)
            fileInfo.filePath = urlpath
            //如果是正常的查询到数据库。然后返回结构
            return fileInfo
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: handle exception
        } finally {
            cursor?.close()
        }

        //如果是文件。Uri.fromFile(File file)生成的uri。那么下面这个方法可以得到结果
        urlpath = contentUri.path
        fileInfo.fileName = urlpath!!.substring(urlpath.lastIndexOf("/") + 1)
        fileInfo.fileType = urlpath.substring(urlpath.lastIndexOf(".") + 1)
        fileInfo.filePath = urlpath
        return fileInfo
    }

    fun getPhotoInfoFromAlbum(albumFile: AlbumFile): PhotoFileInfo {
        val fileInfo = PhotoFileInfo()
        val urlpath = albumFile.path
        fileInfo.fileName = urlpath.substring(urlpath.lastIndexOf("/") + 1)
        fileInfo.fileType = urlpath.substring(urlpath.lastIndexOf(".") + 1)
        fileInfo.mimeType = albumFile.mimeType
        fileInfo.addDate = albumFile.addDate
        fileInfo.latitude = albumFile.latitude
        fileInfo.longitude = albumFile.longitude
        fileInfo.filePath = urlpath
        return fileInfo
    }

    /**
     * 备份所有日记到SDCard
     */
    fun backUpExportJournal(){
        FileUtils.copyDir(JOURNALDIR,JOURNALDIR_BACK_UP_EXPORT)
    }
}