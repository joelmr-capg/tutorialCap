package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.prestamo.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long>, JpaSpecificationExecutor<Prestamo> {
    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    List<Prestamo> findAll(Specification<Prestamo> spec);

    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    Page<Prestamo> findAll(Specification<Prestamo> spec, Pageable pageable);

    @Query("""
                SELECT p FROM Prestamo p
                WHERE p.game.id = :gameId
                  AND (:id IS NULL OR p.id <> :id)
                  AND p.fechaPrestamo <= :fechaFin
                  AND p.fechaDevolucion >= :fechaInicio
            """)
    List<Prestamo> findConflictingGamePrestamos(@Param("gameId") Long gameId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("id") Long id);

    @Query("""
                SELECT COUNT(p) FROM Prestamo p
                WHERE p.client.id = :clientId
                  AND (:id IS NULL OR p.id <> :id)
                  AND p.fechaPrestamo <= :fechaFin
                  AND p.fechaDevolucion >= :fechaInicio
            """)
    Long findConflictingClientPrestamos(@Param("clientId") Long clientId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("id") Long id);

}
