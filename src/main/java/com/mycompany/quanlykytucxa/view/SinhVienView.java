package com.mycompany.quanlykytucxa.view;

import com.mycompany.quanlykytucxa.controller.SinhVienController;
import com.mycompany.quanlykytucxa.entity.SinhVien;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

/**
 * Giao diện Quản lý Sinh viên (Java Swing).
 * - Ngày sinh: chọn bằng lịch (JSpinner + SpinnerDateModel dùng Calendar)
 * - Giới tính / Khoa: chọn bằng Dropdown (JComboBox)
 * - Danh sách hiển thị bằng JTable, bấm vào 1 dòng để đổ dữ liệu lên form
 * - Thêm / Sửa / Xóa / Hiển thị, Xóa có hộp thoại xác nhận
 */
public class SinhVienView extends JFrame {

    private final SinhVienController controller = new SinhVienController();
    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    private JTextField txtMaSV, txtHoTen, txtLop, txtCCCD, txtSDT, txtDiaChi, txtMaPhong;
    private JSpinner spnNgaySinh;
    private JComboBox<String> cboGioiTinh, cboKhoa;
    private JTable tblSinhVien;
    private DefaultTableModel model;

    public SinhVienView() {
        setTitle("Quản lý Sinh viên - Ký túc xá");
        setSize(950, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(taoPanelNhap(), BorderLayout.NORTH);
        add(taoPanelBang(), BorderLayout.CENTER);
        add(taoPanelNut(), BorderLayout.SOUTH);

        hienThiDanhSach();
    }

    // ---------- Tạo giao diện ----------
    private JPanel taoPanelNhap() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;

        txtMaSV = new JTextField(12);
        txtHoTen = new JTextField(20);
        txtLop = new JTextField(12);
        txtCCCD = new JTextField(14);
        txtSDT = new JTextField(14);
        txtDiaChi = new JTextField(20);
        txtMaPhong = new JTextField(12);

        // Ô chọn Ngày sinh dạng lịch: SpinnerDateModel hoạt động dựa trên Calendar
        SpinnerDateModel modelNgay = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnNgaySinh = new JSpinner(modelNgay);
        spnNgaySinh.setEditor(new JSpinner.DateEditor(spnNgaySinh, "dd/MM/yyyy"));

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cboKhoa = new JComboBox<>(new String[]{
            "Công nghệ thông tin", "Kinh tế", "Ngoại ngữ", "Cơ khí", "Điện - Điện tử", "Xây dựng"
        });

        themDong(panel, c, 0, "Mã sinh viên *:", txtMaSV, "Họ tên *:", txtHoTen);
        themDong(panel, c, 1, "Ngày sinh:", spnNgaySinh, "Giới tính:", cboGioiTinh);
        themDong(panel, c, 2, "Số CCCD *:", txtCCCD, "Số điện thoại:", txtSDT);
        themDong(panel, c, 3, "Khoa:", cboKhoa, "Lớp:", txtLop);
        themDong(panel, c, 4, "Địa chỉ:", txtDiaChi, "Mã phòng:", txtMaPhong);
        return panel;
    }

    private void themDong(JPanel panel, GridBagConstraints c, int row,
                          String nhan1, java.awt.Component o1,
                          String nhan2, java.awt.Component o2) {
        c.gridy = row;
        c.gridx = 0;
        panel.add(new JLabel(nhan1), c);
        c.gridx = 1;
        panel.add(o1, c);
        c.gridx = 2;
        panel.add(new JLabel(nhan2), c);
        c.gridx = 3;
        panel.add(o2, c);
    }

    private JScrollPane taoPanelBang() {
        String[] cot = {"Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Khoa",
            "Lớp", "CCCD", "SĐT", "Địa chỉ", "Mã phòng"};
        model = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // không cho sửa trực tiếp trên bảng
            }
        };
        tblSinhVien = new JTable(model);
        // Bấm vào 1 dòng -> đổ dữ liệu của sinh viên đó lên form
        tblSinhVien.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int dong = tblSinhVien.getSelectedRow();
                if (dong >= 0) {
                    doDuLieuLenForm(controller.layDanhSach().get(dong));
                }
            }
        });
        return new JScrollPane(tblSinhVien);
    }

    private JPanel taoPanelNut() {
        JPanel panel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        btnThem.addActionListener(e -> themSinhVien());
        btnSua.addActionListener(e -> suaSinhVien());
        btnXoa.addActionListener(e -> xoaSinhVien());
        btnLamMoi.addActionListener(e -> lamMoiForm());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);
        return panel;
    }

    // ---------- Đọc / đổ dữ liệu ----------
    private SinhVien docTuForm() {
        String ngaySinh = dinhDangNgay.format((Date) spnNgaySinh.getValue());
        return new SinhVien(
                txtMaSV.getText().trim(),
                txtHoTen.getText().trim(),
                ngaySinh,
                (String) cboGioiTinh.getSelectedItem(),
                txtCCCD.getText().trim(),
                txtSDT.getText().trim(),
                txtDiaChi.getText().trim(),
                (String) cboKhoa.getSelectedItem(),
                txtLop.getText().trim(),
                txtMaPhong.getText().trim()
        );
    }

    private void doDuLieuLenForm(SinhVien sv) {
        txtMaSV.setText(sv.getMaSinhVien());
        txtHoTen.setText(sv.getHoTen());
        try {
            spnNgaySinh.setValue(dinhDangNgay.parse(sv.getNgaySinh()));
        } catch (ParseException ex) {
            spnNgaySinh.setValue(new Date());
        }
        cboGioiTinh.setSelectedItem(sv.getGioiTinh());
        txtCCCD.setText(sv.getSoCCCD());
        txtSDT.setText(sv.getSoDienThoai());
        txtDiaChi.setText(sv.getDiaChi());
        cboKhoa.setSelectedItem(sv.getKhoa());
        txtLop.setText(sv.getLop());
        txtMaPhong.setText(sv.getMaPhong());
    }

    private void hienThiDanhSach() {
        model.setRowCount(0);
        for (SinhVien sv : controller.layDanhSach()) {
            model.addRow(new Object[]{
                sv.getMaSinhVien(), sv.getHoTen(), sv.getNgaySinh(), sv.getGioiTinh(),
                sv.getKhoa(), sv.getLop(), sv.getSoCCCD(), sv.getSoDienThoai(),
                sv.getDiaChi(), sv.getMaPhong()
            });
        }
    }

    private void lamMoiForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        spnNgaySinh.setValue(new Date());
        cboGioiTinh.setSelectedIndex(0);
        txtCCCD.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        cboKhoa.setSelectedIndex(0);
        txtLop.setText("");
        txtMaPhong.setText("");
        tblSinhVien.clearSelection();
        txtMaSV.requestFocus();
    }

    // ---------- Xử lý nút bấm ----------
    private void themSinhVien() {
        String loi = controller.themSinhVien(docTuForm());
        if (loi != null) {
            JOptionPane.showMessageDialog(this, loi, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        hienThiDanhSach();
        lamMoiForm();
        JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
    }

    private void suaSinhVien() {
        if (controller.timTheoMa(txtMaSV.getText()) == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 sinh viên trong bảng để sửa!",
                    "Chưa chọn sinh viên", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String loi = controller.suaSinhVien(docTuForm());
        if (loi != null) {
            JOptionPane.showMessageDialog(this, loi, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        hienThiDanhSach();
        JOptionPane.showMessageDialog(this, "Sửa sinh viên thành công!");
    }

    private void xoaSinhVien() {
        String maSV = txtMaSV.getText().trim();
        SinhVien sv = controller.timTheoMa(maSV);
        if (sv == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 sinh viên trong bảng để xóa!",
                    "Chưa chọn sinh viên", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Xóa phải có hộp thoại xác nhận
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa sinh viên " + sv.getMaSinhVien() + " - " + sv.getHoTen() + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (chon != JOptionPane.YES_OPTION) {
            return;
        }
        String loi = controller.xoaSinhVien(maSV);
        if (loi != null) {
            JOptionPane.showMessageDialog(this, loi, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        hienThiDanhSach();
        lamMoiForm();
        JOptionPane.showMessageDialog(this, "Đã xóa sinh viên!");
    }

    // Chạy thử độc lập cửa sổ Sinh viên (khi ghép vào MainView thì gọi new SinhVienView().setVisible(true))
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new SinhVienView().setVisible(true));
    }
}
