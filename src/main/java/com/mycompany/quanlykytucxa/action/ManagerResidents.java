/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlykytucxa.action;

import com.mycompany.quanlykytucxa.entity.StudentXML;
import com.mycompany.quanlykytucxa.entity.Students;
import com.mycompany.quanlykytucxa.utils.FileUtils;
import com.mycompany.quanlykytucxa.view.ResidentView;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ManagerResidents {

    private static final String RESIDENT_FILE_NAME = "Students.xml";
    private List<Students> listResidents;
    private ResidentView residentView;

    public ManagerResidents() {
        this.listResidents = readListResidents();
        if (listResidents == null) {
            listResidents = new ArrayList<Students>();
        }
    }

    public List<Students> readListResidents() {
        List<Students> list = new ArrayList<Students>();
        StudentXML residentXML = (StudentXML) FileUtils.readXMLFile(RESIDENT_FILE_NAME, StudentXML.class);
        if (residentXML != null && residentXML.getResidents() != null) {
            list = residentXML.getResidents();
        }
        return list;
    }

    public void writeListResidents(List<Students> residents) {
        StudentXML residentXML = new StudentXML();
        residentXML.setResidents(residents);
        FileUtils.writeXMLtoFile(RESIDENT_FILE_NAME, residentXML);
    }

    public List<Students> searchResidentName(String search) {
        List<Students> temp = new ArrayList<Students>();
        for (Students person : listResidents) {
            if (person.getName() != null && person.getName().toLowerCase().contains(search.toLowerCase())) {
                temp.add(person);
            }
        }
        return temp;
    }

    public List<Students> searchResidentAddress(String search) {
        List<Students> temp = new ArrayList<Students>();
        for (Students person : listResidents) {
            if (person.getAddress() != null && person.getAddress().toLowerCase().contains(search.toLowerCase())) {
                temp.add(person);
            }
        }
        return temp;
    }

    public List<Students> searchResidentIDFamily(String search) {
        List<Students> temp = new ArrayList<Students>();
        for (Students person : listResidents) {
            if (person.getIDFamily() != null && person.getIDFamily().contains(search)) {
                temp.add(person);
            }
        }
        return temp;
    }

    public List<Students> searchResidentYear(String year) {
        List<Students> temp = new ArrayList<>();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        for (Students person : listResidents) {
            if (person.getBirthday() != null) {
                String personYearStr = yearFormat.format(person.getBirthday());
                if (personYearStr.equals(year)) {
                    temp.add(person);
                }
            }
        }
        return temp;
    }

    public void add(Students resident) {
        int max = 0;
        for (int i = 0; i < listResidents.size(); i++) {
            if (listResidents.get(i).getId() > max) {
                max = listResidents.get(i).getId();
            }
        }
        resident.setId(max + 1);
        listResidents.add(resident);
        writeListResidents(listResidents);
    }

    public boolean isCMTUnique(Students resident) {
        String cmt = resident.getCMT();
        for (Students existingResident : listResidents) {
            if (existingResident.getCMT() != null && existingResident.getCMT().equals(cmt)) {
                return false;
            }
        }
        return true;
    }

    public boolean isHouseholdUnique(Students resident) {
        String IDFamily = resident.getIDFamily();
        String role = resident.getRole();
        for (Students existingResident : listResidents) {
            if ("Chủ hộ".equals(role) && existingResident.getIDFamily() != null
                    && existingResident.getIDFamily().equals(IDFamily)
                    && existingResident.getRole() != null
                    && existingResident.getRole().equals(role)) {
                return false;
            }
        }
        return true;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(residentView, message);
    }

    public void edit(Students resident) throws ParseException {
        int size = listResidents.size();
        for (int i = 0; i < size; i++) {
            if (listResidents.get(i).getId() == resident.getId()) {
                listResidents.get(i).setIDFamily(resident.getIDFamily());
                listResidents.get(i).setName(resident.getName());
                listResidents.get(i).setRole(resident.getRole());
                listResidents.get(i).setBirthday(resident.getBirthday());
                listResidents.get(i).setAddress(resident.getAddress());
                listResidents.get(i).setSex(resident.getSex());
                listResidents.get(i).setTypeCMT(resident.getTypeCMT());
                listResidents.get(i).setCMT(resident.getCMT());
                listResidents.get(i).setBirthPlace(resident.getBirthPlace());
                listResidents.get(i).setPhoneNumber(resident.getPhoneNumber());

                writeListResidents(listResidents);
                break;
            }
        }
    }

    public boolean delete(Students resident) {
        boolean isFound = false;
        int size = listResidents.size();
        for (int i = 0; i < size; i++) {
            if (listResidents.get(i).getId() == resident.getId()) {
                listResidents.remove(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            for (int i = 0; i < listResidents.size(); i++) {
                if (listResidents.get(i).getId() > resident.getId()) {
                    listResidents.get(i).setId(listResidents.get(i).getId() - 1);
                }
            }
            writeListResidents(listResidents);
            return true;
        }
        return false;
    }

    public void sortResidentsByName() {
        Collections.sort(listResidents, new Comparator<Students>() {
            @Override
            public int compare(Students p1, Students p2) {
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                int result = collator.compare(p1.getLastName(), p2.getLastName());
                if (result == 0) {
                    result = collator.compare(p1.getFirstName(), p2.getFirstName());
                }
                return result;
            }
        });
    }

    public void sortResidentsByIDFamily() {
        Collections.sort(listResidents, new Comparator<Students>() {
            @Override
            public int compare(Students person1, Students person2) {
                return person1.getIDFamily().compareTo(person2.getIDFamily());
            }
        });
    }

    public void sortResidentsByID() {
        Collections.sort(listResidents, new Comparator<Students>() {
            @Override
            public int compare(Students person1, Students person2) {
                return Integer.compare(person1.getId(), person2.getId());
            }
        });
    }

    public List<Students> getListResidents() {
        return this.listResidents;
    }
}
