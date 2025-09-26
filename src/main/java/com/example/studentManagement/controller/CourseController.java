package com.example.studentManagement.controller;

import com.example.studentManagement.model.Course;
import com.example.studentManagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listCourses(Model model) throws ExecutionException, InterruptedException {
        List<Course> courses = courseService.getCoursesPaginated(50, null);
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/add";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute Course course) throws ExecutionException, InterruptedException {
        courseService.addCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws ExecutionException, InterruptedException {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "courses/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable String id, @ModelAttribute Course course) throws ExecutionException, InterruptedException {
        course.setId(id);
        courseService.updateCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable String id) throws ExecutionException, InterruptedException {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
