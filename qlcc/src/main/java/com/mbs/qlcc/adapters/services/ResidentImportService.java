package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.imports.ApartmentImportResult;
import com.mbs.qlcc.adapters.imports.ApartmentImportRow;
import com.mbs.qlcc.adapters.imports.ResidenImportResult;
import com.mbs.qlcc.adapters.imports.ResidentImportRow;
import com.mbs.qlcc.adapters.request.Apartment.CreateApartmentRequest;
import com.mbs.qlcc.usecases.input.IResidentInputBoundary;
import com.mbs.qlcc.usecases.request.Resident.ImportResidentInpRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResidentImportService {
    private final IResidentInputBoundary residentInputBoundary;

    public ResidenImportResult importFromExcel(MultipartFile file, String complexId) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Parse data rows (starting from row 5 - index 4)
            List<ResidentImportRow> rows = new ArrayList<>();
            List<ResidenImportResult.ResidentRowError> rowErrors = new ArrayList<>();
            int totalRows = 0;

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) break;

                totalRows++;
                ResidentImportRow importRow = parseRow(row, i + 1);

                // Validate row
                List<String> errors = validateRow(importRow);
                if (!errors.isEmpty()) {
                    importRow.setErrors(errors);
                    ResidenImportResult.ResidentRowError rowError = new ResidenImportResult.ResidentRowError(
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
                return ResidenImportResult.validationError(totalRows, rowErrors.size(), rowErrors);
            }

            // Import valid rows
            List<ImportResidentInpRequest> createResidentRequest = new ArrayList<>();
            for (ResidentImportRow importRow : rows) {
                ImportResidentInpRequest request = new ImportResidentInpRequest(
                        importRow.getFullname(),
                        importRow.getGender(),
                        importRow.getEmail(),
                        importRow.getBirthday(),
                        importRow.getRelationship(),
                        importRow.getPhoneNumber(),
                        importRow.getCccd()
                );

                createResidentRequest.add(request);
            }

            String result = residentInputBoundary.importResidents(createResidentRequest, complexId);
            if (!result.isEmpty())
                return ResidenImportResult.success(false, result);
            return ResidenImportResult.success(true, "Thêm mới thành công");

        } catch (Exception e) {
            return ResidenImportResult.databaseError(e.getMessage());
        }
    }


    private ResidentImportRow parseRow(Row row, int rowNumber) {
        ResidentImportRow importRow = new ResidentImportRow();

        try {
            String fullName = getStringCellValue(row, 1);
            importRow.setFullname(fullName);

            String cccd = getStringCellValue(row, 2);
            importRow.setCccd(cccd);

            String email = getStringCellValue(row, 3);
            importRow.setEmail(email);

            String phoneNumber = getStringCellValue(row, 4);
            importRow.setPhoneNumber(phoneNumber);

            LocalDateTime birthday = getDateCellValue(row, 5);
            importRow.setBirthday(birthday);

            String relationship = getStringCellValue(row, 6);
            importRow.setRelationship(relationship);

            int gender = normalizeGender(getStringCellValue(row, 7).trim().toLowerCase());
            importRow.setGender(gender);

        } catch (Exception e) {
            importRow.setErrors(List.of("Lỗi khi đọc dòng: " + e.getMessage()));
        }

        return importRow;
    }


    private List<String> validateRow(ResidentImportRow row) {
        List<String> errors = new ArrayList<>();

        if (row.getFullname() == null || row.getFullname().isEmpty()) {
            errors.add("Họ tên không được để trống");
        }
        if (row.getGender() != 0 && row.getGender() != 1) {
            errors.add("Giới tính không hợp lệ (chỉ chấp nhận: Nam, Nữ, Male, Female)");
        }
        if (row.getBirthday() == null) {
            errors.add("Ngày sinh không hợp lệ");
        }

        if (!isValidEmail(row.getEmail()) || row.getEmail().isEmpty()) {
            errors.add("Email không hợp lệ");
        }

        if (!isValidCccd(row.getCccd()) || row.getCccd().isEmpty()) {
            errors.add("CCCD phải có 9-12 chữ số");
        }
        if (!isValidPhoneNumber(row.getPhoneNumber()) || row.getPhoneNumber().isEmpty()) {
            errors.add("Số điện thoại phải có 10-11 chữ số");
        }
        if (row.getRelationship() == null || row.getRelationship().isEmpty()) {
            errors.add("Mối quan hệ không được để trống");
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

    private LocalDateTime getDateCellValue(Row row, int columnIndex) {
        try {
            String birthday = row.getCell(columnIndex).getStringCellValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(birthday, formatter).atStartOfDay();
        } catch (Exception e) {
            return null;
        }
    }

    private int normalizeGender(String gender) {
        List<String> maleValues = new ArrayList<>(List.of("nam", "male", "m", "Nam"));
        List<String> femaleValues = new ArrayList<>(List.of("nữ", "female", "f", "Nữ"));
        if (maleValues.contains(gender))
            return 0;
        if (femaleValues.contains(gender))
            return 1;
        return -1;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(\\+84|0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-4|6-9])[0-9]{7}$";
        return phoneNumber != null && phoneNumber.matches(phoneRegex);
    }

    private boolean isValidCccd(String cccd) {
        String cccdRegex = "^[0-9]{12}$";
        return cccd != null && cccd.matches(cccdRegex);
    }


}
