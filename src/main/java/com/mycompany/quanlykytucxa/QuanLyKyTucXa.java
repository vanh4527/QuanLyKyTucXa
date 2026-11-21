/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.quanlykytucxa;

import com.mycompany.quanlykytucxa.controller.LoginController;
import com.mycompany.quanlykytucxa.view.LoginView;
import java.awt.EventQueue;

/**
 *
 * @author PC
 */
public class QuanLyKyTucXa {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView view = new LoginView();
                LoginController controller = new LoginController(view);
                controller.showLoginView();
            }
        });
    }
}
