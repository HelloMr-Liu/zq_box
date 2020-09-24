package zq.bim.entity;

import java.util.Date;

public class FileData {
    private String fileMd5;

    private String isDeleted;

    private String isFinished;

    private Long fileSize;

    private String filePath;

    private Long writeByte;

    private Date createTime;

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5 == null ? null : fileMd5.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished == null ? null : isFinished.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Long getWriteByte() {
        return writeByte;
    }

    public void setWriteByte(Long writeByte) {
        this.writeByte = writeByte;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public FileData(String fileMd5, String isDeleted, String isFinished, Long fileSize, String filePath, Long writeByte,
			Date createTime) {
		super();
		this.fileMd5 = fileMd5;
		this.isDeleted = isDeleted;
		this.isFinished = isFinished;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.writeByte = writeByte;
		this.createTime = createTime;
	}

	public FileData() {
		super();
		// TODO Auto-generated constructor stub
	}
}