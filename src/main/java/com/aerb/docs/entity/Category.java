package com.aerb.docs.entity;
import java.util.List;

public class Category {

    private Long id;
    private String name;
    private List<Category> children;

    public Category(Long id, String name, List<Category> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getChildren() {
        return children;
    }
}

