package com.mycompany.quanlykytucxa.entity;

/**
 * Lớp cha Nguoi: chứa các thuộc tính chung của một người.
 * Lớp SinhVien sẽ kế thừa lại toàn bộ thuộc tính này (tính kế thừa OOP).
 */
public class Nguoi {

    private String hoTen;
    private String ngaySinh;   // định dạng dd/MM/yyyy
    private String gioiTinh;   // Nam / Nữ / Khác
    private String soCCCD;
    private String soDienThoai;
    private String diaChi;

    public Nguoi() {
    }

    public Nguoi(String hoTen, String ngaySinh, String gioiTinh,
                 String soCCCD, String soDienThoai, String diaChi) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soCCCD = soCCCD;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoCCCD() {
        return soCCCD;
    }

    public void setSoCCCD(String soCCCD) {
        this.soCCCD = soCCCD;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return hoTen + " - " + ngaySinh + " - " + gioiTinh + " - CCCD: " + soCCCD;
    }
}
