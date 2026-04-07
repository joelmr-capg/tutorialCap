package com.cssw.tutorial.author;

import com.cssw.tutorial.author.model.Author;
import com.cssw.tutorial.author.model.AuthorDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {
    Author get(Long id);

    Page<Author> findPage(AuthorSearchDto dto);

    void save(Long id, AuthorDTO dto);

    void delete(Long id) throws Exception;

    List<Author> findAll();

}
