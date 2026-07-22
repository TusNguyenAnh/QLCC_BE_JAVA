package com.mbs.qlcc.usecases.response.Expense;

public interface IExpenseFilterResponse {
    String getId();

    String getTaskId();

    String getBuildingId();

    String getCategory();

    String getTitle();

    String getOriginalAmount();

    String getAmountPaid();

    String getVendor();

    String getStatus();

    String getDescription();

    String getCreatedBy();

    String getApprovedBy();

    Integer getApproved();

    String getApprovedAt();
}
