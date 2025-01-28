package com.studentsattendance.studentsattendance.controller;

import com.studentsattendance.studentsattendance.model.Attendance;
import com.studentsattendance.studentsattendance.model.Student;
import com.studentsattendance.studentsattendance.repository.AttendanceRepository;
import com.studentsattendance.studentsattendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Fetch all students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/attendance")
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        LocalDate today = LocalDate.now();

        Attendance newAttendance = new Attendance();
        newAttendance.setStudent(savedStudent);
        newAttendance.setDate(today);
        newAttendance.setStatus("Present");

        attendanceRepository.save(newAttendance);

        return savedStudent;
    }

    // Mark attendance for a student
    @PostMapping("/attendance/students")
    public Attendance markAttendance(@RequestBody Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    // Update attendance status (Present or Absent)
    @PutMapping("/attendance/{id}")
    public Attendance updateAttendance(@PathVariable Long id, @RequestBody Attendance attendanceDetails) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendance.setStatus(attendanceDetails.getStatus());
        return attendanceRepository.save(attendance);
    }
}
