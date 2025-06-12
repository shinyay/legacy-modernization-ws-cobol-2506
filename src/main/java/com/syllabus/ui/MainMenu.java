package com.syllabus.ui;

import com.syllabus.service.SyllabusService;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private final SyllabusService syllabusService;
    private final SyllabusRegistration registration;
    private final SyllabusUpdate update;
    private final SyllabusDelete delete;
    private final SyllabusQuery query;
    private final SyllabusList list;
    private final SyllabusReport report;
    
    public MainMenu(SyllabusService syllabusService) {
        this.scanner = new Scanner(System.in);
        this.syllabusService = syllabusService;
        this.registration = new SyllabusRegistration(syllabusService, scanner);
        this.update = new SyllabusUpdate(syllabusService, scanner);
        this.delete = new SyllabusDelete(syllabusService, scanner);
        this.query = new SyllabusQuery(syllabusService, scanner);
        this.list = new SyllabusList(syllabusService, scanner);
        this.report = new SyllabusReport(syllabusService, scanner);
    }
    
    public void start() {
        boolean exit = false;
        
        while (!exit) {
            displayMainMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    callSyllabusRegister();
                    break;
                case 2:
                    callSyllabusUpdate();
                    break;
                case 3:
                    callSyllabusDelete();
                    break;
                case 4:
                    callSyllabusQuery();
                    break;
                case 5:
                    callSyllabusList();
                    break;
                case 6:
                    callReportGenerate();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("無効な選択です");
            }
        }
        
        System.out.println("シラバス管理システムを終了します。");
    }
    
    private void displayMainMenu() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("シラバス管理システム");
        System.out.println("==============================");
        System.out.println(" 1. シラバス登録");
        System.out.println(" 2. シラバス更新");
        System.out.println(" 3. シラバス削除");
        System.out.println(" 4. シラバス照会");
        System.out.println(" 5. シラバス一覧");
        System.out.println(" 6. レポート作成");
        System.out.println(" 9. 終了");
        System.out.println("==============================");
        System.out.println();
        System.out.print("選択 (1-6, 9): ");
    }
    
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void callSyllabusRegister() {
        try {
            registration.execute();
        } catch (Exception e) {
            System.out.println("プログラム呼出エラー");
        }
    }
    
    private void callSyllabusUpdate() {
        try {
            update.execute();
        } catch (Exception e) {
            System.out.println("プログラム呼出エラー");
        }
    }
    
    private void callSyllabusDelete() {
        try {
            delete.execute();
        } catch (Exception e) {
            System.out.println("プログラム呼出エラー");
        }
    }
    
    private void callSyllabusQuery() {
        try {
            query.execute();
        } catch (Exception e) {
            System.out.println("プログラム呼出エラー");
        }
    }
    
    private void callSyllabusList() {
        try {
            list.execute();
        } catch (Exception e) {
            System.out.println("プログラム呼出エラー");
        }
    }
    
    private void callReportGenerate() {
        try {
            report.execute();
        } catch (Exception e) {
            System.out.println("プログラム呼出エラー");
        }
    }
}
