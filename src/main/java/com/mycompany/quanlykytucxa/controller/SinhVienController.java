package com.mycompany.quanlykytucxa.controller;

import com.mycompany.quanlykytucxa.action.SinhVienAction;
import com.mycompany.quanlykytucxa.entity.SinhVien;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller quản lý danh sách Sinh viên trong bộ nhớ:
 * Thêm / Sửa / Xóa / Hiển thị + kiểm tra dữ liệu đầu vào.
 * Bắt lỗi: bỏ trống trường bắt buộc, trùng Mã SV, trùng số CCCD.
 */
public class SinhVienController implements SinhVienAction {

    private final List<SinhVien> danhSach = new ArrayList<>();

    public SinhVienController() {
        // Dữ liệu mẫu để demo (xóa khi đã gắn ghi/đọc XML của Thành viên 4)
        danhSach.add(new SinhVien("SV001", "Nguyễn Văn An", "12/03/2004", "Nam",
                "001204001234", "0901234567", "Hà Nội", "Công nghệ thông tin", "CNTT1", "A101"));
        danhSach.add(new SinhVien("SV002", "Trần Thị Bình", "25/07/2004", "Nữ",
                "001204005678", "0912345678", "Hải Phòng", "Kinh tế", "KT2", "A102"));
        danhSach.add(new SinhVien("SV003", "Lê Văn Cường", "30/11/2003", "Nam",
                "001203009999", "0987654321", "Nam Định", "Cơ khí", "CK1", ""));
    }

    // ---------- Kiểm tra dữ liệu ----------
    /**
     * Kiểm tra sinh viên trước khi Thêm/Sửa.
     *
     * @param sv        sinh viên cần kiểm tra
     * @param dangSua   true nếu đang sửa (bỏ qua chính sinh viên đó khi check trùng)
     * @return null nếu hợp lệ, ngược lại trả về thông báo lỗi
     */
    private String kiemTra(SinhVien sv, boolean dangSua) {
        if (sv.getMaSinhVien() == null || sv.getMaSinhVien().trim().isEmpty()) {
            return "Mã sinh viên không được bỏ trống!";
        }
        if (sv.getHoTen() == null || sv.getHoTen().trim().isEmpty()) {
            return "Họ tên không được bỏ trống!";
        }
        if (sv.getSoCCCD() == null || sv.getSoCCCD().trim().isEmpty()) {
            return "Số CCCD không được bỏ trống!";
        }
        if (!sv.getSoCCCD().trim().matches("\\d{9}|\\d{12}")) {
            return "Số CCCD phải gồm 9 hoặc 12 chữ số!";
        }
        if (sv.getSoDienThoai() != null && !sv.getSoDienThoai().trim().isEmpty()
                && !sv.getSoDienThoai().trim().matches("\\d{10}")) {
            return "Số điện thoại phải gồm 10 chữ số!";
        }
        for (SinhVien khac : danhSach) {
            // Khi sửa, bỏ qua chính sinh viên đang sửa
            if (dangSua && khac.getMaSinhVien().equalsIgnoreCase(sv.getMaSinhVien())) {
                continue;
            }
            if (khac.getMaSinhVien().equalsIgnoreCase(sv.getMaSinhVien().trim())) {
                return "Trùng Mã sinh viên: " + sv.getMaSinhVien() + " đã tồn tại!";
            }
            if (khac.getSoCCCD().equals(sv.getSoCCCD().trim())) {
                return "Trùng số CCCD: " + sv.getSoCCCD() + " đã tồn tại!";
            }
        }
        return null;
    }

    // ---------- Thêm / Sửa / Xóa / Hiển thị ----------
    @Override
    public String themSinhVien(SinhVien sv) {
        String loi = kiemTra(sv, false);
        if (loi != null) {
            return loi;
        }
        danhSach.add(sv);
        ghiXML();
        return null;
    }

    @Override
    public String suaSinhVien(SinhVien sv) {
        SinhVien cu = timTheoMa(sv.getMaSinhVien());
        if (cu == null) {
            return "Không tìm thấy sinh viên có mã " + sv.getMaSinhVien() + " để sửa!";
        }
        String loi = kiemTra(sv, true);
        if (loi != null) {
            return loi;
        }
        cu.setHoTen(sv.getHoTen());
        cu.setNgaySinh(sv.getNgaySinh());
        cu.setGioiTinh(sv.getGioiTinh());
        cu.setSoCCCD(sv.getSoCCCD());
        cu.setSoDienThoai(sv.getSoDienThoai());
        cu.setDiaChi(sv.getDiaChi());
        cu.setKhoa(sv.getKhoa());
        cu.setLop(sv.getLop());
        cu.setMaPhong(sv.getMaPhong());
        ghiXML();
        return null;
    }

    @Override
    public String xoaSinhVien(String maSinhVien) {
        SinhVien cu = timTheoMa(maSinhVien);
        if (cu == null) {
            return "Không tìm thấy sinh viên có mã " + maSinhVien + " để xóa!";
        }
        danhSach.remove(cu);
        ghiXML();
        return null;
    }

    @Override
    public List<SinhVien> layDanhSach() {
        return danhSach;
    }

    @Override
    public SinhVien timTheoMa(String maSinhVien) {
        if (maSinhVien == null) {
            return null;
        }
        for (SinhVien sv : danhSach) {
            if (sv.getMaSinhVien().equalsIgnoreCase(maSinhVien.trim())) {
                return sv;
            }
        }
        return null;
    }

    /**
     * Điểm gắn ghi XML của Thành viên 4: mỗi lần Thêm/Sửa/Xóa xong sẽ gọi hàm này.
     * Khi TV4 bàn giao SinhVienXML thì bỏ comment dòng dưới:
     */
    private void ghiXML() {
        // SinhVienXML.ghiFile(danhSach);
    }
}
