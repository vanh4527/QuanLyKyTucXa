package com.mycompany.quanlykytucxa.entity;

/**
 * Lớp SinhVien kế thừa từ lớp cha Nguoi (từ khóa extends).
 * SinhVien dùng lại hoTen, ngaySinh, gioiTinh, soCCCD, soDienThoai, diaChi
 * của Nguoi và bổ sung các thuộc tính riêng của sinh viên.
 */
public class SinhVien extends Nguoi {

    private String maSinhVien;
    private String khoa;
    private String lop;
    private String maPhong;    // mã phòng KTX đang ở, để trống nếu chưa xếp phòng

    public SinhVien() {
        super();
    }

    public SinhVien(String maSinhVien, String hoTen, String ngaySinh, String gioiTinh,
                    String soCCCD, String soDienThoai, String diaChi,
                    String khoa, String lop, String maPhong) {
        // Gọi constructor của lớp cha Nguoi để khởi tạo phần thông tin chung
        super(hoTen, ngaySinh, gioiTinh, soCCCD, soDienThoai, diaChi);
        this.maSinhVien = maSinhVien;
        this.khoa = khoa;
        this.lop = lop;
        this.maPhong = maPhong;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    @Override
    public String toString() {
        return maSinhVien + " - " + super.toString() + " - " + khoa + " - " + lop;
    }
}
