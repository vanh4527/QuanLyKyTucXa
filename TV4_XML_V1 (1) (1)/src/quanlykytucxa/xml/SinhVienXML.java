package quanlykytucxa.xml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import quanlykytucxa.entity.SinhVien;

/**
 * Đọc/ghi danh sách sinh viên ra file data/sinhvien.xml (chỉ dùng XML).
 * Ngày sinh lưu dạng dd/MM/yyyy.
 *
 * Cách dùng cho TV3 (SinhVienController):
 *   - Khi mở form:        List<SinhVien> ds = SinhVienXML.docFile();
 *   - Sau Thêm/Sửa/Xóa:   SinhVienXML.ghiFile(ds);
 */
public class SinhVienXML {
    public static final String FILE_PATH = "data/sinhvien.xml";
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private SinhVienXML() {
    }

    public static List<SinhVien> docFile() {
        List<SinhVien> ds = new ArrayList<>();
        try {
            Document doc = XmlUtils.doc(FILE_PATH);
            if (doc == null) {
                return ds;
            }
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            NodeList nl = doc.getElementsByTagName("sinhVien");
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                SinhVien sv = new SinhVien();
                sv.setId(XmlUtils.so(e.getAttribute("id")));
                sv.setMaSV(XmlUtils.text(e, "maSV"));
                sv.setHoTen(XmlUtils.text(e, "hoTen"));
                try {
                    sv.setNgaySinh(df.parse(XmlUtils.text(e, "ngaySinh")));
                } catch (Exception ex) {
                    sv.setNgaySinh(null);
                }
                sv.setGioiTinh(XmlUtils.text(e, "gioiTinh"));
                sv.setSoCCCD(XmlUtils.text(e, "soCCCD"));
                sv.setSoDienThoai(XmlUtils.text(e, "soDienThoai"));
                sv.setKhoa(XmlUtils.text(e, "khoa"));
                sv.setPhongId(XmlUtils.so(XmlUtils.text(e, "phongId")));
                ds.add(sv);
            }
        } catch (Exception ex) {
            System.err.println("Lỗi đọc " + FILE_PATH + ": " + ex.getMessage());
        }
        return ds;
    }

    public static boolean ghiFile(List<SinhVien> ds) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            Document doc = XmlUtils.taoMoi();
            Element root = doc.createElement("danhSachSinhVien");
            doc.appendChild(root);
            for (SinhVien sv : ds) {
                Element e = doc.createElement("sinhVien");
                e.setAttribute("id", String.valueOf(sv.getId()));
                XmlUtils.them(doc, e, "maSV", sv.getMaSV());
                XmlUtils.them(doc, e, "hoTen", sv.getHoTen());
                Date ns = sv.getNgaySinh();
                XmlUtils.them(doc, e, "ngaySinh", ns == null ? "" : df.format(ns));
                XmlUtils.them(doc, e, "gioiTinh", sv.getGioiTinh());
                XmlUtils.them(doc, e, "soCCCD", sv.getSoCCCD());
                XmlUtils.them(doc, e, "soDienThoai", sv.getSoDienThoai());
                XmlUtils.them(doc, e, "khoa", sv.getKhoa());
                XmlUtils.them(doc, e, "phongId", String.valueOf(sv.getPhongId()));
                root.appendChild(e);
            }
            XmlUtils.ghi(doc, FILE_PATH);
            return true;
        } catch (Exception ex) {
            System.err.println("Lỗi ghi " + FILE_PATH + ": " + ex.getMessage());
            return false;
        }
    }
}
