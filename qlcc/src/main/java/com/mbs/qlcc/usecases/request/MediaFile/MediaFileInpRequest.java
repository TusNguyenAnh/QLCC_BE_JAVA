package com.mbs.qlcc.usecases.request.MediaFile;

import java.math.BigInteger;
import java.util.List;

public class MediaFileInpRequest {
    private String ownerType;
    private String ownerId;
    private String fileType;
    private String fileName;
    private String fileUrl;
    private String mimeType;
    private Long size;
    private byte[] data;

    public MediaFileInpRequest() {
    }

    public MediaFileInpRequest(String ownerType, String ownerId, String fileType, String fileName, String fileUrl, String mimeType, Long size, byte[] data) {
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.fileType = fileType;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.mimeType = mimeType;
        this.size = size;
        this.data = data;
    }


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
