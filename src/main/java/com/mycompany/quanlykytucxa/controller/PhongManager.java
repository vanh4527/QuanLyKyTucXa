package com.mycompany.quanlykytucxa.controller;

import ktx.model.Phong;
import ktx.model.ValidationException;
import ktx.storage.PhongXMLStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Xử lý nghiệp vụ Phòng: thêm / sửa / xóa / tìm, kiểm tra dữ liệu, tự động lưu phong.xml.
 * Các thành viên khác (Sinh viên - Hợp đồng, Tìm kiếm - Thống kê) dùng chung đối tượng này.
 */
public class PhongManager {
    private final List<Phong> ds = new ArrayList<>();
    private final PhongXMLStorage storage;

    public PhongManager(PhongXMLStorage storage) throws IOException {
        this.storage = storage;
        ds.addAll(storage.docFile());
    }

    public List<Phong> getDanhSach() {
        return Collections.unmodifiableList(ds);
    }

    public Phong timTheoMa(String ma) {
        if (ma == null) return null;
        for (Phong p : ds) {
            if (p.getMaPhong().equalsIgnoreCase(ma.trim())) return p;
        }
        return null;
    }

    /** Danh sách phòng còn giường trống (dùng khi lập hợp đồng). */
    public List<Phong> getPhongConCho() {
        List<Phong> kq = new ArrayList<>();
        for (Phong p : ds) {
            if (p.conCho()) kq.add(p);
        }
        return kq;
    }

    // ------------------------------------------------------------------
    // Kiểm tra dữ liệu nhập
    // ------------------------------------------------------------------

    /** Kiểm tra chuỗi nhập từ giao diện và tạo đối tượng Phong (chưa kiểm tra trùng mã). */
    public static Phong kiemTraVaTao(String ma, String khu, String loai,
                                     String soGiuongStr, String donGiaStr) throws ValidationException {
        ma = ma == null ? "" : ma.trim().toUpperCase();
        if (ma.isEmpty()) {
            throw new ValidationException("Mã phòng không được để trống.");
        }
        if (!ma.matches("[A-Z0-9][A-Z0-9\\-]{0,9}")) {
            throw new ValidationException(
                    "Mã phòng chỉ gồm chữ cái, số, dấu gạch ngang (tối đa 10 ký tự), ví dụ: A101.");
        }
        if (khu == null || khu.isEmpty()) {
            throw new ValidationException("Vui lòng chọn khu.");
        }
        if (loai == null || loai.isEmpty()) {
            throw new ValidationException("Vui lòng chọn loại phòng.");
        }

        int soGiuong;
        try {
            soGiuong = Integer.parseInt(soGiuongStr == null ? "" : soGiuongStr.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Số giường phải là số nguyên.");
        }
        if (soGiuong < 1 || soGiuong > 20) {
            throw new ValidationException("Số giường phải từ 1 đến 20.");
        }

        // Cho phép nhập 1.500.000 hoặc 1,500,000 hoặc 1 500 000
        String s = donGiaStr == null ? "" : donGiaStr.trim().replaceAll("[\\s.,]", "");
        if (s.isEmpty() || !s.matches("\\d{1,12}")) {
            throw new ValidationException("Đơn giá phải là số không âm (VNĐ).");
        }
        long donGia = Long.parseLong(s);
        if (donGia <= 0) {
            throw new ValidationException("Đơn giá phải lớn hơn 0.");
        }

        // Phòng mới: toàn bộ giường còn trống
        return new Phong(ma, khu, loai, soGiuong, soGiuong, donGia);
    }

    // ------------------------------------------------------------------
    // Thêm / Sửa / Xóa
    // ------------------------------------------------------------------

    public void them(Phong p) throws ValidationException, IOException {
        if (timTheoMa(p.getMaPhong()) != null) {
            throw new ValidationException("Mã phòng \"" + p.getMaPhong() + "\" đã tồn tại.");
        }
        List<Phong> backup = new ArrayList<>(ds);
        ds.add(p);
        luuHoacHoanTac(backup);
    }

    /** Sửa phòng theo mã (mã phòng là khóa, không đổi). Giữ nguyên số sinh viên đang ở. */
    public void sua(Phong moi) throws ValidationException, IOException {
        Phong cu = timTheoMa(moi.getMaPhong());
        if (cu == null) {
            throw new ValidationException("Không tìm thấy phòng \"" + moi.getMaPhong() + "\".");
        }
        int daO = cu.getSoGiuongDaO();
        if (moi.getSoGiuong() < daO) {
            throw new ValidationException("Phòng đang có " + daO
                    + " sinh viên ở, số giường không được nhỏ hơn " + daO + ".");
        }
        moi.setSoGiuongTrong(moi.getSoGiuong() - daO);

        List<Phong> backup = new ArrayList<>(ds);
        ds.set(ds.indexOf(cu), moi);
        luuHoacHoanTac(backup);
    }

    public void xoa(String ma) throws ValidationException, IOException {
        Phong p = timTheoMa(ma);
        if (p == null) {
            throw new ValidationException("Không tìm thấy phòng \"" + ma + "\".");
        }
        if (p.getSoGiuongDaO() > 0) {
            throw new ValidationException("Phòng " + ma + " đang có " + p.getSoGiuongDaO()
                    + " sinh viên ở, không thể xóa.");
        }
        List<Phong> backup = new ArrayList<>(ds);
        ds.remove(p);
        luuHoacHoanTac(backup);
    }

    /**
     * Dành cho phần Hợp đồng (thành viên 3): cập nhật số giường trống.
     * delta = -1 khi có sinh viên vào ở, +1 khi hợp đồng kết thúc / bị hủy.
     */
    public void thayDoiGiuongTrong(String ma, int delta) throws ValidationException, IOException {
        Phong p = timTheoMa(ma);
        if (p == null) {
            throw new ValidationException("Không tìm thấy phòng \"" + ma + "\".");
        }
        int moi = p.getSoGiuongTrong() + delta;
        if (moi < 0) {
            throw new ValidationException("Phòng " + ma + " đã hết chỗ.");
        }
        if (moi > p.getSoGiuong()) {
            throw new ValidationException(
                    "Số giường trống vượt quá tổng số giường của phòng " + ma + ".");
        }
        int cu = p.getSoGiuongTrong();
        p.setSoGiuongTrong(moi);
        try {
            storage.ghiFile(ds);
        } catch (IOException e) {
            p.setSoGiuongTrong(cu);
            throw e;
        }
    }

    private void luuHoacHoanTac(List<Phong> backup) throws IOException {
        try {
            storage.ghiFile(ds);
        } catch (IOException e) {
            ds.clear();
            ds.addAll(backup);
            throw e;
        }
    }
}
