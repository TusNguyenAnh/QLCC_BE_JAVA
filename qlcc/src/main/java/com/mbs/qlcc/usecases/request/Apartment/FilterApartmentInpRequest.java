package com.mbs.qlcc.usecases.request.Apartment;

import java.time.LocalDateTime;

public class FilterApartmentInpRequest {
    private String keyword;
    private LocalDateTime timeRequestStart;
    private LocalDateTime timeRequestEnd;
    private String order;
    private int pageNumber;
    private int pageSize;

    public FilterApartmentInpRequest() {
    }

    public FilterApartmentInpRequest(String keyword, LocalDateTime timeRequestStart,
                                     LocalDateTime timeRequestEnd, String order,
                                     int pageNumber, int pageSize) {
        this.keyword = keyword;
        this.timeRequestStart = timeRequestStart;
        this.timeRequestEnd = timeRequestEnd;
        this.order = order;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LocalDateTime getTimeRequestStart() {
        return timeRequestStart;
    }

    public void setTimeRequestStart(LocalDateTime timeRequestStart) {
        this.timeRequestStart = timeRequestStart;
    }

    public LocalDateTime getTimeRequestEnd() {
        return timeRequestEnd;
    }

    public void setTimeRequestEnd(LocalDateTime timeRequestEnd) {
        this.timeRequestEnd = timeRequestEnd;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
