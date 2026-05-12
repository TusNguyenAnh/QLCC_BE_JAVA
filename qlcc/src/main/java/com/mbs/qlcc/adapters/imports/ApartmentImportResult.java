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
    public static ApartmentImportResult success(String message) {
        return new ApartmentImportResult(true, message, null, null, null);
    }

    public static ApartmentImportResult validationError(int totalRows, int errorRows, List<ApartmentRowError> errors) {
        return new ApartmentImportResult(
                false,
                "Invalid data. Please double-check the faulty lines.",
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
                null
        );
    }
}
