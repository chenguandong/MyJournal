package com.smart.weather.tools.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.smart.weather.bean.PhotoFileInfo;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author guandongchen
 * @date 2018/11/19
 */
public class MJFileTools {
    //日记图片存放路径
    public static String JOURNALDIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyJournal").getAbsolutePath();
    //日记图片导出导入路径
    public static String JOURNALDIR_EXPORT = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyJournal/export").getAbsolutePath();



    public static String saveJournalImageFile2Local(AlbumFile albumFile) {
        PhotoFileInfo fileInfo = getPhotoInfoFromAlbum(albumFile);
        String fileName = UUID.randomUUID()+"."+fileInfo.getFileType();
        File file = new File(JOURNALDIR,fileName);
        FileUtils.copyFile(new File(fileInfo.getFilePath()), file, () -> false);
        return file.getAbsolutePath();
    }

    public static String saveJournalFile2Local(Context context, Uri fileUri,boolean unzip) {

        PhotoFileInfo fileInfo = getAbsoluteFilePath(context, fileUri);
        String fileName = UUID.randomUUID()+"."+fileInfo.getFileType();
        File file = new File(JOURNALDIR_EXPORT,fileName);
        FileUtils.copyFile(new File(fileInfo.getFilePath()), file, () -> false);
        if (unzip){
            try {
                ZipUtils.unzipFile(file, new File(JOURNALDIR_EXPORT));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }


    public static void createJournalPath() {
        File journalPath = new File(JOURNALDIR);
        File journalExpPath = new File(JOURNALDIR_EXPORT);
        if (!journalPath.exists()) {
            journalPath.mkdir();
        }
        if (!journalExpPath.exists()) {
            journalExpPath.mkdir();
        }
    }

    /**
     * 从URI获取本地路径
     *
     * @return
     */
    public static  PhotoFileInfo getAbsoluteFilePath(Context activity, Uri contentUri) {

        //如果是对媒体文件，在android开机的时候回去扫描，然后把路径添加到数据库中。
        //由打印的contentUri可以看到：2种结构。正常的是：content://那么这种就要去数据库读取path。
        //另外一种是Uri是 file:///那么这种是 Uri.fromFile(File file);得到的
        PhotoFileInfo fileInfo = new PhotoFileInfo();

        String[] projection = { MediaStore.Images.Media.DATA};
        String urlpath;
        CursorLoader loader = new CursorLoader(activity,contentUri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        try
        {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            urlpath =cursor.getString(column_index);
            fileInfo.setFileName(urlpath.substring(urlpath.lastIndexOf("/")+1,urlpath.length()));
            fileInfo.setFileType(urlpath.substring(urlpath.lastIndexOf(".")+1,urlpath.length()));
            fileInfo.setFilePath(urlpath);
            //如果是正常的查询到数据库。然后返回结构
            return fileInfo;
        }
        catch (Exception e)
        {

            e.printStackTrace();
            // TODO: handle exception
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }

        //如果是文件。Uri.fromFile(File file)生成的uri。那么下面这个方法可以得到结果
        urlpath = contentUri.getPath();
        fileInfo.setFileName(urlpath.substring(urlpath.lastIndexOf("/")+1,urlpath.length()));
        fileInfo.setFileType(urlpath.substring(urlpath.lastIndexOf(".")+1,urlpath.length()));

        fileInfo.setFilePath(urlpath);
        return fileInfo;
    }

    public static PhotoFileInfo getPhotoInfoFromAlbum(AlbumFile albumFile){
        PhotoFileInfo fileInfo = new PhotoFileInfo();
        String urlpath = albumFile.getPath();
        fileInfo.setFileName(urlpath.substring(urlpath.lastIndexOf("/")+1,urlpath.length()));
        fileInfo.setFileType(urlpath.substring(urlpath.lastIndexOf(".")+1,urlpath.length()));
        fileInfo.setMimeType(albumFile.getMimeType());
        fileInfo.setAddDate(albumFile.getAddDate());
        fileInfo.setLatitude(albumFile.getLatitude());
        fileInfo.setLongitude(albumFile.getLongitude());
        fileInfo.setFilePath(urlpath);
        return fileInfo;
    }

}
