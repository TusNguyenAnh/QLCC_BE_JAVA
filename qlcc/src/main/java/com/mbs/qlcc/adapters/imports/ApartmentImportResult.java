package com.mbs.qlcc.adapters.imports;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentImportResult {
    private boolean success;
    private String message;
    private List<String> messageErrors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalRows;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorRows;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApartmentRowError> errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApartmentRowError {
        private int row;
        private List<String> errors;
        private ApartmentImportRow data;
    }

    // Factory methods
    public static ApartmentImportResult success(boolean status, String message) {
        return new ApartmentImportResult(status, message, null, null, null, null);
    }

    public static ApartmentImportResult validationError(int totalRows, int errorRows, List<ApartmentRowError> errors) {
        return new ApartmentImportResult(
                false,
                "Invalid data. Please double-check the faulty lines.",
                null,
                totalRows,
                errorRows,
                errors
        );
    }

    public static ApartmentImportResult buildingNotFound(List<String> errors) {
        ApartmentImportResult result = new ApartmentImportResult();
        result.setSuccess(false);
        result.setMessage("Building not found. Please ensure the building exists before importing apartments.");
        result.setErrors(null);
        return result;
    }

    public static ApartmentImportResult databaseError(String message) {
        return new ApartmentImportResult(
                false,
                "Error saving data to the database: " + message,
                null,
                null,
                null,
                null
        );
    }

    public static ApartmentImportResult errorExisted(int totalRows, int errorRows, List<String> existed) {
        return new ApartmentImportResult(
                false,
                "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại các dòng bị lỗi.",
                existed,
                totalRows,
                errorRows,
                null
        );
    }
}
