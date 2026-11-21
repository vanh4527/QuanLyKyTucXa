/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlykytucxa.controller;

import com.mycompany.quanlykytucxa.view.MainView;
import com.mycompany.quanlykytucxa.view.ResidentView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class MainController {

    private ResidentView residentView;
    private MainView mainView;

    public MainController(MainView view) {
        this.mainView = view;
        // Kết nối nút "Quản lý Phòng KTX"
        view.addChooseRoomListener(new ChooseRoomListener());
        // Kết nối nút "Quản lý Sinh viên"
        view.addChooseResidentsListener(new ChooseResidentListener());
    }

    public void showMainView() {
        mainView.setVisible(true);
    }

    // Sự kiện mở màn hình Quản lý Phòng KTX (chờ TV2 ghép PhongView & PhongController)
    class ChooseRoomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(mainView,
                    "Chức năng Quản lý Phòng KTX đang được Thành viên 2 hoàn thiện!");
        }
    }

    // Sự kiện mở màn hình Quản lý Sinh viên
    class ChooseResidentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            residentView = new ResidentView();
            ResidentController residentController = new ResidentController(residentView);
            residentController.showManagerView();
            mainView.setVisible(false);
        }
    }
}
