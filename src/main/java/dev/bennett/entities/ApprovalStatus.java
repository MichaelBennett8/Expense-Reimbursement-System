package dev.bennett.entities;

public enum ApprovalStatus {
    APPROVED,
    DENIED,
    PENDING;

    public static ApprovalStatus fromInteger(int x){
        switch (x){
            case 0:
                return APPROVED;
            case 1:
                return DENIED;
            default:
                return PENDING;
        }
    }
}
