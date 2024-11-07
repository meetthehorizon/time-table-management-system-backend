package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Course;
import com.dbms.dbms_project_backend.model.Slots;
import com.dbms.dbms_project_backend.repository.CourseRepository;
import com.dbms.dbms_project_backend.repository.SlotsRepository;
import com.dbms.dbms_project_backend.repository.TeacherReqRepository;

@Service
public class SlotsService {
    @Autowired
    private SlotsRepository slotsRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherReqRepository teacherReqRepository;

    private static final Logger logger = LoggerFactory.getLogger(SlotsService.class);

    public List<Slots> findBySectionId(Long sectionId) {
        logger.info("Fetching slots by section id: " + sectionId);

        List<Slots> slots = slotsRepository.findBySectionId(sectionId);
        logger.debug("[DEBUG] Slots found: " + slots.size());

        return slots;
    }

    public List<Slots> findByTeacherReqId(Long teacherId) {
        logger.info("Fetching slots by teacher id: " + teacherId);

        teacherReqRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("TeacherReq", "id", teacherId));

        List<Slots> slots = slotsRepository.findByTeacherReqId(teacherId);
        logger.debug("[DEBUG] Slots found: " + slots.size());

        return slots;
    }

    public Slots findById(Long id) {
        logger.info("Fetching slot by id: " + id);

        Slots slot = slotsRepository.findById(id).orElseThrow(() -> new NotFoundException("Slots", "id", id));
        logger.debug("[DEBUG] Slot found: " + slot);

        return slot;
    }

    public Slots save(Slots newSlot) {
        logger.info("Saving slot: " + newSlot);

        Course course = courseRepository.findById(newSlot.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course", "id", newSlot.getCourseId()));

        Long sectionId = course.getSectionId();
        List<Slots> sectionSlots = slotsRepository.findBySectionId(sectionId);

        sectionSlots.forEach((slot) -> {
            if (slot.getDay().equals(newSlot.getDay())) {
                if (newSlot.getStartTime() >= slot.getStartTime() && newSlot.getStartTime() < slot.getEndTime()) {
                    logger.error("[ERROR] Slot overlaps with existing slot in section " + sectionId + ": " + slot);
                    throw new IllegalArgumentException("Slot overlaps with existing slot");
                }
                if (newSlot.getEndTime() > slot.getStartTime() && newSlot.getEndTime() <= slot.getEndTime()) {
                    logger.error("[ERROR] Slot overlaps with existing slot in section " + sectionId + ": " + slot);
                    throw new IllegalArgumentException("Slot overlaps with existing slot");
                }
            }
        });

        Long teacherReqId = course.getTeacherReqId();
        List<Slots> teacherSlots = slotsRepository.findByTeacherReqId(teacherReqId);

        teacherSlots.forEach((slot) -> {
            if (slot.getDay().equals(newSlot.getDay())) {
                if (newSlot.getStartTime() >= slot.getStartTime() && newSlot.getStartTime() < slot.getEndTime()) {
                    logger.error("[ERROR] Slot overlaps with existing slot for teacher " + teacherReqId + ": " + slot);
                    throw new IllegalArgumentException("Slot overlaps with existing slot");
                }
                if (newSlot.getEndTime() > slot.getStartTime() && newSlot.getEndTime() <= slot.getEndTime()) {
                    logger.error("[ERROR] Slot overlaps with existing slot for teacher " + teacherReqId + ": " + slot);
                    throw new IllegalArgumentException("Slot overlaps with existing slot");
                }
            }
        });

        Slots slot = slotsRepository.save(newSlot);
        logger.debug("[DEBUG] Slot saved: " + slot);

        return slot;
    }

    public Slots update(Slots newSlot) {
        logger.info("Updating slot: " + newSlot);

        Slots slot = slotsRepository.update(newSlot);
        logger.debug("[DEBUG] Slot updated: " + slot);

        return slot;
    }

    public void deleteById(Long id) {
        logger.info("Deleting slot by id: " + id);

        slotsRepository.deleteById(id);
        logger.debug("[DEBUG] Slot deleted");
    }
}
