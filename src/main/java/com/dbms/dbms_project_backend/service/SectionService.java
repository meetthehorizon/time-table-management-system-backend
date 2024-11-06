package com.dbms.dbms_project_backend.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Section;
import com.dbms.dbms_project_backend.repository.SchoolRepository;
import com.dbms.dbms_project_backend.repository.SectionRepository;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    private static final Logger logger = LoggerFactory.getLogger(SectionService.class);

    public Section findById(Long id) {
        logger.info("[INFO] Fetching section with id: " + id);

        Section section = sectionRepository.findById(id).orElseThrow(() -> new NotFoundException("Section", "id", id));
        logger.debug("[DEBUG] Fetched Section: " + section.toString());

        return section;
    }

    public List<Section> findAll() {
        logger.info("[INFO] Fetching all sections");

        List<Section> section = sectionRepository.findAll();
        logger.debug("[DEBUG] Fetched all sections");

        return section;
    }

    public Section save(Section section) {
        logger.info("[INFO] Saving section: " + section.toString());

        if (sectionRepository.existsByUniqueFields(section)) {
            logger.warn("[WARN] Section already exists: " + section.toString());
            throw new FieldValueAlreadyExistsException("Section", "all", section.toString());
        }

        Section savedSection = sectionRepository.save(section);
        logger.debug("[DEBUG] Saved section: " + savedSection.toString());

        return savedSection;
    }

    public Section update(Section section) {
        logger.info("[INFO] Updating section: " + section.toString());

        if (!schoolRepository.existsById(section.getSchoolId())) {
            logger.warn("[WARN] School not found with id: " + section.getSchoolId());
            throw new NotFoundException("School", "id", section.getSchoolId());
        }

        if (sectionRepository.existsByUniqueFields(section)) {
            Section existingSection = sectionRepository.findByUniqueFields(section);

            if (!existingSection.getId().equals(section.getId())) {
                logger.warn("[WARN] Section already exists: " + section.toString());
                throw new FieldValueAlreadyExistsException("Section", "all", section.toString());
            }
        }

        Section updatedSection = sectionRepository.update(section);
        logger.debug("[DEBUG] Updated section: " + updatedSection.toString());

        return updatedSection;
    }

    public void deleteById(Long id) {
        logger.info("[INFO] Deleting section with id: " + id);

        sectionRepository.findById(id).orElseThrow(() -> {
            logger.warn("[WARN] Section not found with id: " + id);
            return new NotFoundException("Section", "id", id);
        });

        sectionRepository.deleteById(id);
        logger.debug("[DEBUG] Deleted section with id: " + id);
    }

    public List<Section> findBySchoolId(Long id) {
        logger.info("[INFO] Fetching sections by school id: " + id);

        schoolRepository.findById(id).orElseThrow(() -> {
            logger.warn("[WARN] School not found with id: " + id);
            return new NotFoundException("School", "id", id);
        });

        List<Section> sections = sectionRepository.findBySchoolId(id);
        logger.debug("[DEBUG] Fetched sections by school id: " + id);

        return sections;
    }
}
