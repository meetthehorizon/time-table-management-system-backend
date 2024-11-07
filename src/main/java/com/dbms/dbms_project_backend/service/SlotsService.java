package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Slots;
import com.dbms.dbms_project_backend.repository.SlotsRepository;

@Service
public class SlotsService {
    @Autowired
    private SlotsRepository slotsRepository;

    private static final Logger logger = LoggerFactory.getLogger(SlotsService.class);

    public List<Slots> findBySectionId(Long sectionId) {
        logger.info("Fetching slots by section id: " + sectionId);

        List<Slots> slots = slotsRepository.findBySectionId(sectionId);
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
