package quanlykytucxa.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import quanlykytucxa.entity.Phong;

/**
 * Đọc/ghi danh sách phòng ra file data/phong.xml (chỉ dùng XML).
 *
 * Cách dùng cho TV2 (PhongController):
 *   - Khi mở form:        List<Phong> ds = PhongXML.docFile();  rồi đổ lên JTable
 *   - Sau Thêm/Sửa/Xóa:   PhongXML.ghiFile(ds);
 */
public class PhongXML {
    public static final String FILE_PATH = "data/phong.xml";

    private PhongXML() {
    }

    public static List<Phong> docFile() {
        List<Phong> ds = new ArrayList<>();
        try {
            Document doc = XmlUtils.doc(FILE_PATH);
            if (doc == null) {
                return ds; // chưa có file -> danh sách rỗng
            }
            NodeList nl = doc.getElementsByTagName("phong");
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                Phong p = new Phong();
                p.setId(XmlUtils.so(e.getAttribute("id")));
                p.setMaPhong(XmlUtils.text(e, "maPhong"));
                p.setKhuNha(XmlUtils.text(e, "khuNha"));
                p.setLoaiPhong(XmlUtils.text(e, "loaiPhong"));
                p.setSoGiuongToiDa(XmlUtils.so(XmlUtils.text(e, "soGiuongToiDa")));
                p.setSoGiuongDangO(XmlUtils.so(XmlUtils.text(e, "soGiuongDangO")));
                p.setDonGia(XmlUtils.soLong(XmlUtils.text(e, "donGia")));
                p.setTrangThai(XmlUtils.text(e, "trangThai"));
                ds.add(p);
            }
        } catch (Exception ex) {
            System.err.println("Lỗi đọc " + FILE_PATH + ": " + ex.getMessage());
        }
        return ds;
    }

    /** Ghi đè toàn bộ danh sách ra file. Trả về true nếu ghi thành công. */
    public static boolean ghiFile(List<Phong> ds) {
        try {
            Document doc = XmlUtils.taoMoi();
            Element root = doc.createElement("danhSachPhong");
            doc.appendChild(root);
            for (Phong p : ds) {
                Element e = doc.createElement("phong");
                e.setAttribute("id", String.valueOf(p.getId()));
                XmlUtils.them(doc, e, "maPhong", p.getMaPhong());
                XmlUtils.them(doc, e, "khuNha", p.getKhuNha());
                XmlUtils.them(doc, e, "loaiPhong", p.getLoaiPhong());
                XmlUtils.them(doc, e, "soGiuongToiDa", String.valueOf(p.getSoGiuongToiDa()));
                XmlUtils.them(doc, e, "soGiuongDangO", String.valueOf(p.getSoGiuongDangO()));
                XmlUtils.them(doc, e, "donGia", String.valueOf(p.getDonGia()));
                XmlUtils.them(doc, e, "trangThai", p.getTrangThai());
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
