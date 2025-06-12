package com.syllabus.ui;

import com.syllabus.model.Syllabus;
import com.syllabus.service.SyllabusService;
import com.syllabus.exception.FileOperationException;
import java.util.Scanner;
import java.util.Optional;

public class SyllabusDelete {
    private final SyllabusService syllabusService;
    private final Scanner scanner;
    
    public SyllabusDelete(SyllabusService syllabusService, Scanner scanner) {
        this.syllabusService = syllabusService;
        this.scanner = scanner;
    }
    
    public void execute() {
        boolean continueDelete = true;
        
        while (continueDelete) {
            try {
                deleteSyllabusProcess();
                continueDelete = checkContinue();
            } catch (Exception e) {
                System.out.println("削除処理でエラーが発生しました: " + e.getMessage());
                continueDelete = checkContinue();
            }
        }
    }
    
    private void deleteSyllabusProcess() {
        Optional<Syllabus> syllabusOpt = searchSyllabus();
        if (syllabusOpt.isPresent()) {
            Syllabus syllabus = syllabusOpt.get();
            if (confirmDeletion(syllabus)) {
                deleteSyllabusRecord(syllabus.getCourseId());
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }
    
    private Optional<Syllabus> searchSyllabus() {
        System.out.println();
        System.out.println("Delete Syllabus Screen");
        System.out.print("Enter course ID to delete: ");
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
    
    private boolean confirmDeletion(Syllabus syllabus) {
        System.out.println();
        System.out.println("Delete Confirmation");
        System.out.println("Course ID: " + syllabus.getCourseId());
        System.out.println("Course Name: " + syllabus.getCourseName());
        System.out.println("Department: " + syllabus.getDepartmentId());
        System.out.println("Teacher ID: " + syllabus.getTeacherId());
        System.out.println();
        System.out.print("Delete this syllabus? (Y/N): ");
        
        String confirmation = scanner.nextLine().trim();
        return confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("y");
    }
    
    private void deleteSyllabusRecord(String courseId) {
        try {
            syllabusService.deleteSyllabus(courseId);
            System.out.println("Syllabus deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: Failed to delete record.");
        }
    }
    
    private boolean checkContinue() {
        System.out.println();
        System.out.print("Continue deleting? (Y/N): ");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("y");
    }
}
