package com.mbs.qlcc.service;

import com.mbs.qlcc.imports.ApartmentImportResult;
import com.mbs.qlcc.imports.ApartmentImportRow;
import com.mbs.qlcc.dto.request.Apartment.CreateApartmentRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApartmentImportService {

    private final IApartmentService apartmentService;

    public ApartmentImportResult importFromExcel(MultipartFile file, String complexId) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Parse data rows (starting from row 5 - index 4)
            List<ApartmentImportRow> rows = new ArrayList<>();
            List<ApartmentImportResult.ApartmentRowError> rowErrors = new ArrayList<>();
            int totalRows = 0;

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) break;

                totalRows++;
                ApartmentImportRow importRow = parseRow(row, i + 1);

                // Validate row
                List<String> errors = validateRow(importRow);
                if (!errors.isEmpty()) {
                    importRow.setErrors(errors);
                    ApartmentImportResult.ApartmentRowError rowError = new ApartmentImportResult.ApartmentRowError(
                            i + 1,
                            errors,
                            importRow
                    );
                    rowErrors.add(rowError);
                } else {
                    rows.add(importRow);
                }
            }

            workbook.close();

            // If there are validation errors, return them
            if (!rowErrors.isEmpty()) {
                return ApartmentImportResult.validationError(totalRows, rowErrors.size(), rowErrors);
            }

            // Import valid rows
            List<CreateApartmentRequest> createApartmentRequests = new ArrayList<>();
            for (ApartmentImportRow importRow : rows) {
                CreateApartmentRequest request = new CreateApartmentRequest(
                        importRow.getBuildingId(),
                        importRow.getFloor(),
                        importRow.getAptNumber(),
                        importRow.getGrossArea(),
                        importRow.getCoefficient(),
                        importRow.getAptType(),
                        importRow.getDescription()
                );

                createApartmentRequests.add(request);
            }

            String result = apartmentService.importExcel(createApartmentRequests, complexId);
            if (!Objects.equals(result, "DONE"))
                return ApartmentImportResult.success(false, result);
            return ApartmentImportResult.success(true, "Import successful!");
        } catch (Exception e) {
            return ApartmentImportResult.databaseError(e.getMessage());
        }
    }


    private ApartmentImportRow parseRow(Row row, int rowNumber) {
        ApartmentImportRow importRow = new ApartmentImportRow();

        try {
            // Column A: Building ID (expecting building name or ID)
            String buildingId = getStringCellValue(row, 1);
            importRow.setBuildingId(buildingId);

            // Column B: Apartment Number
            String aptNumber = getStringCellValue(row, 2);
            importRow.setAptNumber(aptNumber);

            // Column C: Floor
            int floor = getIntCellValue(row, 3);
            importRow.setFloor(floor);

            // Column D: Gross Area
            BigDecimal grossArea = getDoubleCellValue(row, 4);
            importRow.setGrossArea(grossArea);

            BigDecimal coefficient = getDoubleCellValue(row, 5);
            importRow.setCoefficient(coefficient);

            // Column E: Apartment Type
            String aptType = getStringCellValue(row, 6);
            importRow.setAptType(aptType);

            // Column F: Description (optional)
            String description = getStringCellValue(row, 7);
            importRow.setDescription(description);

        } catch (Exception e) {
            importRow.setErrors(List.of("Lỗi khi đọc dòng: " + e.getMessage()));
        }

        return importRow;
    }


    private List<String> validateRow(ApartmentImportRow row) {
        List<String> errors = new ArrayList<>();

        if (row.getBuildingId() == null || row.getBuildingId().trim().isEmpty()) {
            errors.add("Tòa nhà không được để trống");
        }

        if (row.getAptNumber() == null || row.getAptNumber().trim().isEmpty()) {
            errors.add("Số căn hộ không được để trống");
        }

        if (row.getFloor() <= 0) {
            errors.add("Số tầng phải lớn hơn 0");
        }

        if (row.getGrossArea() == null || row.getGrossArea().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Diện tích phải lớn hơn 0");
        }

        if (row.getCoefficient() == null || row.getCoefficient().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Hệ số quy đổi phải lớn hơn 0");
        }

        if (row.getAptType() == null || row.getAptType().trim().isEmpty()) {
            errors.add("Loại căn hộ không được để trống");
        }

        return errors;
    }

    // Helper methods for cell reading
    private String getStringCellValue(Row row, int columnIndex) {
        try {
            return row.getCell(columnIndex).getStringCellValue();
        } catch (Exception e) {
            return "";
        }
    }

    private int getIntCellValue(Row row, int columnIndex) {
        try {
            return (int) row.getCell(columnIndex).getNumericCellValue();
        } catch (Exception e) {
            return 0;
        }
    }

    private BigDecimal getDoubleCellValue(Row row, int columnIndex) {
        try {
            return BigDecimal.valueOf(row.getCell(columnIndex).getNumericCellValue());
        } catch (Exception e) {
            return BigDecimal.valueOf(0.0);
        }
    }
}
