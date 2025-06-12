package com.syllabus.ui;

import com.syllabus.model.Syllabus;
import com.syllabus.service.SyllabusService;
import com.syllabus.exception.FileOperationException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SyllabusRegistration {
    private final SyllabusService syllabusService;
    private final Scanner scanner;
    
    public SyllabusRegistration(SyllabusService syllabusService, Scanner scanner) {
        this.syllabusService = syllabusService;
        this.scanner = scanner;
    }
    
    public void execute() {
        boolean continueRegistration = true;
        
        while (continueRegistration) {
            try {
                Syllabus syllabus = new Syllabus();
                inputSyllabusData(syllabus);
                inputWeekPlanData(syllabus);
                writeSyllabusRecord(syllabus);
                continueRegistration = checkContinue();
            } catch (Exception e) {
                System.out.println("登録処理でエラーが発生しました: " + e.getMessage());
                continueRegistration = checkContinue();
            }
        }
    }
    
    private void inputSyllabusData(Syllabus syllabus) {
        boolean validInput = false;
        
        while (!validInput) {
            displaySyllabusInputScreen();
            
            System.out.print("科目コード(例:CS1001): ");
            String courseId = scanner.nextLine().trim();
            
            System.out.print("科目名: ");
            String courseName = scanner.nextLine().trim();
            
            System.out.print("学部学科コード: ");
            String departmentId = scanner.nextLine().trim();
            
            System.out.print("教員ID: ");
            String teacherId = scanner.nextLine().trim();
            
            System.out.print("開講学期(01=春前期): ");
            String semester = scanner.nextLine().trim();
            
            System.out.print("単位数: ");
            int credits;
            try {
                credits = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                credits = 0;
            }
            
            System.out.print("授業概要: ");
            String description = scanner.nextLine().trim();
            
            System.out.print("学習目標: ");
            String objectives = scanner.nextLine().trim();
            
            try {
                syllabusService.syllabusExists(courseId);
                syllabus.setCourseId(courseId);
                syllabus.setCourseName(courseName);
                syllabus.setDepartmentId(departmentId);
                syllabus.setTeacherId(teacherId);
                syllabus.setSemester(semester);
                syllabus.setCredits(credits);
                syllabus.setDescription(description);
                syllabus.setObjectives(objectives);
                validInput = true;
            } catch (Exception e) {
                System.out.println("入力検証でエラーが発生しました: " + e.getMessage());
            }
        }
    }
    
    private void inputWeekPlanData(Syllabus syllabus) {
        displayWeekPlanScreen(syllabus);
        
        List<String> weekPlan = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            System.out.print("第" + i + "週: ");
            String plan = scanner.nextLine().trim();
            weekPlan.add(plan);
        }
        
        syllabus.setWeekPlan(weekPlan);
    }
    
    private void writeSyllabusRecord(Syllabus syllabus) throws FileOperationException {
        try {
            syllabusService.registerSyllabus(syllabus);
            System.out.println("シラバスが正常に登録されました。");
        } catch (FileOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new FileOperationException("登録処理でエラーが発生しました。");
        }
    }
    
    private boolean checkContinue() {
        System.out.println();
        System.out.print("続けて登録しますか？ (Y/N): ");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("y");
    }
    
    private void displaySyllabusInputScreen() {
        System.out.println();
        System.out.println("シラバス登録画面");
        System.out.println();
    }
    
    private void displayWeekPlanScreen(Syllabus syllabus) {
        System.out.println();
        System.out.println("授業計画登録画面");
        System.out.println("科目コード: " + syllabus.getCourseId() + " 科目名: " + syllabus.getCourseName());
        System.out.println();
        System.out.println("授業計画入力:");
        System.out.println();
    }
}
