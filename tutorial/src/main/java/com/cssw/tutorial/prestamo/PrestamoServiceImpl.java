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
        return this.prestamoRepository.findAll(dto.getPageable().getPageable());
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

        prestamo.setGame(gameService.get(dto.getGame().getId()));
        prestamo.setClient(clientService.get(dto.getClient().getId()));

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
