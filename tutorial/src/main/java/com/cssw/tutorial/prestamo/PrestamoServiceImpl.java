package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.client.ClientService;
import com.cssw.tutorial.common.criteria.SearchCriteria;
import com.cssw.tutorial.game.GameService;
import com.cssw.tutorial.prestamo.model.Prestamo;
import com.cssw.tutorial.prestamo.model.PrestamoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    @Override
    public Prestamo get(Long id) {
        return this.prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Prestamo> findPage(PrestamoSearchDTO dto) {

        PrestamoSpecification titleSpec = new PrestamoSpecification(new SearchCriteria("game.title", ":", dto.getTitulo()));

        PrestamoSpecification clientSpec = new PrestamoSpecification(new SearchCriteria("client.id", ":", dto.getIdClient()));

        PrestamoSpecification fechaSpec = new PrestamoSpecification(new SearchCriteria("fechaPrestamo", ":", dto.getFecha()));

        Specification<Prestamo> spec = Specification.where(titleSpec).and(clientSpec).and(fechaSpec);

        return this.prestamoRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public List<Prestamo> find(String titulo, Long idClient, Date fecha) {
        PrestamoSpecification titleSpec = new PrestamoSpecification(new SearchCriteria("titulo", ":", titulo));
        PrestamoSpecification clientSpec = new PrestamoSpecification(new SearchCriteria("client.id", ":", idClient));
        PrestamoSpecification fechaSpec = new PrestamoSpecification(new SearchCriteria("fecha", ":", fecha));
        Specification<Prestamo> spec = titleSpec.and(clientSpec).and(fechaSpec);
        return this.prestamoRepository.findAll(spec);
    }

    @Override
    public void save(Long id, PrestamoDTO dto) {
        Prestamo prestamo;
        if (id == null) {
            prestamo = new Prestamo();
        } else {
            prestamo = prestamoRepository.findById(id).orElse(null);
        }
        BeanUtils.copyProperties(dto, prestamo, "id", "game", "client");

        if (prestamo.getFechaPrestamo() != null && prestamo.getFechaDevolucion() != null && prestamo.getFechaDevolucion().before(prestamo.getFechaPrestamo())) {
            throw new IllegalArgumentException("La fecha de devolucion no puede ser anterior a la fecha de prestamo");
        }

        long MILLIS_POR_DIA = 1000L * 60 * 60 * 24;
        long dias = (prestamo.getFechaDevolucion().getTime() - prestamo.getFechaPrestamo().getTime()) / MILLIS_POR_DIA;
        final int LIMIT = 14;

        if (dias > LIMIT) {
            throw new IllegalArgumentException("El periodo maximo de prestamo son 14 dias");
        }

        prestamo.setGame(gameService.get(dto.getGame().getId()));
        prestamo.setClient(clientService.get(dto.getClient().getId()));

        List<Prestamo> conflictos = prestamoRepository.findConflictingGamePrestamos(prestamo.getGame().getId(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getId());
        if (!conflictos.isEmpty()) {
            throw new IllegalArgumentException("El juego ya esta prestado");
        }

        final int MAX_JUEGOS_CLIENTE = 2;
        Long prestamosCliente = prestamoRepository.findConflictingClientPrestamos(prestamo.getClient().getId(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getId());
        if (prestamosCliente >= MAX_JUEGOS_CLIENTE) {
            throw new IllegalArgumentException("Un cliente no puede tener mas de dos juegos");
        }

        this.prestamoRepository.save(prestamo);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("No existe el prestamo que desea borrar");
        }
        this.prestamoRepository.deleteById(id);
    }

    @Override
    public List<Prestamo> findAll() {
        return (List<Prestamo>) this.prestamoRepository.findAll();
    }

}
