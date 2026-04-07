package com.cssw.tutorial.game.model;

import com.cssw.tutorial.author.model.AuthorDTO;
import com.cssw.tutorial.category.model.CategoryDTO;

public class GameDTO {
    private Long id;

    private String title;

    private String age;

    private CategoryDTO category;

    private AuthorDTO author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
