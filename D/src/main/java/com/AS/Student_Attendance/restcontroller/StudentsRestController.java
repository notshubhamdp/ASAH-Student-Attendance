package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.Students;
import com.AS.Student_Attendance.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentsRestController {

    @Autowired
    private StudentsRepository studentsRepository;

    // ✅ Get all students
    @GetMapping
    public List<Students> getAllStudents() {
        return studentsRepository.findAll();
    }

    // ✅ Add new student
    @PostMapping
    public Students addStudent(@RequestBody Students student) {
        // ❌ Remove student.setStudentId(..) → DB will generate ID automatically
        return studentsRepository.save(student);
    }

    // ✅ Get student by ID
    @GetMapping("/{id}")
    public Students getStudentById(@PathVariable Integer id) {
        return studentsRepository.findById(id).orElse(null);
    }

    // ✅ Update student
    @PutMapping("/{id}")
    public Students updateStudent(@PathVariable Integer id, @RequestBody Students updatedStudent) {
        return studentsRepository.findById(id).map(student -> {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setBranch(updatedStudent.getBranch());
            return studentsRepository.save(student);
        }).orElse(null);
    }

    // ✅ Delete student
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentsRepository.deleteById(id);
    }
}
