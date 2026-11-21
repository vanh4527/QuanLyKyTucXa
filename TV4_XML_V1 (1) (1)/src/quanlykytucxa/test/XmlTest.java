package quanlykytucxa.test;

import java.util.List;
import quanlykytucxa.entity.Phong;
import quanlykytucxa.entity.SinhVien;
import quanlykytucxa.xml.PhongXML;
import quanlykytucxa.xml.SinhVienXML;

/** Chạy thử: đọc 2 file XML, in ra, ghi lại rồi đọc lại để kiểm tra. */
public class XmlTest {
    public static void main(String[] args) {
        List<Phong> dsP = PhongXML.docFile();
        System.out.println("Số phòng: " + dsP.size() + " | " + dsP.get(0).getLoaiPhong());
        PhongXML.ghiFile(dsP);
        System.out.println("Đọc lại sau khi ghi: " + PhongXML.docFile().size());

        List<SinhVien> dsS = SinhVienXML.docFile();
        System.out.println("Số sinh viên: " + dsS.size() + " | " + dsS.get(0).getHoTen());
        SinhVienXML.ghiFile(dsS);
        System.out.println("Đọc lại sau khi ghi: " + SinhVienXML.docFile().size());
    }
}
