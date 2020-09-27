package com.cjj.qlemusic.admin.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class OssCallback implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "文件大小")
    private String size;

    @ApiModelProperty(value = "文件的mimeType")
    private String mimeType;

    @ApiModelProperty(value = "文件的宽")
    private String width;

    @ApiModelProperty(value = "文件的高")
    private String height;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
