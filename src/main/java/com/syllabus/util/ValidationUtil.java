package com.syllabus.util;

import com.syllabus.exception.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {
    
    public static void validateCourseId(String courseId) throws ValidationException {
        if (courseId == null || courseId.length() != 6) {
            throw new ValidationException("科目コードは先頭2文字がアルファベット、残り4文字が数字である必要があります。");
        }
        
        String prefix = courseId.substring(0, 2);
        String suffix = courseId.substring(2, 6);
        
        if (!prefix.matches("[A-Za-z]{2}")) {
            throw new ValidationException("科目コードは先頭2文字がアルファベット、残り4文字が数字である必要があります。");
        }
        
        if (!suffix.matches("\\d{4}")) {
            throw new ValidationException("科目コードは先頭2文字がアルファベット、残り4文字が数字である必要があります。");
        }
    }
    
    public static void validateDate(String date) throws ValidationException {
        if (date == null || date.length() != 8) {
            throw new ValidationException("日付形式が不正です（YYYYMMDD形式である必要があります）。");
        }
        
        if (!date.matches("\\d{8}")) {
            throw new ValidationException("日付形式が不正です（YYYYMMDD形式である必要があります）。");
        }
        
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));
            
            if (month < 1 || month > 12) {
                throw new ValidationException("日付の値が範囲外です。");
            }
            
            if (day < 1 || day > 31) {
                throw new ValidationException("日付の値が範囲外です。");
            }
            
            LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new ValidationException("日付の値が範囲外です。");
        }
    }
    
    public static String formatErrorMessage(String error, String detail) {
        return "エラー: " + error + " - " + detail;
    }
    
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
