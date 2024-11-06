package com.dbms.dbms_project_backend.repository;

import com.dbms.dbms_project_backend.model.Section;
import java.util.List;
import java.util.Optional;

public interface SectionRepository {
    public Section save(Section sections);

    public List<Section> findAll();

    public Optional<Section> findById(Long id);

    public void deleteById(Long id);

    public Section update(Section sections);

    public List<Section> findBySchoolId(Long id);

    public boolean existsByUniqueFields(Section section);

    public Section findByUniqueFields(Section section);
}
