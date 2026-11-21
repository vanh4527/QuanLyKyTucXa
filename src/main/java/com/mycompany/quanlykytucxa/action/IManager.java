package com.mycompany.quanlykytucxa.action;

import java.text.ParseException;
import java.util.List;

/**
 * Interface chuẩn cho các lớp quản lý nghiệp vụ (OOP - Tính trừu tượng & Đa hình)
 */
public interface IManager<T> {
    void add(T item);
    void edit(T item) throws ParseException;
    boolean delete(T item);
    List<T> getAll();
}
