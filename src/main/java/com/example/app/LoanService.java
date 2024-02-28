package com.example.app;

import com.example.HibernateUtil;
import com.example.model.Item;
import com.example.model.Loan;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class LoanService {

    public String loanItemToUser(Long itemId, Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Obtener el ítem de la base de datos
            Item item = session.get(Item.class, itemId);

            if (item == null) {
                transaction.rollback(); // Deshacer la transacción si el ítem no se encuentra
                return "Item no encontrado";
            }

            // Crear un nuevo préstamo
            Loan newLoan = new Loan(item.getId(), userId);

            // Guardar el préstamo en la base de datos
            session.save(newLoan);
            transaction.commit();

            return "Item prestado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fallo al prestar el ítem";
        }
    }

    public String returnItem(Long itemId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Obtener el préstamo correspondiente al ítem de la base de datos
            Loan loan = session.createQuery("FROM Loan WHERE item_id = :itemId", Loan.class)
                    .setParameter("itemId", itemId)
                    .uniqueResult();

            if (loan == null) {
                transaction.rollback(); // Deshacer la transacción si el préstamo no se encuentra
                return "Préstamo no encontrado";
            }

            // Eliminar el préstamo de la base de datos
            session.delete(loan);
            transaction.commit();

            return "Item devuelto correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fallo al devolver el ítem";
        }
    }

    public List<Item> getItemsLoanedToUser(Long userId) {
        List<Item> items = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta para obtener los ítems prestados al usuario
            List<Loan> userLoans = session.createQuery("FROM Loan WHERE user_id = :userId", Loan.class)
                    .setParameter("userId", userId)
                    .getResultList();

            // Recorrer los préstamos y obtener los ítems correspondientes
            for (Loan loan : userLoans) {
                items.add(loan.getItem());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<Item> getAllItemsOnLoan() {
        List<Item> items = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta para obtener todos los préstamos
            List<Loan> allLoans = session.createQuery("FROM Loan", Loan.class).getResultList();

            // Recorrer los préstamos y obtener los ítems correspondientes
            for (Loan loan : allLoans) {
                items.add(loan.getItem());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }


    public List<Loan> getAllLoansFromItem(Long itemId) {
        List<Loan> itemLoans = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta para obtener todos los préstamos relacionados con el ítem
            List<Loan> loans = session.createQuery("FROM Loan WHERE item_id = :itemId", Loan.class)
                    .setParameter("itemId", itemId)
                    .getResultList();

            itemLoans.addAll(loans);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemLoans;
    }
}

