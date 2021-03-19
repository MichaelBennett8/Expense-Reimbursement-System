package dev.bennett.entities;

public class ExpenseStatusUpdate {

    private String reason;
    private ApprovalStatus status;

    public ExpenseStatusUpdate() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = ApprovalStatus.valueOf(status);
    }
}
