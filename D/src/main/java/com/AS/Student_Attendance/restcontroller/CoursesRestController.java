package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.Courses;
import com.AS.Student_Attendance.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesRestController {
    @Autowired
    private CoursesRepository coursesRepository;

    @GetMapping
    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    @GetMapping("/{id}")
    public Courses getCourseById(@PathVariable Integer id) {
        return coursesRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Courses createCourse(@RequestBody Courses course) {
        return coursesRepository.save(course);
    }

    @PutMapping("/{id}")
    public Courses updateCourse(@PathVariable Integer id, @RequestBody Courses course) {
        course.setCourseId(id);
        return coursesRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        coursesRepository.deleteById(id);
    }
}
