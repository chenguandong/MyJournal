package com.smart.weather.tools.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.blankj.utilcode.util.FileUtils;
import com.smart.weather.bean.PhotoFileInfo;

import java.io.File;
import java.util.UUID;

/**
 * @author guandongchen
 * @date 2018/11/19
 */
public class MJFileTools {

    public static String journalDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyJournal").getAbsolutePath();

    static {
        createJournalPath();
    }

    /**
     * 存储图片到本地文件件
     * @param context
     * @param photoUri
     * @return  本地文件路径
     */
    public static String saveJournalFile2Local(Context context, Uri photoUri) {

        PhotoFileInfo fileInfo = getPathFromUri(context, photoUri);
        String fileName = UUID.randomUUID()+"."+fileInfo.getFileType().split("/")[1];
        File file = new File(journalDir,fileName);
        FileUtils.copyFile(new File(fileInfo.getFilePath()), file, () -> false);

        return file.getAbsolutePath();
    }

    public static void createJournalPath() {
        File journalPath = new File(journalDir);
        if (!journalPath.exists()) {
            journalPath.mkdir();
        }
    }

    public static PhotoFileInfo getPathFromUri(Context context, Uri uri) {

        PhotoFileInfo photoFileInfo = new PhotoFileInfo();

        String[] projection = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        int DISPLAY_NAME = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        int DATE_ADDED = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
        int MIME_TYPE = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        String s_DISPLAY_NAME = cursor.getString(DISPLAY_NAME);
        String s_DATE_ADDED = cursor.getString(DATE_ADDED);
        String s_MIME_TYPE = cursor.getString(MIME_TYPE);

        photoFileInfo.setFilePath(s);
        photoFileInfo.setFileName(s_DISPLAY_NAME);
        photoFileInfo.setFileType(s_MIME_TYPE);
        cursor.close();
        return photoFileInfo;
    }

}
