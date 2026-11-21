package quanlykytucxa.entity;

import java.util.Date;

/** Lớp cha: thông tin chung của một người. */
public class Nguoi {
    private int id;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String soCCCD;
    private String soDienThoai;

    public Nguoi() {
    }

    public Nguoi(int id, String hoTen, Date ngaySinh, String gioiTinh, String soCCCD, String soDienThoai) {
        this.id = id;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soCCCD = soCCCD;
        this.soDienThoai = soDienThoai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getSoCCCD() { return soCCCD; }
    public void setSoCCCD(String soCCCD) { this.soCCCD = soCCCD; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    @Override
    public String toString() {
        return hoTen + " (" + soCCCD + ")";
    }
}
