package com.example.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date checkout_date;

    private Date due_date;
    private Date returned_date;

    @ManyToOne //Muchos prestamos para un item
    @JoinColumn(name="item_id")
    private Item item;
    @ManyToOne //Muchos prestamos para un usuario
    @JoinColumn(name="user_id")
    private User user;

//CONSTRUCTORES

    //CONTRUCTOR VACIO

    public Loan() {
    }

    //CONTRUCTOR CON TODO

    public Loan(Long id, Date checkout_date, Date due_date, Date returned_date, Item item, User user) {
        this.id = id;
        this.checkout_date = checkout_date;
        this.due_date = due_date;
        this.returned_date = returned_date;
        this.item = item;
        this.user = user;
    }

    public Loan(Long itemId, Long userId) {
    }

//GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Date checkout_date) {
        this.checkout_date = checkout_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getReturned_date() {
        return returned_date;
    }

    public void setReturned_date(Date returned_date) {
        this.returned_date = returned_date;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


//TO STRING


    @Override
    public String toString() {
        return "Loan{" +
                "checkout_date=" + checkout_date +
                ", due_date=" + due_date +
                ", returned_date=" + returned_date +
                ", item=" + item +
                ", user=" + user +
                '}';
    }
}
