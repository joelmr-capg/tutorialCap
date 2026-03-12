package com.cssw.tutorial.author;

import com.cssw.tutorial.author.model.Author;
import com.cssw.tutorial.author.model.AuthorDTO;
import org.springframework.data.domain.Page;

public interface AuthorService {
    Page<Author> findPage(AuthorSearchDto dto);

    void save(Long id, AuthorDTO dto);

    void delete(Long id) throws Exception;
}
