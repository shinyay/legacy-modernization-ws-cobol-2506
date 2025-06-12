package com.syllabus.dao;

import com.syllabus.model.Syllabus;
import com.syllabus.exception.FileOperationException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileSyllabusDAO implements SyllabusDAO {
    private static final String DATA_FILE = "syllabus.dat";
    private static final String DELIMITER = "|";
    
    @Override
    public void save(Syllabus syllabus) throws FileOperationException {
        if (exists(syllabus.getCourseId())) {
            throw new FileOperationException("エラー: 科目コード " + syllabus.getCourseId() + " はすでに存在します。");
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE, true))) {
            writer.println(syllabusToString(syllabus));
        } catch (IOException e) {
            throw new FileOperationException("ファイルの書き込みに失敗しました。", e);
        }
    }
    
    @Override
    public void update(Syllabus syllabus) throws FileOperationException {
        List<Syllabus> allSyllabi = findAll();
        boolean found = false;
        
        for (int i = 0; i < allSyllabi.size(); i++) {
            if (allSyllabi.get(i).getCourseId().equals(syllabus.getCourseId())) {
                allSyllabi.set(i, syllabus);
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new FileOperationException("更新対象のレコードが見つかりません。");
        }
        
        writeAllSyllabi(allSyllabi);
    }
    
    @Override
    public void delete(String courseId) throws FileOperationException {
        List<Syllabus> allSyllabi = findAll();
        boolean removed = allSyllabi.removeIf(s -> s.getCourseId().equals(courseId));
        
        if (!removed) {
            throw new FileOperationException("削除対象のレコードが見つかりません。");
        }
        
        writeAllSyllabi(allSyllabi);
    }
    
    @Override
    public Optional<Syllabus> findById(String courseId) throws FileOperationException {
        return findAll().stream()
                .filter(s -> s.getCourseId().equals(courseId))
                .findFirst();
    }
    
    @Override
    public List<Syllabus> findAll() throws FileOperationException {
        List<Syllabus> syllabi = new ArrayList<>();
        File file = new File(DATA_FILE);
        
        if (!file.exists()) {
            return syllabi;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    syllabi.add(stringToSyllabus(line));
                }
            }
        } catch (IOException e) {
            throw new FileOperationException("ファイルの読み込みに失敗しました。", e);
        }
        
        return syllabi;
    }
    
    @Override
    public List<Syllabus> findByDepartment(String departmentId) throws FileOperationException {
        return findAll().stream()
                .filter(s -> s.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Syllabus> findByTeacher(String teacherId) throws FileOperationException {
        return findAll().stream()
                .filter(s -> s.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Syllabus> findBySemester(String semester) throws FileOperationException {
        return findAll().stream()
                .filter(s -> s.getSemester().equals(semester))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean exists(String courseId) throws FileOperationException {
        return findById(courseId).isPresent();
    }
    
    private void writeAllSyllabi(List<Syllabus> syllabi) throws FileOperationException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Syllabus syllabus : syllabi) {
                writer.println(syllabusToString(syllabus));
            }
        } catch (IOException e) {
            throw new FileOperationException("ファイルの書き込みに失敗しました。", e);
        }
    }
    
    private String syllabusToString(Syllabus syllabus) {
        StringBuilder sb = new StringBuilder();
        sb.append(syllabus.getCourseId()).append(DELIMITER);
        sb.append(syllabus.getCourseName()).append(DELIMITER);
        sb.append(syllabus.getDepartmentId()).append(DELIMITER);
        sb.append(syllabus.getTeacherId()).append(DELIMITER);
        sb.append(syllabus.getSemester()).append(DELIMITER);
        sb.append(syllabus.getCredits()).append(DELIMITER);
        sb.append(syllabus.getDescription()).append(DELIMITER);
        sb.append(syllabus.getObjectives()).append(DELIMITER);
        
        for (int i = 0; i < 15; i++) {
            sb.append(syllabus.getWeekPlan(i + 1));
            if (i < 14) sb.append(DELIMITER);
        }
        
        return sb.toString();
    }
    
    private Syllabus stringToSyllabus(String line) {
        String[] parts = line.split("\\" + DELIMITER, -1);
        if (parts.length < 23) {
            throw new RuntimeException("Invalid data format in file");
        }
        
        Syllabus syllabus = new Syllabus();
        syllabus.setCourseId(parts[0]);
        syllabus.setCourseName(parts[1]);
        syllabus.setDepartmentId(parts[2]);
        syllabus.setTeacherId(parts[3]);
        syllabus.setSemester(parts[4]);
        syllabus.setCredits(Integer.parseInt(parts[5]));
        syllabus.setDescription(parts[6]);
        syllabus.setObjectives(parts[7]);
        
        List<String> weekPlan = new ArrayList<>();
        for (int i = 8; i < 23; i++) {
            weekPlan.add(parts[i]);
        }
        syllabus.setWeekPlan(weekPlan);
        
        return syllabus;
    }
}
