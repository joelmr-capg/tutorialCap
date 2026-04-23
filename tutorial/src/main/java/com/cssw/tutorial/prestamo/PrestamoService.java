package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.prestamo.model.Prestamo;
import com.cssw.tutorial.prestamo.model.PrestamoDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface PrestamoService {

    Prestamo get(Long id);

    List<Prestamo> find(String titulo, Long idClient, Date fecha);

    void save(Long id, PrestamoDTO dto);

    public Page<Prestamo> findPage(PrestamoSearchDTO dto);

    public void delete(Long id) throws Exception;

    List<Prestamo> findAll();

}
