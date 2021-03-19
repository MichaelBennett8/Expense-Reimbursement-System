package dev.bennett.entities;

import javax.persistence.*;

@Entity
@Table(name= "expense")
public class Expense {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseID;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Column(name = "date_submitted")
    private long dateSubmitted;

    @Column(name = "date_approved_denied")
    private long dateApprovedDenied;

    @Column(name = "employee_id")
    private int employeeID;

    @Column(name = "reason_approved_denied")
    private String reasonApprovedOrDenied;

    public Expense() {
    }

    public Expense(int expenseID, double amount, String reason, ApprovalStatus status, long dateSubmitted, long dateApprovedDenied, int employeeID) {
        this.expenseID = expenseID;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateApprovedDenied = dateApprovedDenied;
        this.employeeID = employeeID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public long getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(long dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public long getDateApprovedDenied() {
        return dateApprovedDenied;
    }

    public void setDateApprovedDenied(long dateApprovedDenied) {
        this.dateApprovedDenied = dateApprovedDenied;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getReasonApprovedOrDenied() {
        return reasonApprovedOrDenied;
    }

    public void setReasonApprovedOrDenied(String reasonApprovedOrDenied) {
        this.reasonApprovedOrDenied = reasonApprovedOrDenied;
    }
}
