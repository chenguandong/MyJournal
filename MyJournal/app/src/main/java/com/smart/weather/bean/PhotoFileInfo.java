package com.smart.weather.bean;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/11/21
 */
public class PhotoFileInfo implements Serializable {

    private String fileName;
    private String filePath;
    private String fileType;

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
