/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlykytucxa.controller;

import com.mycompany.quanlykytucxa.action.ManagerResidents;
import com.mycompany.quanlykytucxa.entity.Students;
import com.mycompany.quanlykytucxa.view.MainView;
import com.mycompany.quanlykytucxa.view.ResidentView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author PC
 */
public class ResidentController {

    private ResidentView residentView;
    private MainView mainView;
    private ManagerResidents managerResidents;

    public ResidentController(ResidentView view) {
        this.residentView = view;
        this.managerResidents = new ManagerResidents();
        view.addUndoListener(new UndoListener());
        view.addAddResidentListener(new AddResidentListener());
        view.addListResidentSelectionListener(new ListResidentsSelectionListener());
        view.addEditResidentListener(new EditResidentListener());
        view.addClearListener(new ClearResidentListener());
        view.addDeleteSpecialPersonListener(new DeleteSpecialPersonListener());
        view.addSortSpecialPersonListener(new SortResidentsListener());
        view.addSearchListener(new SearchResidentViewListener());
        view.addSearchDialogListener(new SearchResidentListener());
        view.addCancelSearchResidentListener(new CancelSearchResidentListener());
        view.addCancelDialogListener(new CancelDialogSearchResidentListener());
    }

    public void showManagerView() {
        List<Students> residentsList = managerResidents.getListResidents();
        residentView.setVisible(true);
        residentView.showListResidents(residentsList);
        residentView.showCountListResidents(residentsList);
        residentView.showStatisticTypeCMT(residentsList);
        residentView.showStatisticIDFamily(residentsList);
    }

    class UndoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            residentView.setVisible(false);
        }
    }

    class AddResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Students residents = residentView.getResidentInfo();
            if (residents != null) {
                try {
                    if (!managerResidents.isCMTUnique(residents)) {
                        throw new IllegalArgumentException("Lỗi: Số chứng minh thư / CCCD đã tồn tại!");
                    }

                    if (!managerResidents.isHouseholdUnique(residents)) {
                        throw new IllegalArgumentException("Lỗi: Đã có chủ hộ đối với số hộ khẩu này");
                    }

                    managerResidents.add(residents);
                    residentView.showResidents(residents);
                    residentView.showListResidents(managerResidents.getListResidents());
                    residentView.showCountListResidents(managerResidents.getListResidents());
                    residentView.showStatisticTypeCMT(managerResidents.getListResidents());
                    residentView.showStatisticIDFamily(managerResidents.getListResidents());
                    residentView.showMessage("Thêm thành công!");
                } catch (IllegalArgumentException ex) {
                    residentView.showMessage(ex.getMessage());
                }
            }
        }
    }

    class EditResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Students resident = residentView.getResidentInfo();
            if (resident != null) {
                try {
                    managerResidents.edit(resident);
                } catch (ParseException ex) {
                    Logger.getLogger(ResidentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                residentView.showResidents(resident);
                residentView.showListResidents(managerResidents.getListResidents());
                residentView.showCountListResidents(managerResidents.getListResidents());
                residentView.showStatisticTypeCMT(managerResidents.getListResidents());
                residentView.showStatisticIDFamily(managerResidents.getListResidents());
                residentView.showMessage("Cập nhật thành công!");
            }
        }
    }

    class DeleteSpecialPersonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Students resident = residentView.getResidentInfo();
            if (resident != null) {
                managerResidents.delete(resident);
                residentView.clearResidentInfo();
                residentView.showListResidents(managerResidents.getListResidents());
                residentView.showCountListResidents(managerResidents.getListResidents());
                residentView.showStatisticTypeCMT(managerResidents.getListResidents());
                residentView.showStatisticIDFamily(managerResidents.getListResidents());
                residentView.showMessage("Xóa thành công!");
            }
        }
    }

    class ListResidentsSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            List<Students> residentsList = managerResidents.getListResidents();
            try {
                residentView.fillResidentFromSelectedRow(residentsList);
            } catch (ParseException ex) {
                Logger.getLogger(ResidentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class ClearResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            residentView.clearResidentInfo();
        }
    }

    class SortResidentsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int check = residentView.getChooseSelectSort();
            if (check == 1) {
                managerResidents.sortResidentsByID();
                residentView.showListResidents(managerResidents.getListResidents());
            } else if (check == 2) {
                managerResidents.sortResidentsByName();
                residentView.showListResidents(managerResidents.getListResidents());
            } else if (check == 3) {
                managerResidents.sortResidentsByIDFamily();
                residentView.showListResidents(managerResidents.getListResidents());
            } else {
                residentView.showMessage("Bạn chưa chọn tiêu chí sắp xếp");
            }
        }
    }

    class SearchResidentViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            residentView.searchResidentInfo();
        }
    }

    class CancelDialogSearchResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            residentView.cancelDialogSearchResidentInfo();
        }
    }

    class CancelSearchResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            residentView.showListResidents(managerResidents.getListResidents());
            residentView.cancelSearchResident();
        }
    }

    class SearchResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Students> temp = new ArrayList<>();
            int check = residentView.getChooseSelectSearch();
            String search = residentView.validateSearch();
            if (check == 1) {
                temp = managerResidents.searchResidentIDFamily(search);
            } else if (check == 2) {
                temp = managerResidents.searchResidentName(search);
            } else if (check == 3) {
                temp = managerResidents.searchResidentYear(search);
            } else if (check == 4) {
                temp = managerResidents.searchResidentAddress(search);
            }
            if (!temp.isEmpty()) {
                residentView.showListResidents(temp);
            } else {
                residentView.showMessage("Không tìm thấy kết quả!");
            }
        }
    }
}
