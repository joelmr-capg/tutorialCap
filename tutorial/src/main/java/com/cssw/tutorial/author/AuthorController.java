package com.cssw.tutorial.author;

import com.cssw.tutorial.author.model.Author;
import com.cssw.tutorial.author.model.AuthorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Author", description = "API of author")
@RequestMapping(value = "/author")
@RestController
@CrossOrigin(origins = "*")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find page", description = "Crear lista de autores")
    @RequestMapping(path = " ", method = RequestMethod.POST)
    public Page<AuthorDTO> findPage(@RequestBody AuthorSearchDto dto) {
        Page<Author> page = this.authorService.findPage(dto);

        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, AuthorDTO.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    @Operation(summary = "Save or Update", description = "Metodo que guarda y actualiza autor")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody AuthorDTO dto) {
        this.authorService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Metodo que elimina un autor")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.authorService.delete(id);
    }

    @Operation(summary = "find", description = "Metodo que devuelve lista de autores")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<AuthorDTO> findAll() {
        List<Author> authors = this.authorService.findAll();

        return authors.stream().map(e -> mapper.map(e, AuthorDTO.class)).collect(Collectors.toList());
    }
}


