package com.example.studentManagement.controller;

import com.example.studentManagement.model.Student;
import com.example.studentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public String addStudent(@RequestBody Student student) throws ExecutionException, InterruptedException {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) throws ExecutionException, InterruptedException {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public List<Student> getStudents(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String lastDocId
    ) throws ExecutionException, InterruptedException {
        return studentService.getStudentsPaginated(pageSize, lastDocId);
    }

    @PutMapping
    public String updateStudent(@RequestBody Student student) throws ExecutionException, InterruptedException {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable String id) throws ExecutionException, InterruptedException {
        return studentService.deleteStudent(id);
    }
}