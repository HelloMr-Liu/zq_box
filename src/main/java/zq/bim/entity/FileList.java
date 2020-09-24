package zq.bim.entity;

import java.util.Date;

public class FileList {
    private String fileId;

    private String projectId;

    private String fileName;

    private Long fileSize;

    private String fileType;

    private String fileMd5;

    private String createUserId;

    private Date createTime;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5 == null ? null : fileMd5.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public FileList(String fileId, String projectId, String fileName, Long fileSize, String fileType, String fileMd5,
			String createUserId, Date createTime) {
		super();
		this.fileId = fileId;
		this.projectId = projectId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.fileMd5 = fileMd5;
		this.createUserId = createUserId;
		this.createTime = createTime;
	}
}