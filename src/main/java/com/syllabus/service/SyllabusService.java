package com.syllabus.service;

import com.syllabus.dao.SyllabusDAO;
import com.syllabus.model.Syllabus;
import com.syllabus.exception.FileOperationException;
import com.syllabus.exception.ValidationException;
import com.syllabus.util.ValidationUtil;
import java.util.List;
import java.util.Optional;

public class SyllabusService {
    private final SyllabusDAO syllabusDAO;
    
    public SyllabusService(SyllabusDAO syllabusDAO) {
        this.syllabusDAO = syllabusDAO;
    }
    
    public void registerSyllabus(Syllabus syllabus) throws FileOperationException, ValidationException {
        ValidationUtil.validateCourseId(syllabus.getCourseId());
        syllabusDAO.save(syllabus);
    }
    
    public void updateSyllabus(Syllabus syllabus) throws FileOperationException, ValidationException {
        ValidationUtil.validateCourseId(syllabus.getCourseId());
        syllabusDAO.update(syllabus);
    }
    
    public void deleteSyllabus(String courseId) throws FileOperationException, ValidationException {
        ValidationUtil.validateCourseId(courseId);
        syllabusDAO.delete(courseId);
    }
    
    public Optional<Syllabus> findSyllabus(String courseId) throws FileOperationException, ValidationException {
        ValidationUtil.validateCourseId(courseId);
        return syllabusDAO.findById(courseId);
    }
    
    public List<Syllabus> getAllSyllabi() throws FileOperationException {
        return syllabusDAO.findAll();
    }
    
    public List<Syllabus> getSyllabiByDepartment(String departmentId) throws FileOperationException {
        return syllabusDAO.findByDepartment(departmentId);
    }
    
    public List<Syllabus> getSyllabiByTeacher(String teacherId) throws FileOperationException {
        return syllabusDAO.findByTeacher(teacherId);
    }
    
    public List<Syllabus> getSyllabiBySemaster(String semester) throws FileOperationException {
        return syllabusDAO.findBySemester(semester);
    }
    
    public boolean syllabusExists(String courseId) throws FileOperationException {
        return syllabusDAO.exists(courseId);
    }
}
