package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> fetchAllStudent(){
        return studentService.getAllStudents();
    }
    
}
