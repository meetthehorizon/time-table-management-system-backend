package com.dbms.dbms_project_backend.service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Attendance;
import com.dbms.dbms_project_backend.repository.AttendanceRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

  @Autowired 
  private AttendanceRepository attendanceRepository;

  private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

  public List<Attendance> findAll() {
    logger.info("[INFO] Fetching all Attendance records");
    List<Attendance> attendances = attendanceRepository.findAll();
    logger.debug("[DEBUG] Retrieved {} attendance records", attendances.size());
    return attendances;
  }

  public List<Attendance> findByStudentId(Long studentId) {
    logger.info("[INFO] Fetching attendance for student ID: {}", studentId);
    List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
    if (attendances.isEmpty()) {
      logger.warn("[WARN] No attendance records found for student ID: {}", studentId);
      throw new NotFoundException("No attendance records found for student ID: ","ID", + studentId);
    }
    logger.debug("[DEBUG] Retrieved {} attendance records for student ID: {}", attendances.size(), studentId);
    return attendances;
  }

  public List<Attendance> findBySlotId(Long slotId) {
    logger.info("[INFO] Fetching attendance for slot ID: {}", slotId);
    List<Attendance> attendances = attendanceRepository.findBySlotId(slotId);
    if (attendances.isEmpty()) {
      logger.warn("[WARN] No attendance records found for slot ID: {}", slotId);
      throw new NotFoundException("No attendance records found for slot ID: ","Slot ID", + slotId);
    }
    logger.debug("[DEBUG] Retrieved {} attendance records for slot ID: {}", attendances.size(), slotId);
    return attendances;
  }

  public Attendance findById(Long id) {
    logger.info("[INFO] Fetching attendance by ID: {}", id);
    Optional<Attendance> attendanceOpt = attendanceRepository.findById(id);
    if (attendanceOpt.isEmpty()) {
      logger.warn("[WARN] Attendance record not found with ID: {}", id);
      throw new NotFoundException("Attendance record not found with ID: ","ID", + id);
    }
    logger.debug("[DEBUG] Retrieved attendance record for ID: {}", id);
    return attendanceOpt.get();
  }

  public Attendance save(Attendance attendance) {
    logger.info("[INFO] Saving attendance record for student ID: {}", attendance.getStudentId());
    Attendance savedAttendance = attendanceRepository.save(attendance);
    logger.debug("[DEBUG] Saved attendance record with ID: {}", savedAttendance.getId());
    return savedAttendance;
  }

  public Attendance update(Long id, Attendance updatedAttendance) {
    logger.info("[INFO] Updating attendance record with ID: {}", id);
    Optional<Attendance> existingAttendanceOpt = attendanceRepository.findById(id);
    if (existingAttendanceOpt.isEmpty()) {
      logger.warn("[WARN] Attendance record not found with ID: {}", id);
      throw new NotFoundException("Attendance record not found with ID: ","ID", + id);
    }
    Attendance existingAttendance = existingAttendanceOpt.get();
    existingAttendance.setSlotId(updatedAttendance.getSlotId());
    existingAttendance.setStudentId(updatedAttendance.getStudentId());
    existingAttendance.setIsPresent(updatedAttendance.getIsPresent());
    existingAttendance.setCurrDate(updatedAttendance.getCurrDate());
    existingAttendance.setRemark(updatedAttendance.getRemark());

    Attendance updated = attendanceRepository.update(existingAttendance);
    logger.debug("[DEBUG] Updated attendance record with ID: {}", updated.getId());
    return updated;
  }
}
