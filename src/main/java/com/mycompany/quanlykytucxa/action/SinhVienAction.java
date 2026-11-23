package com.mycompany.quanlykytucxa.action;

import com.mycompany.quanlykytucxa.entity.SinhVien;

import java.util.List;

/**
 * Interface quy định các thao tác bắt buộc trên Sinh viên.
 * SinhVienController sẽ implements interface này.
 * Các hàm Thêm/Sửa/Xóa trả về String:
 *   - null  : thực hiện thành công
 *   - khác null: nội dung lỗi để View hiển thị cho người dùng
 */
public interface SinhVienAction {

    String themSinhVien(SinhVien sv);

    String suaSinhVien(SinhVien sv);

    String xoaSinhVien(String maSinhVien);

    List<SinhVien> layDanhSach();

    SinhVien timTheoMa(String maSinhVien);
}
