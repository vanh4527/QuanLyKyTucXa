/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlykytucxa.controller;

import com.mycompany.quanlykytucxa.action.CheckLogin;
import com.mycompany.quanlykytucxa.entity.User;
import com.mycompany.quanlykytucxa.view.LoginView;
import com.mycompany.quanlykytucxa.view.MainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author PC
 */
public class LoginController {

    private CheckLogin checkLogin;
    private LoginView loginView;
    private MainView mainView;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.checkLogin = new CheckLogin();
        view.addLoginListener(new LoginListener());
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (checkLogin.checkUser(user)) {
                mainView = new MainView();
                MainController mainController = new MainController(mainView);
                mainController.showMainView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        }
    }
}
