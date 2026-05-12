package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.imports.AptResImportResult;
import com.mbs.qlcc.adapters.imports.AptResImportRow;
import com.mbs.qlcc.usecases.input.IResidentInputBoundary;
import com.mbs.qlcc.usecases.request.Resident.ImportAptResidentInpRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AptResImportService {
    private final IResidentInputBoundary residentInputBoundary;

    public AptResImportResult importFromExcel(MultipartFile file, String complexId) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Parse data rows (starting from row 5 - index 4)
            List<AptResImportRow> rows = new ArrayList<>();
            List<AptResImportResult.AptResRowError> rowErrors = new ArrayList<>();
            int totalRows = 0;

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) break;

                totalRows++;
                AptResImportRow importRow = parseRow(row, i + 1);

                // Validate row
                List<String> errors = validateRow(importRow);
                if (!errors.isEmpty()) {
                    importRow.setErrors(errors);
                    AptResImportResult.AptResRowError rowError = new AptResImportResult.AptResRowError(
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
                return AptResImportResult.validationError(totalRows, rowErrors.size(), rowErrors);
            }

            // Import valid rows
            List<ImportAptResidentInpRequest> aptResidentInpRequests = new ArrayList<>();
            for (AptResImportRow importRow : rows) {
                ImportAptResidentInpRequest request = new ImportAptResidentInpRequest(
                        importRow.getBuildingName(),
                        importRow.getAptName(),
                        importRow.getCccd()
                );

                aptResidentInpRequests.add(request);
            }

            List<String> result = residentInputBoundary.importAptResidents(aptResidentInpRequests, complexId);
            if (!result.isEmpty())
                return AptResImportResult.errorExisted(aptResidentInpRequests.size(), result.size(), result);
            return AptResImportResult.success("Thêm mới thành công");

        } catch (Exception e) {
            return AptResImportResult.databaseError(e.getMessage());
        }
    }


    private AptResImportRow parseRow(Row row, int rowNumber) {
        AptResImportRow importRow = new AptResImportRow();

        try {
            String buidingName = getStringCellValue(row, 1);
            importRow.setBuildingName(buidingName);

            String aptName = getStringCellValue(row, 2);
            importRow.setAptName(aptName);

            String cccd = getStringCellValue(row, 3);
            importRow.setCccd(cccd);

        } catch (Exception e) {
            importRow.setErrors(List.of("Lỗi khi đọc dòng: " + e.getMessage()));
        }

        return importRow;
    }


    private List<String> validateRow(AptResImportRow row) {
        List<String> errors = new ArrayList<>();

        if (row.getBuildingName() == null || row.getBuildingName().isEmpty()) {
            errors.add("Tòa nhà không được để trống");
        }

        if (row.getAptName() == null || row.getAptName().isEmpty()) {
            errors.add("Căn hộ không được để trống");
        }

        if (isValidCccd(row.getCccd()) || row.getCccd().isEmpty()) {
            errors.add("CCCD phải có 9-12 chữ số");
        }

        return errors;
    }

    private String getStringCellValue(Row row, int columnIndex) {
        try {
            return row.getCell(columnIndex).getStringCellValue();
        } catch (Exception e) {
            return "";
        }
    }

    private boolean isValidCccd(String cccd) {
        String cccdRegex = "^[0-9]{12}$";
        return cccd != null && cccd.matches(cccdRegex);
    }
}
