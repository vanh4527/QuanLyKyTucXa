package ktx.storage;

import ktx.model.Phong;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/** Đọc / ghi danh sách phòng ra file phong.xml. */
public class PhongXMLStorage {
    private final File file;

    public PhongXMLStorage(String duongDan) {
        this.file = new File(duongDan);
    }

    /** Đọc file XML. File chưa tồn tại => danh sách rỗng. */
    public List<Phong> docFile() throws IOException {
        List<Phong> ds = new ArrayList<>();
        if (!file.exists() || file.length() == 0) {
            return ds;
        }
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(file);
            NodeList nodes = doc.getElementsByTagName("Phong");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);
                String ma = text(e, "maPhong");
                try {
                    ds.add(new Phong(
                            ma,
                            text(e, "khu"),
                            text(e, "loaiPhong"),
                            Integer.parseInt(text(e, "soGiuong")),
                            Integer.parseInt(text(e, "soGiuongTrong")),
                            Double.parseDouble(text(e, "donGia"))));
                } catch (NumberFormatException ex) {
                    throw new IOException("Dữ liệu số không hợp lệ ở phòng \"" + ma
                            + "\" trong " + file.getName());
                }
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Không đọc được file " + file.getName() + ": " + e.getMessage(), e);
        }
        return ds;
    }

    /** Ghi toàn bộ danh sách ra file (ghi file tạm rồi thay thế để tránh hỏng dữ liệu). */
    public void ghiFile(List<Phong> ds) throws IOException {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("DanhSachPhong");
            doc.appendChild(root);

            for (Phong p : ds) {
                Element e = doc.createElement("Phong");
                them(doc, e, "maPhong", p.getMaPhong());
                them(doc, e, "khu", p.getKhu());
                them(doc, e, "loaiPhong", p.getLoaiPhong());
                them(doc, e, "soGiuong", String.valueOf(p.getSoGiuong()));
                them(doc, e, "soGiuongTrong", String.valueOf(p.getSoGiuongTrong()));
                them(doc, e, "donGia", String.valueOf((long) p.getDonGia()));
                root.appendChild(e);
            }

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            File tmp = new File(file.getPath() + ".tmp");
            t.transform(new DOMSource(doc), new StreamResult(tmp));
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Không ghi được file " + file.getName() + ": " + e.getMessage(), e);
        }
    }

    private static void them(Document doc, Element cha, String ten, String giaTri) {
        Element c = doc.createElement(ten);
        c.setTextContent(giaTri == null ? "" : giaTri);
        cha.appendChild(c);
    }

    private static String text(Element e, String ten) {
        NodeList nl = e.getElementsByTagName(ten);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent().trim();
    }
}
