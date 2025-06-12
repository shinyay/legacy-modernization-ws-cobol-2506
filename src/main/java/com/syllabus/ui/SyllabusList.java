package com.syllabus.ui;

import com.syllabus.model.Syllabus;
import com.syllabus.service.SyllabusService;
import java.util.List;
import java.util.Scanner;

public class SyllabusList {
    private final SyllabusService syllabusService;
    private final Scanner scanner;
    private static final int RECORDS_PER_PAGE = 10;
    
    public SyllabusList(SyllabusService syllabusService, Scanner scanner) {
        this.syllabusService = syllabusService;
        this.scanner = scanner;
    }
    
    public void execute() {
        try {
            int listOption = getListOption();
            String filter = getFilterValue(listOption);
            List<Syllabus> syllabi = getSyllabi(listOption, filter);
            
            if (syllabi.isEmpty()) {
                System.out.println("該当するシラバスが見つかりません。");
                return;
            }
            
            displayPaginatedList(syllabi);
        } catch (Exception e) {
            System.out.println("一覧表示でエラーが発生しました: " + e.getMessage());
        }
    }
    
    private int getListOption() {
        System.out.println();
        System.out.println("Syllabus List");
        System.out.println("Select display option:");
        System.out.println();
        System.out.println("1. All syllabi");
        System.out.println("2. By department");
        System.out.println("3. By teacher");
        System.out.println("4. By semester");
        System.out.println();
        System.out.print("Select (1-4): ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection. Showing all syllabi.");
            return 1;
        }
    }
    
    private String getFilterValue(int listOption) {
        switch (listOption) {
            case 2:
                System.out.println();
                System.out.println("Syllabus List by Department");
                System.out.print("Enter department code: ");
                return scanner.nextLine().trim();
            case 3:
                System.out.println();
                System.out.println("Syllabus List by Teacher");
                System.out.print("Enter teacher ID: ");
                return scanner.nextLine().trim();
            case 4:
                System.out.println();
                System.out.println("Syllabus List by Semester");
                System.out.print("Enter semester (e.g. 01=Spring): ");
                return scanner.nextLine().trim();
            default:
                return "";
        }
    }
    
    private List<Syllabus> getSyllabi(int listOption, String filter) throws Exception {
        switch (listOption) {
            case 2:
                return syllabusService.getSyllabiByDepartment(filter);
            case 3:
                return syllabusService.getSyllabiByTeacher(filter);
            case 4:
                return syllabusService.getSyllabiBySemaster(filter);
            default:
                return syllabusService.getAllSyllabi();
        }
    }
    
    private void displayPaginatedList(List<Syllabus> syllabi) {
        int totalRecords = syllabi.size();
        int totalPages = (totalRecords + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE;
        int currentPage = 1;
        boolean exitList = false;
        
        while (!exitList) {
            displayListHeader(currentPage, totalPages);
            displayCurrentPage(syllabi, currentPage);
            displayListFooter();
            
            String pageOption = scanner.nextLine().trim().toUpperCase();
            
            switch (pageOption) {
                case "N":
                    if (currentPage < totalPages) {
                        currentPage++;
                    }
                    break;
                case "P":
                    if (currentPage > 1) {
                        currentPage--;
                    }
                    break;
                case "X":
                    exitList = true;
                    break;
                default:
                    System.out.println("Invalid option. Use N=Next, P=Prev, X=Exit");
            }
        }
    }
    
    private void displayListHeader(int currentPage, int totalPages) {
        System.out.println();
        System.out.println("Syllabus List");
        System.out.println("Page: " + currentPage + "/" + totalPages);
        System.out.println("==============================");
        System.out.println("Course Course Name   Dept Teacher Sem");
        System.out.println("==============================");
    }
    
    private void displayCurrentPage(List<Syllabus> syllabi, int currentPage) {
        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, syllabi.size());
        
        for (int i = startIndex; i < endIndex; i++) {
            Syllabus syllabus = syllabi.get(i);
            System.out.printf("%-6s %-25s %-4s %-7s %-2s%n",
                syllabus.getCourseId(),
                truncate(syllabus.getCourseName(), 25),
                syllabus.getDepartmentId(),
                syllabus.getTeacherId(),
                syllabus.getSemester());
        }
    }
    
    private void displayListFooter() {
        System.out.println("==============================");
        System.out.println();
        System.out.print("N=Next, P=Prev, X=Exit: ");
    }
    
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }
}
