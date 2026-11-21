package quanlykytucxa.entity;

import java.util.Date;

/** Sinh viên kế thừa Nguoi (tính kế thừa OOP). */
public class SinhVien extends Nguoi {
    private String maSV;
    private String khoa;
    private int phongId; // id phòng đang ở, 0 nếu chưa có

    public SinhVien() {
        super();
    }

    public SinhVien(int id, String maSV, String hoTen, Date ngaySinh, String gioiTinh,
                    String soCCCD, String soDienThoai, String khoa, int phongId) {
        super(id, hoTen, ngaySinh, gioiTinh, soCCCD, soDienThoai);
        this.maSV = maSV;
        this.khoa = khoa;
        this.phongId = phongId;
    }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }
    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }
    public int getPhongId() { return phongId; }
    public void setPhongId(int phongId) { this.phongId = phongId; }
}
