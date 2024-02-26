package com.example.app;

import com.example.model.Item;
import com.example.model.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private List<Loan> loans; // Supongamos que Loan es una clase que representa un préstamo

    public LoanService() {
        this.loans = new ArrayList<>();
    }

    public String loanItemToUser(Long itemId, Long userId) {
        // Aquí iría la lógica para prestar un ítem al usuario
        // Supongamos que se realiza alguna operación y se agrega un préstamo a la lista de préstamos
        loans.add(new Loan(itemId, userId));
        return "Item prestado correctamente";
    }

    public String returnItem(Long itemId) {
        // Aquí iría la lógica para devolver un ítem
        // Supongamos que se realiza alguna operación para devolver el ítem y se elimina el préstamo de la lista
        loans.removeIf(loan -> loan.getItem().equals(itemId));
        return "Item devuelto correctamente";
    }

    public List<Item> getItemsLoanedToUser(Long userId) {
        // Aquí iría la lógica para obtener los ítems prestados a un usuario específico
        // Supongamos que se recorre la lista de préstamos y se obtienen los ítems prestados al usuario
        List<Item> items = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUser().equals(userId)) {
                items.add(new Item(loan.getItem())); // Suponiendo que Item es una clase que representa un ítem
            }
        }
        return items;
    }

    public List<Item> getAllItemsOnLoan() {
        // Aquí iría la lógica para obtener todos los ítems prestados
        // Supongamos que se recorre la lista de préstamos y se obtienen todos los ítems prestados
        List<Item> items = new ArrayList<>();
        for (Loan loan : loans) {
            items.add(new Item(loan.getItem())); // Suponiendo que Item es una clase que representa un ítem
        }
        return items;
    }

    public List<Loan> getAllLoansFromItem(Long itemId) {
        // Aquí iría la lógica para obtener todos los préstamos de un ítem específico
        // Supongamos que se recorre la lista de préstamos y se obtienen todos los préstamos del ítem
        List<Loan> itemLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getItem().equals(itemId)) {
                itemLoans.add(loan);
            }
        }
        return itemLoans;
    }
}

