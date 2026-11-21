/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlykytucxa.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@XmlRootElement(name = "Students")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentXML {
    private List<Students> students;

    public List<Students> getResidents() {
        return students;
    }

    public void setResidents(List<Students> residents) {
        this.students = residents;
    } 
}
