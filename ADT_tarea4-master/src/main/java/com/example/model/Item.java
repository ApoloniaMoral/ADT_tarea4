package com.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name= "box_id")
    private Box box;

    @ManyToMany
    @JoinTable(name = "category_id",
            joinColumns =@JoinColumn(name="item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Loan> Loans = new ArrayList<>();


//CONSTRUCTORES

    //CONTRUCTOR VACIO

    public Item() {
    }

    //CONTRUCTOR SEED
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //CONTRUCTOR CON TODO

    public Item(String name, String description, Box box, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.box = box;
        this.categories = categories;
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(Item item) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Loan> getLoans() {
        return Loans;
    }

    public void setLoans(List<Loan> loans) {
        Loans = loans;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}