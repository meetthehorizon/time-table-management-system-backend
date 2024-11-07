package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.Slots;

public interface SlotsRepository {
    List<Slots> findBySectionId(Long sectionId);

    Slots save(Slots newSlot);

    Slots update(Slots newSlot);

    Optional<Slots> findById(Long id);

    void deleteById(Long id);
}
