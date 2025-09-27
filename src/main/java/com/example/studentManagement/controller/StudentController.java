package com.example.studentManagement.controller;

import com.example.studentManagement.model.Student;
import com.example.studentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) throws ExecutionException, InterruptedException {
        List<Student> students = studentService.getStudentsPaginated(50, null);
        model.addAttribute("students", students);
        return "students/list";  // Thymeleaf template
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/add";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) throws ExecutionException, InterruptedException {
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws ExecutionException, InterruptedException {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "students/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable String id, @ModelAttribute Student student) throws ExecutionException, InterruptedException {
        student.setId(id);
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) throws ExecutionException, InterruptedException {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
