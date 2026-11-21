package quanlykytucxa.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


final class XmlUtils {
    private XmlUtils() {
    }

    /** Đọc file XML; trả về null nếu file chưa tồn tại. */
    static Document doc(String path) throws Exception {
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
    }

    static Document taoMoi() throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }

    static void ghi(Document doc, String path) throws Exception {
        File f = new File(path);
        if (f.getParentFile() != null) {
            f.getParentFile().mkdirs();
        }
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        t.transform(new DOMSource(doc), new StreamResult(f));
    }

    static Element them(Document doc, Element cha, String ten, String giaTri) {
        Element e = doc.createElement(ten);
        e.setTextContent(giaTri == null ? "" : giaTri);
        cha.appendChild(e);
        return e;
    }

    static String text(Element cha, String ten) {
        NodeList nl = cha.getElementsByTagName(ten);
        if (nl.getLength() == 0) {
            return "";
        }
        Node n = nl.item(0);
        return n.getTextContent().trim();
    }

    static int so(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    static long soLong(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            return 0L;
        }
    }
}
