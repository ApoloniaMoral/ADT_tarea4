package com.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Loan> Loans = new ArrayList<>();

//CONSTRUCTORES

    //CONTRUCTOR VACIO

    public User() {
    }

    //CONTRUCTOR SEED
    public User(String name) {
        this.name = name;
    }
    //CONTRUCTOR CON TODO

    public User(Long id, String name, List<Loan> loans) {
        this.id = id;
        this.name = name;
        Loans = loans;
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

    public List<Loan> getLoans() {
        return Loans;
    }

    public void setLoans(List<Loan> loans) {
        Loans = loans;
    }


//TO STRING

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
