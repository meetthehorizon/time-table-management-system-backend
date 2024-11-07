package com.dbms.dbms_project_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.dto.AddSlotsDto;
import com.dbms.dbms_project_backend.dto.UpdateSlotsDto;
import com.dbms.dbms_project_backend.model.Course;
import com.dbms.dbms_project_backend.model.Slots;
import com.dbms.dbms_project_backend.model.SubjectReq;
import com.dbms.dbms_project_backend.model.enumerations.Day;
import com.dbms.dbms_project_backend.response.SlotResponse;
import com.dbms.dbms_project_backend.service.CourseService;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SlotsService;
import com.dbms.dbms_project_backend.service.SubjectReqService;
import com.dbms.dbms_project_backend.service.SubjectService;
import com.dbms.dbms_project_backend.service.TeacherReqService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/slots")
public class SlotsController {
    @Autowired
    private LogService logService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectReqService subjectReqService;

    @Autowired
    private TeacherReqService teacherReqService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SlotsService slotsService;

    private SlotResponse convertSlotToSlotResponse(Slots slot) {
        SlotResponse slotResponse = new SlotResponse().setSlots(slot);
        Course course = courseService.findById(slot.getCourseId());
        SubjectReq subjectReq = subjectReqService.findById(course.getSubjectReqId());

        slotResponse.setSubject(subjectService.findById(subjectReq.getSubjectId()));
        slotResponse.setTeacherReq(teacherReqService.findById(course.getTeacherReqId()));

        return slotResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<SlotResponse> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/slots/" + id, "GET");

        Slots slots = slotsService.findById(id);
        return ResponseEntity.ok(convertSlotToSlotResponse(slots));
    }

    @GetMapping("section/{sectionId}")
    public ResponseEntity<List<SlotResponse>> findBySectionId(@PathVariable Long sectionId) {
        logService.logRequestAndUser("/slots/section/" + sectionId, "GET");

        List<Slots> slots = slotsService.findBySectionId(sectionId);
        List<SlotResponse> slotResponses = slots.stream().map(this::convertSlotToSlotResponse).toList();

        return ResponseEntity.ok(slotResponses);
    }

    @GetMapping("teacher-req/{teacherReqId}")
    public ResponseEntity<List<SlotResponse>> findByTeacherId(@PathVariable Long teacherReqId) {
        logService.logRequestAndUser("/slots/teacher/" + teacherReqId, "GET");

        List<Slots> slots = slotsService.findByTeacherReqId(teacherReqId);
        List<SlotResponse> slotResponses = slots.stream().map(this::convertSlotToSlotResponse).toList();

        return ResponseEntity.ok(slotResponses);
    }

    @PostMapping
    public ResponseEntity<SlotResponse> save(@Valid @RequestBody AddSlotsDto addSlotDto) {
        logService.logRequestAndUser("/slots", "POST");

        Slots newSlot = new Slots().setCourseId(addSlotDto.getCourseId()).setDay(Day.fromString(addSlotDto.getDay()))
                .setStartTime(addSlotDto.getStartTime()).setEndTime(addSlotDto.getEndTime());

        if (newSlot.getStartTime() >= newSlot.getEndTime()) {
            throw new IllegalArgumentException("Start time must be before end time");
        } else if (newSlot.getEndTime() - newSlot.getStartTime() != 100) {
            throw new IllegalArgumentException("Slot duration must be 1 hour");
        }

        Slots savedSlot = slotsService.save(newSlot);
        return ResponseEntity.ok(convertSlotToSlotResponse(savedSlot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlotResponse> update(@PathVariable Long id,
            @Valid @RequestBody UpdateSlotsDto updateSlotDto) {
        logService.logRequestAndUser("/slots/" + id, "PUT");

        Slots existingSlot = slotsService.findById(id).setStartTime(updateSlotDto.getStartTime())
                .setEndTime(updateSlotDto.getEndTime());

        Optional.ofNullable(updateSlotDto.getDay()).ifPresent(day -> existingSlot.setDay(Day.fromString(day)));

        if (existingSlot.getStartTime() >= existingSlot.getEndTime()) {
            throw new IllegalArgumentException("Start time must be before end time");
        } else if (existingSlot.getEndTime() - existingSlot.getStartTime() != 100) {
            throw new IllegalArgumentException("Slot duration must be 1 hour");
        }

        Slots updatedSlot = slotsService.update(existingSlot);
        return ResponseEntity.ok(convertSlotToSlotResponse(updatedSlot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logService.logRequestAndUser("/slots/" + id, "DELETE");

        slotsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
