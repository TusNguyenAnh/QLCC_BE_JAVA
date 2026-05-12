package com.mbs.qlcc.adapters.imports;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptResImportResult {
    private boolean success;
    private String message;
    private List<String> messageErrors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalRows;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorRows;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AptResImportResult.AptResRowError> errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AptResRowError {
        private int row;
        private List<String> errors;
        private AptResImportRow data;
    }

    public static AptResImportResult success(String message) {
        return new AptResImportResult(true, message, null, null, null, null);
    }

    public static AptResImportResult validationError(int totalRows, int errorRows, List<AptResImportResult.AptResRowError> errors) {
        return new AptResImportResult(
                false,
                "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại các dòng bị lỗi.",
                null,
                totalRows,
                errorRows,
                errors
        );
    }

    public static AptResImportResult errorExisted(int totalRows, int errorRows, List<String> existed) {
        return new AptResImportResult(
                false,
                "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại các dòng bị lỗi.",
                existed,
                totalRows,
                errorRows,
                null
        );
    }

    public static AptResImportResult residentNotFound(List<String> errors) {
        AptResImportResult result = new AptResImportResult();
        result.setSuccess(false);
        result.setMessage("Resident không tồn tại");
        result.setErrors(null);
        return result;
    }

    public static AptResImportResult databaseError(String message) {
        return new AptResImportResult(
                false,
                "Lỗi khi lưu dữ liệu vào database: " + message,
                null,
                null,
                null,
                null
        );
    }
}
