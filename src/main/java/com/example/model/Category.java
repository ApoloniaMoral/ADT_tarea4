package com.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "categories")
    private List<Item> Items = new ArrayList<>();


//CONSTRUCTORES

    //CONTRUCTOR VACIO

    public Category() {
    }
    //CONTRUCTOR SEED
    public Category(String name) {
    }
    //CONTRUCTOR CON TODO

    public Category(Long id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        Items = items;
    }



//GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return Items;
    }

    public void setItems(List<Item> items) {
        Items = items;
    }


//TO STRING


    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", Items=" + Items +
                '}';
    }
}
