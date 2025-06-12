package com.syllabus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Syllabus {
    private String courseId;
    private String courseName;
    private String departmentId;
    private String teacherId;
    private String semester;
    private int credits;
    private String description;
    private String objectives;
    private List<String> weekPlan;

    public Syllabus() {
        this.weekPlan = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            this.weekPlan.add("");
        }
    }

    public Syllabus(String courseId, String courseName, String departmentId, 
                   String teacherId, String semester, int credits, 
                   String description, String objectives, List<String> weekPlan) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.departmentId = departmentId;
        this.teacherId = teacherId;
        this.semester = semester;
        this.credits = credits;
        this.description = description;
        this.objectives = objectives;
        this.weekPlan = weekPlan != null ? new ArrayList<>(weekPlan) : new ArrayList<>();
        while (this.weekPlan.size() < 15) {
            this.weekPlan.add("");
        }
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public List<String> getWeekPlan() {
        return weekPlan;
    }

    public void setWeekPlan(List<String> weekPlan) {
        this.weekPlan = weekPlan != null ? new ArrayList<>(weekPlan) : new ArrayList<>();
        while (this.weekPlan.size() < 15) {
            this.weekPlan.add("");
        }
    }

    public String getWeekPlan(int week) {
        if (week >= 1 && week <= 15) {
            return weekPlan.get(week - 1);
        }
        return "";
    }

    public void setWeekPlan(int week, String plan) {
        if (week >= 1 && week <= 15) {
            weekPlan.set(week - 1, plan);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Syllabus syllabus = (Syllabus) o;
        return Objects.equals(courseId, syllabus.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Syllabus{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", semester='" + semester + '\'' +
                ", credits=" + credits +
                ", description='" + description + '\'' +
                ", objectives='" + objectives + '\'' +
                '}';
    }
}
