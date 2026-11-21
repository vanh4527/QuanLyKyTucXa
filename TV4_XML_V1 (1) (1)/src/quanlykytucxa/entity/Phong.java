package quanlykytucxa.entity;

public class  Phong {
    private int id;
    private String maPhong;
    private String khuNha;
    private String loaiPhong;
    private int soGiuongToiDa;
    private int soGiuongDangO;
    private long donGia;
    private String trangThai;

    public Phong() {
    }

    public Phong(int id, String maPhong, String khuNha, String loaiPhong,
                 int soGiuongToiDa, int soGiuongDangO, long donGia, String trangThai) {
        this.id = id;
        this.maPhong = maPhong;
        this.khuNha = khuNha;
        this.loaiPhong = loaiPhong;
        this.soGiuongToiDa = soGiuongToiDa;
        this.soGiuongDangO = soGiuongDangO;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    public String getKhuNha() { return khuNha; }
    public void setKhuNha(String khuNha) { this.khuNha = khuNha; }
    public String getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }
    public int getSoGiuongToiDa() { return soGiuongToiDa; }
    public void setSoGiuongToiDa(int soGiuongToiDa) { this.soGiuongToiDa = soGiuongToiDa; }
    public int getSoGiuongDangO() { return soGiuongDangO; }
    public void setSoGiuongDangO(int soGiuongDangO) { this.soGiuongDangO = soGiuongDangO; }
    public long getDonGia() { return donGia; }
    public void setDonGia(long donGia) { this.donGia = donGia; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return maPhong;
    }
}
