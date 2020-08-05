package com.smart.journal.bean;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/11/21
 */
public class PhotoFileInfo implements Serializable {
    /**
     * {
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "addDate": 1544966724,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "bucketName": "Camera",
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "checked": true,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "disable": false,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "duration": 0,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "latitude": 34.798134,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "longitude": 113.56004,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "mediaType": 1,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "mimeType": "image\/jpeg",
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "path":
     * "\/storage\/emulated\/0\/DCIM\/Camera\/IMG_20181216_212524.jpg",
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "size": 4692895,
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │     "thumbPath":
     * "\/storage\/emulated\/0\/AlbumCache\/3edddcf89677aca3ae0afe1ce215f4b5.album"
     * 2018-12-18 22:11:29.020 20076-20076/com.smart.weather D/com.smart.weather: │   }
     */
    private String fileName;

    private String filePath;

    private String fileType;

    private String mimeType;

    private float latitude;

    private float longitude;

    private long addDate;

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
