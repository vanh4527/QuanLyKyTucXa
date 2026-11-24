package com.mycompany.quanlykytucxa.entity;

/** Lớp thực thể Phòng ký túc xá. */
public class Phong {
    public static final String[] DS_KHU = {"A", "B", "C", "D"};
    public static final String[] DS_LOAI = {"Thường", "Dịch vụ", "VIP"};

    private String maPhong;
    private String khu;
    private String loaiPhong;
    private int soGiuong;
    private int soGiuongTrong;
    private double donGia; // đơn giá thuê / tháng (VNĐ)

    public Phong() {
    }

    public Phong(String maPhong, String khu, String loaiPhong,
                 int soGiuong, int soGiuongTrong, double donGia) {
        this.maPhong = maPhong;
        this.khu = khu;
        this.loaiPhong = loaiPhong;
        this.soGiuong = soGiuong;
        this.soGiuongTrong = soGiuongTrong;
        this.donGia = donGia;
    }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getKhu() { return khu; }
    public void setKhu(String khu) { this.khu = khu; }

    public String getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }

    public int getSoGiuong() { return soGiuong; }
    public void setSoGiuong(int soGiuong) { this.soGiuong = soGiuong; }

    public int getSoGiuongTrong() { return soGiuongTrong; }
    public void setSoGiuongTrong(int soGiuongTrong) { this.soGiuongTrong = soGiuongTrong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    /** Số giường đã có người ở. */
    public int getSoGiuongDaO() { return soGiuong - soGiuongTrong; }

    /** Phòng còn chỗ trống hay không (dùng cho phần Hợp đồng / Thống kê). */
    public boolean conCho() { return soGiuongTrong > 0; }

    @Override
    public String toString() {
        return maPhong + " - Khu " + khu + " - " + loaiPhong
                + " (" + soGiuongTrong + "/" + soGiuong + " giường trống)";
    }
}
