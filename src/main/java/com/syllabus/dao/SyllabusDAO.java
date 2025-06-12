package com.syllabus.dao;

import com.syllabus.model.Syllabus;
import com.syllabus.exception.FileOperationException;
import java.util.List;
import java.util.Optional;

public interface SyllabusDAO {
    void save(Syllabus syllabus) throws FileOperationException;
    void update(Syllabus syllabus) throws FileOperationException;
    void delete(String courseId) throws FileOperationException;
    Optional<Syllabus> findById(String courseId) throws FileOperationException;
    List<Syllabus> findAll() throws FileOperationException;
    List<Syllabus> findByDepartment(String departmentId) throws FileOperationException;
    List<Syllabus> findByTeacher(String teacherId) throws FileOperationException;
    List<Syllabus> findBySemester(String semester) throws FileOperationException;
    boolean exists(String courseId) throws FileOperationException;
}
