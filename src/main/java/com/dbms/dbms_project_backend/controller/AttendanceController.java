package com.dbms.dbms_project_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.dto.AddAttendanceDto;
import com.dbms.dbms_project_backend.dto.UpdateAttendanceDto;
import com.dbms.dbms_project_backend.model.Attendance;
import com.dbms.dbms_project_backend.service.AttendanceService;
import com.dbms.dbms_project_backend.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/attendance")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
public class AttendanceController {

    @Autowired
    private LogService logService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<Attendance>> findAll() {
        logService.logRequestAndUser("/attendance", "GET");

        List<Attendance> attendances = attendanceService.findAll();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> findByStudentId(@PathVariable Long studentId) {
        logService.logRequestAndUser("/attendance/student/" + studentId, "GET");

        List<Attendance> attendances = attendanceService.findByStudentId(studentId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/slot/{slotId}")
    public ResponseEntity<List<Attendance>> findBySlotId(@PathVariable Long slotId) {
        logService.logRequestAndUser("/attendance/slot/" + slotId, "GET");

        List<Attendance> attendances = attendanceService.findBySlotId(slotId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/attendance/" + id, "GET");

        Attendance attendance = attendanceService.findById(id);
        return ResponseEntity.ok(attendance);
    }

    @PostMapping
    public ResponseEntity<Attendance> save(@Valid @RequestBody AddAttendanceDto addAttendanceDto) {
        logService.logRequestAndUser("/attendance", "POST");

        Attendance attendance = new Attendance();
        attendance.setStudentId(addAttendanceDto.getStudentId());
        attendance.setSlotId(addAttendanceDto.getSlotId());
        attendance.setRemark(addAttendanceDto.getRemark()); 

        Attendance savedAttendance = attendanceService.save(attendance);
        return ResponseEntity.ok(savedAttendance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> update(@PathVariable Long id, @Valid @RequestBody UpdateAttendanceDto updateAttendanceDto) {
        logService.logRequestAndUser("/attendance/" + id, "PUT");

        Attendance existingAttendance = attendanceService.findById(id);

        existingAttendance.setRemark(updateAttendanceDto.getRemark()); 
        Attendance updatedAttendance = attendanceService.update(id, existingAttendance);
        return ResponseEntity.ok(updatedAttendance);
    }

    

}
