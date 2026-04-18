package com.mbs.qlcc.usecases.response.MediaFile;

import java.math.BigInteger;

public class MediaFileResponse {
    private String id;
    private String ownerType;
    private String ownerId;
    private String fileType;
    private String fileName;
    private String fileUrl;
    private String mimeType;
    private BigInteger size;

    public MediaFileResponse() {
    }

    public MediaFileResponse(String id, String ownerType, String ownerId, String fileType, String fileName, String fileUrl, String mimeType, BigInteger size) {
        this.id = id;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.fileType = fileType;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.mimeType = mimeType;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }
}
