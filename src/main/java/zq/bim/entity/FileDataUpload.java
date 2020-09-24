package zq.bim.entity;

import java.util.Date;

public class FileDataUpload {
    private String uploadId;

    private String fileMd5;

    private String isFinished;

    private Long byteBegin;

    private Long byteEnd;

    private Long byteCount;

    private Date updateTime;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId == null ? null : uploadId.trim();
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5 == null ? null : fileMd5.trim();
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished == null ? null : isFinished.trim();
    }

    public Long getByteBegin() {
        return byteBegin;
    }

    public void setByteBegin(Long byteBegin) {
        this.byteBegin = byteBegin;
    }

    public Long getByteEnd() {
        return byteEnd;
    }

    public void setByteEnd(Long byteEnd) {
        this.byteEnd = byteEnd;
    }

    public Long getByteCount() {
        return byteCount;
    }

    public void setByteCount(Long byteCount) {
        this.byteCount = byteCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public FileDataUpload(String uploadId, String fileMd5, String isFinished, Long byteBegin, Long byteEnd,
			Long byteCount, Date updateTime) {
		super();
		this.uploadId = uploadId;
		this.fileMd5 = fileMd5;
		this.isFinished = isFinished;
		this.byteBegin = byteBegin;
		this.byteEnd = byteEnd;
		this.byteCount = byteCount;
		this.updateTime = updateTime;
	}

	public FileDataUpload() {
		super();
		// TODO Auto-generated constructor stub
	}
}