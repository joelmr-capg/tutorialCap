package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.prestamo.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long>, JpaSpecificationExecutor<Prestamo> {
    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    List<Prestamo> findAll(Specification<Prestamo> spec);

    Page<Prestamo> findAll(Pageable pageable);
}
