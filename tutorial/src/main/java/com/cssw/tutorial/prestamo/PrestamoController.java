package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.prestamo.model.Prestamo;
import com.cssw.tutorial.prestamo.model.PrestamoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Prestamo", description = "Api prestamo")
@RequestMapping(value = "/prestamo")
@RestController
@CrossOrigin(origins = "*")
public class PrestamoController {
    @Autowired
    PrestamoServiceImpl prestamoService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "Metodo que devuelve la lista filtrada de prestamos")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<PrestamoDTO> find(@RequestParam(value = "titulo", required = false) String titulo, @RequestParam(value = "idClient", required = false) Long idCliente, @RequestParam(value = "fecha", required = false) Date fecha) {
        List<Prestamo> prestamos = prestamoService.find(titulo, idCliente, fecha);
        return prestamos.stream().map(e -> mapper.map(e, PrestamoDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or update", description = "Metodo que guarda o actualiza prestamos")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody PrestamoDTO dto) {
        prestamoService.save(id, dto);
    }

    @Operation(summary = "Find page", description = "Crea lista de prestamos")
    @RequestMapping(path = " ", method = RequestMethod.POST)
    public Page<PrestamoDTO> findPage(@RequestBody PrestamoSearchDTO dto) {
        Page<Prestamo> page = this.prestamoService.findPage(dto);
        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, PrestamoDTO.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());

    }

    /*@Operation(summary = "FindInicial", description = "Metodo qeu devuelve lista de prestamos")
    @RequestMapping(path = " ", method = RequestMethod.GET)
    public List<PrestamoDTO> findAll() {
        List<Prestamo> prestamos = this.prestamoService.findAll();
        return prestamos.stream().map(e -> mapper.map(e, PrestamoDTO.class)).collect(Collectors.toList());

    }*/
}
