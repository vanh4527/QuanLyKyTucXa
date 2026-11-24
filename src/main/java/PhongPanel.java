package ktx.ui;

import ktx.model.Phong;
import ktx.model.ValidationException;
import ktx.service.PhongManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Giao diện quản lý Phòng: form nhập (có dropdown), JTable hiển thị, nút Thêm / Sửa / Xóa / Làm mới.
 * Là một JPanel nên có thể gắn vào JTabbedPane của màn hình chính (thành viên 1).
 */
public class PhongPanel extends JPanel {
    private static final DecimalFormat TIEN =
            new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.forLanguageTag("vi-VN")));

    private final PhongManager manager;

    private final JTextField txtMa = new JTextField(10);
    private final JComboBox<String> cboKhu = new JComboBox<>(Phong.DS_KHU);
    private final JComboBox<String> cboLoai = new JComboBox<>(Phong.DS_LOAI);
    private final JTextField txtSoGiuong = new JTextField(10);
    private final JTextField txtDonGia = new JTextField(10);

    private final JButton btnThem = new JButton("Thêm");
    private final JButton btnSua = new JButton("Sửa");
    private final JButton btnXoa = new JButton("Xóa");
    private final JButton btnLamMoi = new JButton("Làm mới");

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã phòng", "Khu", "Loại phòng", "Số giường", "Giường trống", "Đơn giá (VNĐ)"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);

    public PhongPanel(PhongManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(taoFormPanel(), BorderLayout.NORTH);
        add(taoBangPanel(), BorderLayout.CENTER);

        taoSuKien();
        napBang();
        datCheDo(false);
    }

    // ------------------------------------------------------------------
    // Dựng giao diện
    // ------------------------------------------------------------------

    private JPanel taoFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Thông tin phòng",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 8, 5, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        themDong(form, c, 0, 0, "Mã phòng:", txtMa);
        themDong(form, c, 0, 2, "Khu:", cboKhu);
        themDong(form, c, 1, 0, "Loại phòng:", cboLoai);
        themDong(form, c, 1, 2, "Số giường:", txtSoGiuong);
        themDong(form, c, 2, 0, "Đơn giá (VNĐ/tháng):", txtDonGia);

        JPanel nut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        nut.add(btnThem);
        nut.add(btnSua);
        nut.add(btnXoa);
        nut.add(btnLamMoi);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        form.add(nut, c);

        return form;
    }

    private void themDong(JPanel p, GridBagConstraints c, int row, int col, String nhan, JComponent o) {
        c.gridwidth = 1;
        c.weightx = 0;
        c.gridy = row;
        c.gridx = col;
        p.add(new JLabel(nhan), c);
        c.gridx = col + 1;
        c.weightx = 1;
        p.add(o, c);
    }

    private JScrollPane taoBangPanel() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRendererPhai phai = new DefaultTableCellRendererPhai();
        table.getColumnModel().getColumn(3).setCellRenderer(phai);
        table.getColumnModel().getColumn(4).setCellRenderer(phai);
        table.getColumnModel().getColumn(5).setCellRenderer(phai);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách phòng"));
        return sp;
    }

    /** Canh phải cho các cột số. */
    private static class DefaultTableCellRendererPhai extends javax.swing.table.DefaultTableCellRenderer {
        DefaultTableCellRendererPhai() {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    // ------------------------------------------------------------------
    // Sự kiện
    // ------------------------------------------------------------------

    private void taoSuKien() {
        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnLamMoi.addActionListener(e -> lamMoi());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiDongDuocChon();
            }
        });
    }

    private void xuLyThem() {
        try {
            Phong p = PhongManager.kiemTraVaTao(
                    txtMa.getText(), (String) cboKhu.getSelectedItem(), (String) cboLoai.getSelectedItem(),
                    txtSoGiuong.getText(), txtDonGia.getText());
            manager.them(p);
            napBang();
            lamMoi();
            JOptionPane.showMessageDialog(this, "Đã thêm phòng " + p.getMaPhong() + ".");
        } catch (ValidationException ex) {
            baoLoi(ex.getMessage());
        } catch (IOException ex) {
            baoLoi("Lỗi lưu file: " + ex.getMessage());
        }
    }

    private void xuLySua() {
        String ma = txtMa.getText().trim();
        if (ma.isEmpty()) {
            baoLoi("Hãy chọn một phòng trong bảng để sửa.");
            return;
        }
        try {
            Phong p = PhongManager.kiemTraVaTao(
                    ma, (String) cboKhu.getSelectedItem(), (String) cboLoai.getSelectedItem(),
                    txtSoGiuong.getText(), txtDonGia.getText());
            manager.sua(p);
            napBang();
            lamMoi();
            JOptionPane.showMessageDialog(this, "Đã cập nhật phòng " + p.getMaPhong() + ".");
        } catch (ValidationException ex) {
            baoLoi(ex.getMessage());
        } catch (IOException ex) {
            baoLoi("Lỗi lưu file: " + ex.getMessage());
        }
    }

    private void xuLyXoa() {
        String ma = txtMa.getText().trim();
        if (ma.isEmpty()) {
            baoLoi("Hãy chọn một phòng trong bảng để xóa.");
            return;
        }
        int kq = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phòng " + ma + "?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (kq != JOptionPane.YES_OPTION) return;

        try {
            manager.xoa(ma);
            napBang();
            lamMoi();
        } catch (ValidationException ex) {
            baoLoi(ex.getMessage());
        } catch (IOException ex) {
            baoLoi("Lỗi lưu file: " + ex.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Hỗ trợ
    // ------------------------------------------------------------------

    /** Nạp lại bảng từ danh sách trong PhongManager. */
    public void napBang() {
        model.setRowCount(0);
        for (Phong p : manager.getDanhSach()) {
            model.addRow(new Object[]{
                    p.getMaPhong(), p.getKhu(), p.getLoaiPhong(),
                    p.getSoGiuong(), p.getSoGiuongTrong(), TIEN.format(p.getDonGia())
            });
        }
    }

    private void hienThiDongDuocChon() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) return;
        int r = table.convertRowIndexToModel(viewRow);

        txtMa.setText(model.getValueAt(r, 0).toString());
        cboKhu.setSelectedItem(model.getValueAt(r, 1));
        cboLoai.setSelectedItem(model.getValueAt(r, 2));
        txtSoGiuong.setText(model.getValueAt(r, 3).toString());
        txtDonGia.setText(model.getValueAt(r, 5).toString());
        datCheDo(true);
    }

    private void lamMoi() {
        table.clearSelection();
        txtMa.setText("");
        txtSoGiuong.setText("");
        txtDonGia.setText("");
        cboKhu.setSelectedIndex(0);
        cboLoai.setSelectedIndex(0);
        datCheDo(false);
        txtMa.requestFocusInWindow();
    }

    /** dangChon = true: đang xem/sửa một phòng (khóa mã phòng, tắt nút Thêm). */
    private void datCheDo(boolean dangChon) {
        txtMa.setEditable(!dangChon);
        btnThem.setEnabled(!dangChon);
        btnSua.setEnabled(dangChon);
        btnXoa.setEnabled(dangChon);
    }

    private void baoLoi(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
