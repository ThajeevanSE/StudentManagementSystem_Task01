package com.example.studentManagement.controller;

import com.example.studentManagement.model.Course;
import com.example.studentManagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public String addCourse(@RequestBody Course course) throws ExecutionException, InterruptedException {
        return courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return courseService.getCourseById(id);
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() throws ExecutionException, InterruptedException {
        return courseService.getAllCourses();
    }

    @PutMapping
    public String updateCourse(@RequestBody Course course) throws ExecutionException, InterruptedException {
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable String id) throws ExecutionException, InterruptedException {
        return courseService.deleteCourse(id);
    }
}
