package com.mbs.qlcc.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    // General Errors
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa được phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998, "Invalid constraint key", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_CREATED(9997, "Đã xảy ra lỗi khi tạo mới. Vui lòng kiểm tra lại thông tin!", HttpStatus.BAD_REQUEST),
    NOT_FOUND(9998, "Không tìm thấy thông tin", HttpStatus.BAD_REQUEST),
    NOT_UPDATE(9995, "Đã xảy ra lỗi khi cập nhật. Vui lòng kiểm tra lại thông tin!", HttpStatus.BAD_REQUEST),
    NOT_DELETED(9994, "Đã xảy ra lỗi khi xóa dữ liệu. Vui lòng kiểm tra lại thông tin!", HttpStatus.BAD_REQUEST),

    // Authentication & Authorization
    UNAUTHENTICATED(1000, "Không thể xác thực người dùng", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1001, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(1002, "Token đã hết hạn", HttpStatus.FORBIDDEN),
    INCORRECT_LOGIN_INFO(1003, "Sai thông tin đăng nhập", HttpStatus.UNAUTHORIZED),
    INCORRECT_RF_TOKEN(1004, "Refresh token không hợp lệ hoặc hết hạn", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID(1005, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    CODE_NOT_EMPTY(1006, "Mã code không hợp lệ hoặc bị bỏ trống", HttpStatus.BAD_REQUEST),


    // User Errors
    USER_EXISTED(1010, "User đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_EXITED(1011, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NON_EXISTED(1012, "User không tồn tại", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1018, "Password và Retype password không trùng nhau", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_NULL(1019, "Username không được để trống", HttpStatus.BAD_REQUEST),
    USERNAME_SIZE(1020, "Độ dài tên lớn hơn 2 và không vượt quá 50 kí tự", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_NULL(1021, "Email không được để trống", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FORMAT(1022, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_NULL(1023, "Địa chỉ không được để trống", HttpStatus.BAD_REQUEST),
    ADDRESS_SIZE(1024, "Độ dài địa chỉ lớn hơn 5 và không vượt quá 255 kí tự", HttpStatus.BAD_REQUEST),


    // Organization Errors
    ORG_NAME_NOT_EMPTY(2000, "Tên phòng ban không được để trống!", HttpStatus.BAD_REQUEST),
    ORG_NAME_LENGTH(2001, "Tên phòng ban dài tối thiểu 5 ký tự và tối đa 30 ký tự!", HttpStatus.BAD_REQUEST),
    ORG_NAME_NOT_FOUND(2002, "Phòng ban không tồn tại!", HttpStatus.BAD_REQUEST),
    ORG_DESCRIPTION_LENGTH(2003, "Mô tả phòng ban dài tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    ORG_NAME_UNIQUE(2004, "Phòng ban đã tồn tại!", HttpStatus.BAD_REQUEST),
    PARENT_ORG_EXISTED(2005, "Chỉ tồn tại 1 cấp BQT cao nhất!", HttpStatus.BAD_REQUEST),
    MAX_ORG_LEVEL(2006, "Cấp tổ chức tối đa là cấp 3", HttpStatus.BAD_REQUEST),


    // Complex Errors
    COMPLEX_NAME_EXISTED(3000, "Tên chung cư đã tồn tại!", HttpStatus.BAD_REQUEST),
    COMPLEX_ADDRESS_EXISTED(3001, "Địa chỉ đã tồn tại!", HttpStatus.BAD_REQUEST),
    COMPLEX_NAME_NOT_EMPTY(3002, "Tên chung cư không được để trống!", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_EMPTY(3003, "Địa chỉ không được để trống!", HttpStatus.BAD_REQUEST),
    ADDRESS_LENGTH(3004, "Địa chỉ dài tối thiểu 5 ký tự và tối đa 50 ký tự!", HttpStatus.BAD_REQUEST),
    DESCRIPTION_LENGTH(3005, "Mô tả dài tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    TOTAL_BUILDING_NOT_EMPTY(3006, "Tổng số tòa nhà không được để trống!", HttpStatus.BAD_REQUEST),
    TOTAL_BUILDING_LENGTH(3007, "Tối đa 20 tòa!", HttpStatus.BAD_REQUEST),
    TOTAL_APARTMENT_NOT_EMPTY(3008, "Tổng số căn hộ không được để trống!", HttpStatus.BAD_REQUEST),
    NAME_CONTACT_NOT_EMPTY(3009, "Tên người liên hệ không được để trống!", HttpStatus.BAD_REQUEST),
    PHONE_CONTACT_NOT_EMPTY(3010, "Số điện thoại liên hệ không được để trống!", HttpStatus.BAD_REQUEST),
    EMAIL_CONTACT_NOT_EMPTY(3011, "Email liên hệ không được để trống!", HttpStatus.BAD_REQUEST),
    COMPLEX_PHONE_EXISTED(3012, "Số điện thoại liên hệ đã được sử dụng!", HttpStatus.BAD_REQUEST),
    COMPLEX_NOT_FOUND(3013, "Chung cư không tồn tại!", HttpStatus.NOT_FOUND),
    INVALID_REQUEST(3014, "Yêu cầu không hợp lệ!", HttpStatus.BAD_REQUEST),


    // Service Unit Price Errors
    PRICE_NON_EXISTED(4000, "Giá dịch vụ không tồn tại", HttpStatus.BAD_REQUEST),
    YEAR_NOT_EMPTY(4001, "Năm là bắt buộc", HttpStatus.BAD_REQUEST),
    YEAR_NOT_INTEGER(4002, "Năm phải là số nguyên", HttpStatus.BAD_REQUEST),
    YEAR_MIN_MAX(4003, "Năm phải từ 1900 đến 2100", HttpStatus.BAD_REQUEST),
    MONTH_NOT_EMPTY(4004, "Tháng là bắt buộc", HttpStatus.BAD_REQUEST),
    MONTH_NOT_INTEGER(4005, "Tháng phải là số nguyên", HttpStatus.BAD_REQUEST),
    MONTH_MIN_MAX(4006, "Tháng phải từ 1 đến 12", HttpStatus.BAD_REQUEST),
    PRICE_PER_M2_NOT_EMPTY(4007, "Đơn giá/m² là bắt buộc", HttpStatus.BAD_REQUEST),
    PRICE_PER_M2_NOT_NUMERIC(4008, "Đơn giá/m² phải là số", HttpStatus.BAD_REQUEST),
    PRICE_HAS_REVENUE(4009, "Đơn giá dịch vụ đã có hóa đơn", HttpStatus.BAD_REQUEST),


    // Revenue Errors
    APARTMENT_ID_NOT_UUID(4010, "ID căn hộ không hợp lệ", HttpStatus.BAD_REQUEST),
    STATUS_INVALID(4011, "Trạng thái không hợp lệ (unpaid, partial, paid, overpaid)", HttpStatus.BAD_REQUEST),
    APARTMENT_ID_REQUIRED(4012, "Căn hộ là bắt buộc", HttpStatus.BAD_REQUEST),
    APARTMENT_ID_NOT_EXISTS(4013, "Căn hộ không tồn tại", HttpStatus.BAD_REQUEST),
    ORIGINAL_AMOUNT_REQUIRED(4014, "Số tiền nghĩa vụ là bắt buộc", HttpStatus.BAD_REQUEST),
    ORIGINAL_AMOUNT_NOT_NUMERIC(4015, "Số tiền nghĩa vụ phải là số", HttpStatus.BAD_REQUEST),
    REVENUE_NOT_UPDATE(4016, "Khoản thu không thể chỉnh sửa", HttpStatus.BAD_REQUEST),


    // Expense Errors
    TITLE_REQUIRED(4017, "Tên sự việc là bắt buộc", HttpStatus.BAD_REQUEST),
    CATEGORY_REQUIRED(4018, "Hạng mục chi là bắt buộc", HttpStatus.BAD_REQUEST),
    CATEGORY_INVALID(4019, "Hạng mục chi không hợp lệ", HttpStatus.BAD_REQUEST),
    AMOUNT_REQUIRED(4020, "Số tiền là bắt buộc", HttpStatus.BAD_REQUEST),
    AMOUNT_NOT_NUMERIC(4021, "Số tiền phải là số", HttpStatus.BAD_REQUEST),
    AMOUNT_MIN(4022, "Số tiền phải >= 0", HttpStatus.BAD_REQUEST),
    EXPENSE_NOT_UPDATE(4023, "Khoản chi không thể cập nhật", HttpStatus.BAD_REQUEST),
    DATE_INVALID(4024, "Ngày không hợp lệ", HttpStatus.BAD_REQUEST),
    DATE_TO_AFTER_FROM(4025, "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu", HttpStatus.BAD_REQUEST),

    // Ledger Errors
    BUILDING_ID_REQUIRED(4026, "Tòa nhà là bắt buộc", HttpStatus.BAD_REQUEST),
    FUND_TYPE_REQUIRED(4027, "Loại quỹ là bắt buộc", HttpStatus.BAD_REQUEST),
    FUND_TYPE_INVALID(4028, "Loại quỹ không hợp lệ", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_REQUIRED(4029, "Phương thức thanh toán là bắt buộc", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_INVALID(4030, "Phương thức thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    TRANSACTION_DATE_REQUIRED(4031, "Ngày giao dịch là bắt buộc", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_VALID(5000, "Loại phiếu không hợp lệ. Chỉ chấp nhận PT hoặc PC", HttpStatus.BAD_REQUEST),
    LEDGER_SUMMARY_EXISTED(5001, "Số dư cuối kỳ đã được tạo", HttpStatus.BAD_REQUEST),
    LEDGER_SUMMARY_NOT_EXISTED(5002, "Số dư cuối kỳ chưa được tạo", HttpStatus.BAD_REQUEST),

    // Building Errors
    BUILDING_NON_EXISTED(4099, "Toà nhà không tồn tại", HttpStatus.BAD_REQUEST),
    BUILDING_NOT_EMPTY(4098, "Toà nhà không được để trống", HttpStatus.BAD_REQUEST),
    BUILDING_NAME_NOT_EMPTY(4097, "Tên toà nhà không được để trống", HttpStatus.BAD_REQUEST),
    BUILDING_ADDRESS_NOT_EMPTY(4096, "Địa chỉ toà nhà không được để trống", HttpStatus.BAD_REQUEST),
    COMPLEX_ID_NOT_EMPTY(4095, "Chung cư không được để trống", HttpStatus.BAD_REQUEST),
    FINANCIAL_TOTAL_RATIO_NOT_VALID(4096, "Tổng tỉ lệ phải bằng 100%", HttpStatus.BAD_REQUEST),

    // Apartment Errors
    FLOOR_REQUIRED(4100, "Số tầng là bắt buộc", HttpStatus.BAD_REQUEST),
    FLOOR_NOT_INTEGER(4101, "Số tầng phải là số nguyên", HttpStatus.BAD_REQUEST),
    APT_NUMBER_REQUIRED(4102, "Số căn hộ là bắt buộc", HttpStatus.BAD_REQUEST),
    APT_NUMBER_LENGTH(4103, "Số căn hộ không vượt quá 20 ký tự", HttpStatus.BAD_REQUEST),
    APT_AREA_REQUIRED(4104, "Diện tích căn hộ là bắt buộc", HttpStatus.BAD_REQUEST),
    APT_AREA_NOT_NUMERIC(4105, "Diện tích căn hộ phải là số", HttpStatus.BAD_REQUEST),
    APT_AREA_MIN(4106, "Diện tích căn hộ phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    APT_TYPE_REQUIRED(4107, "Loại căn hộ là bắt buộc", HttpStatus.BAD_REQUEST),
    APT_TYPE_INVALID(4108, "Loại căn hộ không hợp lệ", HttpStatus.BAD_REQUEST),
    APT_NUMBER_EXISTED(4109, "Số căn hộ đã tồn tại trong tòa nhà này", HttpStatus.BAD_REQUEST),


    // File Upload Errors
    FILE_REQUIRED(6000, "File là bắt buộc", HttpStatus.BAD_REQUEST),
    FILE_INVALID(6001, "File tải lên không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_EXCEL_INVALID_FORMAT(6002, "File phải có định dạng xlsx hoặc xls", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED(6003, "File không được vượt quá 50MB", HttpStatus.BAD_REQUEST),


    // Resident Errors
    RESIDENT_EXISTED(7000, "Thông tin cư dân đã tồn tại!", HttpStatus.BAD_REQUEST),
    RESIDENT_EMAIL_EXISTED(7001, "Email cư dân đã tồn tại trong chung cư này!", HttpStatus.BAD_REQUEST),
    RESIDENT_PHONE_EXISTED(7002, "Số điện thoại cư dân đã tồn tại trong chung cư này!", HttpStatus.BAD_REQUEST),
    RESIDENT_CCCD_EXISTED(7003, "Số CCCD cư dân đã tồn tại trong chung cư này!", HttpStatus.BAD_REQUEST),
    RESIDENT_NOT_FOUND(7004, "Cư dân không tồn tại!", HttpStatus.NOT_FOUND),
    RESIDENT_GENDER_NOT_EMPTY(7005, "Giới tính không được để trống", HttpStatus.BAD_REQUEST),
    RESIDENT_FULLNAME_NOT_EMPTY(7006, "Họ tên không được để trống", HttpStatus.BAD_REQUEST),
    RESIDENT_EMAIL_NOT_EMPTY(7007, "Email không được để trống", HttpStatus.BAD_REQUEST),
    RESIDENT_BIRTHDAY_NOT_EMPTY(7008, "Ngày sinh không được để trống", HttpStatus.BAD_REQUEST),
    RESIDENT_PHONE_NOT_EMPTY(7009, "Số điện thoại không được để trống", HttpStatus.BAD_REQUEST),
    RESIDENT_RELATIONSHIP_NOT_EMPTY(7010, "Mối quan hệ không được để trống", HttpStatus.BAD_REQUEST),
    RESIDENT_CCCD_NOT_EMPTY(7011, "Số CCCD không được để trống", HttpStatus.BAD_REQUEST),


    // Staff Errors
    STAFF_EXISTED(8001, "Thông tin thành viên BQL đã tồn tại!", HttpStatus.BAD_REQUEST),
    STAFF_FULLNAME_NOT_EMPTY(8002, "Họ tên không được để trống", HttpStatus.BAD_REQUEST),
    STAFF_EMAIL_NOT_EMPTY(8003, "Email không được để trống", HttpStatus.BAD_REQUEST),
    STAFF_PHONE_NOT_EMPTY(8004, "Số điện thoại không được để trống", HttpStatus.BAD_REQUEST),
    STAFF_ORG_ID_NOT_EMPTY(8005, "Phòng ban không được để trống", HttpStatus.BAD_REQUEST),
    STAFF_ROLE_ID_NOT_EMPTY(8006, "Vai trò không được để trống", HttpStatus.BAD_REQUEST),


    // Task Errors
    TASK_INFO_INVALID(8999, "Thông tin đề xuất chưa hợp lệ. Vui lòng kiểm tra lại thông tin (loại yêu cầu,tòa nhà)", HttpStatus.BAD_REQUEST),
    TASK_TYPE_ID_NOT_EMPTY(8100, "Loại yêu cầu không được để trống", HttpStatus.BAD_REQUEST),
    TASK_BUILDING_ID_NOT_EMPTY(8101, "Tòa nhà không được để trống", HttpStatus.BAD_REQUEST),
    TASK_NAME_NOT_EMPTY(8102, "Tên yêu cầu không được để trống", HttpStatus.BAD_REQUEST),
    TASK_DESCRIPTION_NOT_EMPTY(8103, "Mô tả không được để trống", HttpStatus.BAD_REQUEST),


    // Task Type Errors
    TASKTYPE_WORKFLOW_ID_NOT_EMPTY(8200, "Quy trình không được để trống", HttpStatus.BAD_REQUEST),
    TASKTYPE_PRIORITY_ID_NOT_EMPTY(8201, "Độ ưu tiên không được để trống", HttpStatus.BAD_REQUEST),
    TASKTYPE_NAME_NOT_EMPTY(8202, "Tên loại yêu cầu không được để trống", HttpStatus.BAD_REQUEST),
    TASKTYPE_DESCRIPTION_NOT_EMPTY(8203, "Mô tả không được để trống", HttpStatus.BAD_REQUEST),


    // Permission Errors
    PERMISSION_NAME_NOT_EMPTY(9000, "Tên quyền không được để trống", HttpStatus.BAD_REQUEST),
    PERMISSION_MODULE_NOT_EMPTY(9001, "Module không được để trống", HttpStatus.BAD_REQUEST),


    // Role Errors
    ROLE_NAME_NOT_EMPTY(9100, "Tên vai trò không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_COMPLEX_ID_NOT_EMPTY(9101, "Chung cư không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_DESCRIPTION_NOT_EMPTY(9102, "Mô tả không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_USER_ID_NOT_EMPTY(9103, "User không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_ID_NOT_EMPTY(9104, "Vai trò không được để trống", HttpStatus.BAD_REQUEST),


    // Workflow Errors
    WORKFLOW_COMPLEX_ID_NOT_EMPTY(8300, "Chung cư không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_NAME_NOT_EMPTY(8301, "Tên quy trình không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_DESCRIPTION_NOT_EMPTY(8302, "Mô tả không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STATUS_NOT_EMPTY(8303, "Trạng thái không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_NOT_EMPTY(8304, "Bước quy trình không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_MIN(8305, "Quy trình phải có ít nhất 1 bước", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_ORG_LEVEL_NOT_EMPTY(8306, "Cấp tổ chức của bước không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_ORDER_NOT_EMPTY(8307, "Thứ tự bước không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_DESCRIPTION_NOT_EMPTY(8308, "Mô tả bước không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_STATUS_NOT_EMPTY(8309, "Trạng thái bước không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_POSITION_NOT_EMPTY(8310, "Vị trí không được để trống", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_POSITION_ARRAY(8311, "Vị trí phải là một mảng", HttpStatus.BAD_REQUEST),
    WORKFLOW_STEP_POSITION_ITEM_NOT_EMPTY(8312, "Phần tử vị trí không được để trống", HttpStatus.BAD_REQUEST),


    // Money Account Errors
    MONEY_ACCOUNT_BANK_NAME_NOT_EMPTY(8400, "Tên ngân hàng không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_NUMBER_NOT_EMPTY(8401, "Số tài khoản không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_TERM_NOT_EMPTY(8402, "Kỳ hạn không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_TERM_NOT_INTEGER(8403, "Kỳ hạn phải là số nguyên", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_DEPOSIT_DATE_NOT_EMPTY(8404, "Ngày gửi không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_DEPOSIT_DATE_INVALID(8405, "Ngày gửi không hợp lệ", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_MATURITY_DATE_NOT_EMPTY(8406, "Ngày đáo hạn không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_MATURITY_DATE_INVALID(8407, "Ngày đáo hạn không hợp lệ", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_INTEREST_RATE_NOT_EMPTY(8408, "Lãi suất không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_INTEREST_RATE_NOT_NUMERIC(8409, "Lãi suất phải là số", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_MONEY_NOT_EMPTY(8410, "Số tiền không được để trống", HttpStatus.BAD_REQUEST),
    MONEY_ACCOUNT_MONEY_NOT_NUMERIC(8411, "Số tiền phải là số", HttpStatus.BAD_REQUEST),

    // Building Errors (Additional)
    BUILDING_NAME_EXISTS(4094, "Tên tòa nhà đã tồn tại", HttpStatus.BAD_REQUEST),
    BUILDING_NOT_FOUND(4093, "Tòa nhà không tồn tại", HttpStatus.NOT_FOUND),

    // Apartment Errors (Additional)
    APARTMENT_NOT_FOUND(4110, "Căn hộ không tồn tại", HttpStatus.NOT_FOUND),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
