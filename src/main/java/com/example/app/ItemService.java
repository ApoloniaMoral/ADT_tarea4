package com.example.app;
import com.example.HibernateUtil;
import com.example.model.Item;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ItemService {

        private Session session;

        public ItemService() {
            this.session = HibernateUtil.getSessionFactory().openSession();
        }

        public String reassignItemToBox(Long itemId, Long boxId) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                // Realizar la actualización del ID de la caja del ítem en la base de datos
                Query query = session.createQuery("UPDATE Item SET boxId = :boxId WHERE id = :itemId");

                query.setParameter("boxId", boxId);
                query.setParameter("itemId", itemId);
                int result = query.executeUpdate();

                transaction.commit();

                if (result > 0) {
                    return "Item reubicado correctamente";
                } else {
                    return "Fallo al reubicar el item";
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return "Fallo al reubicar el item";
            }

        }

        public List<Item> listAllItems() {
            // Suponiendo que tienes mapeada la entidad Item con Hibernate
            return session.createQuery("FROM Item", Item.class).getResultList();
        }

    }

