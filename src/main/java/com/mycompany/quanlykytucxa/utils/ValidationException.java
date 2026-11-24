package ktx.model;

/** Lỗi nhập sai số liệu / vi phạm quy tắc nghiệp vụ (trùng mã, số giường không hợp lệ...). */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
