package com.example.app;

import com.example.HibernateUtil;
import com.example.model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ItemService {

    public String reassignItemToBox(Long itemId, long boxId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Recuperar el objeto Item de la base de datos usando su ID
            Item item = session.get(Item.class, itemId);

            if (item == null) {
                transaction.rollback(); // Deshacer la transacción si el ítem no se encuentra
                return "Ítem no encontrado";
            }

            // Modificar el atributo boxId del objeto Item
            item.setBox(boxId);

            // Guardar los cambios en la base de datos
            session.update(item);
            transaction.commit();

            return "Item reubicado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Fallo al reubicar el ítem";
        }
    }


    public List<Item> listAllItems() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta para obtener todos los ítems
            return session.createQuery("FROM Item", Item.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


