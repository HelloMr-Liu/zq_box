package zq.bim.entity;

import java.util.Date;

public class SystemOperationLog {
    private Long logId;

    private String logType;

    private Long operationUserId;

    private Date operationTime;

    private String operationPath;

    private Date createTime;

    private String operationAddress;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public Long getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(Long operationUserId) {
        this.operationUserId = operationUserId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath == null ? null : operationPath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperationAddress() {
        return operationAddress;
    }

    public void setOperationAddress(String operationAddress) {
        this.operationAddress = operationAddress == null ? null : operationAddress.trim();
    }
}