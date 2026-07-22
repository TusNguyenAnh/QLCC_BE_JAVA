package com.mbs.qlcc.adapters.imports;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mbs.qlcc.usecases.input.IResidentInputBoundary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidenImportResult {
    private boolean success;
    private String message;
    private List<String> messageErrors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalRows;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorRows;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ResidenImportResult.ResidentRowError> errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResidentRowError {
        private int row;
        private List<String> errors;
        private ResidentImportRow data;
    }

    public static ResidenImportResult success(boolean status,String message) {
        return new ResidenImportResult(status, message, null, null, null, null);
    }

    public static ResidenImportResult validationError(int totalRows, int errorRows, List<ResidenImportResult.ResidentRowError> errors) {
        return new ResidenImportResult(
                false,
                "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại các dòng bị lỗi.",
                null,
                totalRows,
                errorRows,
                errors
        );
    }

    public static ResidenImportResult errorExisted(int totalRows, int errorRows, List<String> existed) {
        return new ResidenImportResult(
                false,
                "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại các dòng bị lỗi.",
                existed,
                totalRows,
                errorRows,
                null
        );
    }

    public static ResidenImportResult residentNotFound(List<String> errors) {
        ResidenImportResult result = new ResidenImportResult();
        result.setSuccess(false);
        result.setMessage("Resident không tồn tại");
        result.setErrors(null);
        return result;
    }

    public static ResidenImportResult databaseError(String message) {
        return new ResidenImportResult(
                false,
                "Lỗi khi lưu dữ liệu vào database: " + message,
                null,
                null,
                null,
                null
        );
    }
}
