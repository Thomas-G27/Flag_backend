package com.takima.backskeleton.controllers;

import com.takima.backskeleton.models.Major;
import com.takima.backskeleton.models.Student;
import com.takima.backskeleton.services.MajorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequestMapping("majors")
@RestController
public class MajorController {
    private final MajorService majorService;

    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping("")
    public List<Major> findAll() {
        return majorService.findAll();
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsOfMajor(@PathVariable Long id) {
        return majorService.getStudentsOfMajor(id);
    }
}
