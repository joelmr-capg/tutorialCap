package com.cssw.tutorial.author;

import com.cssw.tutorial.author.model.Author;
import com.cssw.tutorial.author.model.AuthorDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {
        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, AuthorDTO data) {
        Author author;
        if (id == null) {
            author = new Author();
        } else {
            author = this.authorRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(data, author, "id");

        this.authorRepository.save(author);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.authorRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }
        this.authorRepository.deleteById(id);
    }
}
