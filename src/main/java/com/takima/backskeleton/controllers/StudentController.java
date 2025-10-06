package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.StudentDto;
import com.takima.backskeleton.models.Student;
import com.takima.backskeleton.services.StudentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequestMapping("students")
@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public List<Student> listStudents(@RequestParam(required = false) Integer majorId, @RequestParam(required = false) Integer courseId) {
        if (majorId != null && courseId !=null) {
            return studentService.searchByMajorAndCourse(majorId, courseId);
        }
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @PostMapping("")
    public void addStudent(@RequestBody StudentDto studentDto) {
        studentService.addStudent(studentDto);
    }

    @PostMapping("/{id}")
    public void updateStudent(@RequestBody StudentDto studentDto, @PathVariable Long id) {
        studentService.updateStudent(studentDto, id);
    }
}
