package com.syllabus.ui;

import com.syllabus.model.Syllabus;
import com.syllabus.service.SyllabusService;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SyllabusReport {
    private final SyllabusService syllabusService;
    private final Scanner scanner;
    private static final String REPORT_FILE = "syllabus_report.txt";
    private static final int RECORDS_PER_PAGE = 40;
    
    public SyllabusReport(SyllabusService syllabusService, Scanner scanner) {
        this.syllabusService = syllabusService;
        this.scanner = scanner;
    }
    
    public void execute() {
        try {
            int reportOption = getReportOption();
            String filter = getFilterValue(reportOption);
            String reportTitle = getReportTitle(reportOption, filter);
            
            List<Syllabus> syllabi = getSyllabi(reportOption, filter);
            
            generateReport(syllabi, reportTitle);
            
            System.out.println("レポートが正常に生成されました。");
            System.out.println("ファイル名: " + REPORT_FILE);
        } catch (Exception e) {
            System.out.println("レポート生成でエラーが発生しました: " + e.getMessage());
        }
    }
    
    private int getReportOption() {
        System.out.println();
        System.out.println("レポート生成");
        System.out.println("レポートの種類を選択してください:");
        System.out.println();
        System.out.println("1. 全シラバスレポート");
        System.out.println("2. 学部学科別シラバスレポート");
        System.out.println("3. 教員別シラバスレポート");
        System.out.println();
        System.out.print("選択 (1-3): ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("無効な選択です。全シラバスレポートを生成します。");
            return 1;
        }
    }
    
    private String getFilterValue(int reportOption) {
        switch (reportOption) {
            case 2:
                System.out.println();
                System.out.println("学部学科別レポート");
                System.out.print("学部学科コードを入力してください: ");
                return scanner.nextLine().trim();
            case 3:
                System.out.println();
                System.out.println("教員別レポート");
                System.out.print("教員IDを入力してください: ");
                return scanner.nextLine().trim();
            default:
                return "";
        }
    }
    
    private String getReportTitle(int reportOption, String filter) {
        switch (reportOption) {
            case 2:
                return "学部学科別レポート: " + filter;
            case 3:
                return "教員別レポート: " + filter;
            default:
                return "全シラバスレポート";
        }
    }
    
    private List<Syllabus> getSyllabi(int reportOption, String filter) throws Exception {
        switch (reportOption) {
            case 2:
                return syllabusService.getSyllabiByDepartment(filter);
            case 3:
                return syllabusService.getSyllabiByTeacher(filter);
            default:
                return syllabusService.getAllSyllabi();
        }
    }
    
    private void generateReport(List<Syllabus> syllabi, String reportTitle) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REPORT_FILE))) {
            int pageNumber = 1;
            int lineCount = 0;
            
            generateReportHeader(writer, reportTitle, pageNumber);
            lineCount = 5;
            
            for (Syllabus syllabus : syllabi) {
                if (lineCount >= RECORDS_PER_PAGE) {
                    pageNumber++;
                    generateNewPage(writer, reportTitle, pageNumber);
                    lineCount = 7;
                }
                
                generateDetailLine(writer, syllabus);
                lineCount++;
            }
            
            generateReportFooter(writer, syllabi.size());
        }
    }
    
    private void generateReportHeader(PrintWriter writer, String reportTitle, int pageNumber) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        writer.println("シラバス管理システム                    日付: " + currentDate + "     ページ:" + String.format("%3d", pageNumber));
        writer.println(reportTitle);
        writer.println("==================================================");
        writer.println("科目コード       科目名                学部  教員    学期  単位");
        writer.println("==================================================");
    }
    
    private void generateNewPage(PrintWriter writer, String reportTitle, int pageNumber) {
        writer.println();
        writer.println();
        generateReportHeader(writer, reportTitle, pageNumber);
    }
    
    private void generateDetailLine(PrintWriter writer, Syllabus syllabus) {
        writer.printf("%-10s  %-20s  %-4s  %-5s  %-2s  %d%n",
            syllabus.getCourseId(),
            truncate(syllabus.getCourseName(), 20),
            syllabus.getDepartmentId(),
            syllabus.getTeacherId(),
            syllabus.getSemester(),
            syllabus.getCredits());
    }
    
    private void generateReportFooter(PrintWriter writer, int totalRecords) {
        writer.println();
        writer.println("--------------------------------------------------------------------------------");
        writer.println();
        writer.println("合計レコード数: " + totalRecords);
        writer.println("--------------------------------------------------------------------------------");
        writer.println();
        writer.println("*** レポート終了 ***");
    }
    
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }
}
