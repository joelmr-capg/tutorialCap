package com.cssw.tutorial.category;

import com.cssw.tutorial.category.model.Category;
import com.cssw.tutorial.category.model.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Category", description = "API of category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "Metodo que devuelve lista de categorias")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CategoryDTO> findAll() {
        List<Category> categories = this.categoryService.findAll();
        return categories.stream().map(e -> mapper.map(e, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Metodo que guarda o actualiza categorias")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDTO dto) {
        this.categoryService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Metodo que elimina categorias")
    @RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws Exception {
        this.categoryService.delete(id);
    }
}
