package com.ebase.core.fastdfs.dto;

import com.ebase.core.entity.BaseEntity;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class FileDTO extends BaseEntity {
    private static final long serialVersionUID = -1L;

    private byte[] contents;

    private String fileId;

    private String fileName;

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
