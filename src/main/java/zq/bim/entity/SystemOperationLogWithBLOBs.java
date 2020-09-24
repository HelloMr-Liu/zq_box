package zq.bim.entity;

public class SystemOperationLogWithBLOBs extends SystemOperationLog {
    private String operationContent;

    private String operationArgs;

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent == null ? null : operationContent.trim();
    }

    public String getOperationArgs() {
        return operationArgs;
    }

    public void setOperationArgs(String operationArgs) {
        this.operationArgs = operationArgs == null ? null : operationArgs.trim();
    }
}