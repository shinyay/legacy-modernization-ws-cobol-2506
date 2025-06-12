package com.syllabus;

import com.syllabus.dao.FileSyllabusDAO;
import com.syllabus.dao.SyllabusDAO;
import com.syllabus.service.SyllabusService;
import com.syllabus.ui.MainMenu;

public class SyllabusManagementSystem {
    public static void main(String[] args) {
        try {
            SyllabusDAO syllabusDAO = new FileSyllabusDAO();
            SyllabusService syllabusService = new SyllabusService(syllabusDAO);
            MainMenu mainMenu = new MainMenu(syllabusService);
            
            System.out.println("シラバス管理システムを開始します...");
            mainMenu.start();
        } catch (Exception e) {
            System.err.println("システム開始エラー: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
