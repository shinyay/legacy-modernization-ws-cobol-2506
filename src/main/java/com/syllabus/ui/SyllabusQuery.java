package com.syllabus.ui;

import com.syllabus.model.Syllabus;
import com.syllabus.service.SyllabusService;
import java.util.Scanner;
import java.util.Optional;

public class SyllabusQuery {
    private final SyllabusService syllabusService;
    private final Scanner scanner;
    
    public SyllabusQuery(SyllabusService syllabusService, Scanner scanner) {
        this.syllabusService = syllabusService;
        this.scanner = scanner;
    }
    
    public void execute() {
        boolean continueQuery = true;
        
        while (continueQuery) {
            try {
                querySyllabusProcess();
                continueQuery = checkContinue();
            } catch (Exception e) {
                System.out.println("照会処理でエラーが発生しました: " + e.getMessage());
                continueQuery = checkContinue();
            }
        }
    }
    
    private void querySyllabusProcess() {
        Optional<Syllabus> syllabusOpt = searchSyllabus();
        if (syllabusOpt.isPresent()) {
            Syllabus syllabus = syllabusOpt.get();
            displaySyllabusDetail(syllabus);
            displayWeekPlan(syllabus);
        }
    }
    
    private Optional<Syllabus> searchSyllabus() {
        System.out.println();
        System.out.println("Syllabus Query Screen");
        System.out.print("Enter course ID to query: ");
        String courseId = scanner.nextLine().trim();
        
        try {
            Optional<Syllabus> syllabusOpt = syllabusService.findSyllabus(courseId);
            if (!syllabusOpt.isPresent()) {
                System.out.println("Error: Course ID " + courseId + " does not exist.");
            }
            return syllabusOpt;
        } catch (Exception e) {
            System.out.println("Error: Course ID " + courseId + " does not exist.");
            return Optional.empty();
        }
    }
    
    private void displaySyllabusDetail(Syllabus syllabus) {
        System.out.println();
        System.out.println("Syllabus Details");
        System.out.println("Course ID: " + syllabus.getCourseId());
        System.out.println("Course Name: " + syllabus.getCourseName());
        System.out.println("Department: " + syllabus.getDepartmentId());
        System.out.println("Teacher ID: " + syllabus.getTeacherId());
        System.out.println("Semester: " + syllabus.getSemester());
        System.out.println("Credits: " + syllabus.getCredits());
        System.out.println();
        System.out.println("Description: ");
        System.out.println("     " + syllabus.getDescription());
        System.out.println();
        System.out.println("Objectives: ");
        System.out.println("     " + syllabus.getObjectives());
        System.out.println();
        System.out.print("Press any key to continue...");
        scanner.nextLine();
    }
    
    private void displayWeekPlan(Syllabus syllabus) {
        System.out.println();
        System.out.println("Weekly Schedule");
        System.out.println("Course ID: " + syllabus.getCourseId() + " Course Name: " + syllabus.getCourseName());
        System.out.println();
        System.out.println("Weekly Plan:");
        System.out.println();
        
        for (int i = 1; i <= 15; i++) {
            System.out.println("Week " + String.format("%2d", i) + ": " + syllabus.getWeekPlan(i));
        }
        
        System.out.println();
        System.out.print("Press any key to continue...");
        scanner.nextLine();
    }
    
    private boolean checkContinue() {
        System.out.println();
        System.out.print("Continue querying? (Y/N): ");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("y");
    }
}
