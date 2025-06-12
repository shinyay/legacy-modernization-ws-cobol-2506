package com.syllabus.ui;

import com.syllabus.model.Syllabus;
import com.syllabus.service.SyllabusService;
import com.syllabus.exception.FileOperationException;
import java.util.Scanner;
import java.util.Optional;

public class SyllabusUpdate {
    private final SyllabusService syllabusService;
    private final Scanner scanner;
    
    public SyllabusUpdate(SyllabusService syllabusService, Scanner scanner) {
        this.syllabusService = syllabusService;
        this.scanner = scanner;
    }
    
    public void execute() {
        boolean continueUpdate = true;
        
        while (continueUpdate) {
            try {
                Optional<Syllabus> syllabusOpt = searchSyllabus();
                if (syllabusOpt.isPresent()) {
                    Syllabus syllabus = syllabusOpt.get();
                    updateSyllabusLoop(syllabus);
                    rewriteSyllabusRecord(syllabus);
                }
                continueUpdate = checkContinue();
            } catch (Exception e) {
                System.out.println("更新処理でエラーが発生しました: " + e.getMessage());
                continueUpdate = checkContinue();
            }
        }
    }
    
    private Optional<Syllabus> searchSyllabus() {
        System.out.println();
        System.out.println("UPDATE SYLLABUS");
        System.out.print("ENTER COURSE ID: ");
        String courseId = scanner.nextLine().trim();
        
        try {
            Optional<Syllabus> syllabusOpt = syllabusService.findSyllabus(courseId);
            if (!syllabusOpt.isPresent()) {
                System.out.println("ERROR: COURSE ID " + courseId + " NOT FOUND");
            }
            return syllabusOpt;
        } catch (Exception e) {
            System.out.println("ERROR: COURSE ID " + courseId + " NOT FOUND");
            return Optional.empty();
        }
    }
    
    private void updateSyllabusLoop(Syllabus syllabus) {
        int updateOption = 0;
        
        while (updateOption != 9) {
            displayUpdateMenu(syllabus);
            updateOption = getUpdateOption();
            
            switch (updateOption) {
                case 1:
                    updateCourseName(syllabus);
                    break;
                case 2:
                    updateDepartment(syllabus);
                    break;
                case 3:
                    updateTeacher(syllabus);
                    break;
                case 4:
                    updateSemester(syllabus);
                    break;
                case 5:
                    updateCredits(syllabus);
                    break;
                case 6:
                    updateDescription(syllabus);
                    break;
                case 7:
                    updateObjectives(syllabus);
                    break;
                case 8:
                    updateWeekPlan(syllabus);
                    break;
                case 9:
                    break;
                default:
                    System.out.println("INVALID SELECTION");
            }
        }
    }
    
    private void displayUpdateMenu(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE MENU");
        System.out.println("COURSE ID: " + syllabus.getCourseId() + " NAME: " + syllabus.getCourseName());
        System.out.println();
        System.out.println("SELECT ITEM TO UPDATE:");
        System.out.println();
        System.out.println("1. COURSE NAME");
        System.out.println("2. DEPARTMENT");
        System.out.println("3. TEACHER ID");
        System.out.println("4. SEMESTER");
        System.out.println("5. CREDITS");
        System.out.println("6. DESCRIPTION");
        System.out.println("7. OBJECTIVES");
        System.out.println("8. WEEK PLAN");
        System.out.println("9. SAVE AND EXIT");
        System.out.println();
        System.out.print("SELECT (1-9): ");
    }
    
    private int getUpdateOption() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void updateCourseName(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE COURSE NAME");
        System.out.println("CURRENT NAME: " + syllabus.getCourseName());
        System.out.print("NEW NAME: ");
        String newName = scanner.nextLine().trim();
        syllabus.setCourseName(newName);
    }
    
    private void updateDepartment(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE DEPARTMENT");
        System.out.println("CURRENT DEPT: " + syllabus.getDepartmentId());
        System.out.print("NEW DEPT ID: ");
        String newDept = scanner.nextLine().trim();
        syllabus.setDepartmentId(newDept);
    }
    
    private void updateTeacher(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE TEACHER");
        System.out.println("CURRENT TEACHER: " + syllabus.getTeacherId());
        System.out.print("NEW TEACHER ID: ");
        String newTeacher = scanner.nextLine().trim();
        syllabus.setTeacherId(newTeacher);
    }
    
    private void updateSemester(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE SEMESTER");
        System.out.println("CURRENT SEMESTER: " + syllabus.getSemester());
        System.out.print("NEW SEMESTER: ");
        String newSemester = scanner.nextLine().trim();
        syllabus.setSemester(newSemester);
    }
    
    private void updateCredits(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE CREDITS");
        System.out.println("CURRENT CREDITS: " + syllabus.getCredits());
        System.out.print("NEW CREDITS: ");
        try {
            int newCredits = Integer.parseInt(scanner.nextLine().trim());
            syllabus.setCredits(newCredits);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        }
    }
    
    private void updateDescription(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE DESCRIPTION");
        System.out.println("CURRENT DESCRIPTION: " + syllabus.getDescription());
        System.out.print("NEW DESCRIPTION: ");
        String newDescription = scanner.nextLine().trim();
        syllabus.setDescription(newDescription);
    }
    
    private void updateObjectives(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE OBJECTIVES");
        System.out.println("CURRENT OBJECTIVES: " + syllabus.getObjectives());
        System.out.print("NEW OBJECTIVES: ");
        String newObjectives = scanner.nextLine().trim();
        syllabus.setObjectives(newObjectives);
    }
    
    private void updateWeekPlan(Syllabus syllabus) {
        System.out.println();
        System.out.println("UPDATE WEEKLY PLAN");
        System.out.println("COURSE ID: " + syllabus.getCourseId() + " NAME: " + syllabus.getCourseName());
        System.out.println();
        System.out.println("ENTER WEEKLY PLANS:");
        System.out.println();
        
        for (int i = 1; i <= 15; i++) {
            System.out.print("WEEK " + String.format("%2d", i) + ": ");
            String plan = scanner.nextLine().trim();
            syllabus.setWeekPlan(i, plan);
        }
    }
    
    private void rewriteSyllabusRecord(Syllabus syllabus) throws FileOperationException {
        try {
            syllabusService.updateSyllabus(syllabus);
            System.out.println("UPDATE SUCCESSFUL");
        } catch (Exception e) {
            System.out.println("ERROR: UPDATE FAILED");
            throw new FileOperationException("更新処理でエラーが発生しました。");
        }
    }
    
    private boolean checkContinue() {
        System.out.println();
        System.out.print("CONTINUE? (Y/N): ");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("y");
    }
}
