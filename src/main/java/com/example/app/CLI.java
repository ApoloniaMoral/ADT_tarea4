package com.example.app;

import com.example.HibernateUtil;
import com.example.model.Category;
import com.example.model.Item;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static SessionFactory factory;
    public static void main(String[] args) {

          try {
                // Crear la sesión de Hibernate
                //factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

                Scanner scanner = new Scanner(System.in);

                while (true) {
                    printMenu();
                    System.out.print("Selecciona una opción: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    switch (choice) {
                        case 1:
                            CLI.createItem(scanner);
                            break;
                        case 2:
                            CLI.readItem(scanner);
                            break;
                        case 3:
                            CLI.searchItem(scanner);
                            break;
                        case 4:
                            CLI.updateItem(scanner);
                            break;
                        case 5:
                            CLI.deleteItem(scanner);
                            break;
                        case 6:
                            CLI.assignCategory(scanner);
                            break;
                        case 7:
                            System.out.println("Saliendo...");
                            return;
                        default:
                            System.out.println("Opción no válida.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Cerrar la sesión de Hibernate
                //if (factory != null) {
                 //   factory.close();
                }

        }


    private static void printMenu() {
            System.out.println("\n1. Registrar un item.");
            System.out.println("2. Buscar por id.");
            System.out.println("3. Buscar por nombre.");
            System.out.println("4. Actualizar item.");
            System.out.println("5. Borrar item.");
            System.out.println("6. Asignar categoría.");
            System.out.println("7. Salir");
    }




    public static void createItem(Scanner scanner) {
        //CREAR ITEMS
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            System.out.print("Introduce el nombre del item: ");
            String name = scanner.nextLine();
            System.out.print("Introduce la descripción del item: ");
            String description = scanner.nextLine();

            Item item = new Item(name, description);
            session.persist(item);

            transaction.commit();
            System.out.println("Item creado correctamente.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        session.close();
    }
    private static void readItem(Scanner scanner) {
        Item item = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.print("Introduce el id del item a buscar: ");
            Integer id = scanner.nextInt();

            // Obtener el Item por su ID
            item = session.get(Item.class, id);

            // Hacer commit de la transacción
            transaction.commit();

            // Imprimir la representación de cadena del Item
            if (item != null) {
                System.out.println("Item encontrado:");
                System.out.println(item.toString());
            } else {
                System.out.println("No se ha encontrado el item");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    private static void searchItem(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.print("Introduce el nombre del item a buscar: ");
            String name = scanner.nextLine();

            // Realizar la consulta para buscar el Item por nombre
            Query query = session.createQuery("FROM Item WHERE name = :name");
            query.setParameter("name", name);
            List<Item> items = query.getResultList();

            // Hacer commit de la transacción
            transaction.commit();

            // Imprimir los resultados de la búsqueda
            if (!items.isEmpty()) {
                System.out.println("Items encontrados:");
                for (Item item : items) {
                    System.out.println(item.toString());
                }
            } else {
                System.out.println("No se han encontrado items con ese nombre.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void updateItem(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.print("Introduce el ID del item a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            // Obtener el Item por su ID
            Item item = session.get(Item.class, id);
            if (item != null) {
                System.out.print("Introduce el nuevo nombre del item (deja en blanco para mantener el actual): ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    item.setName(newName);
                }

                System.out.print("Introduce la nueva descripción del item (deja en blanco para mantener la actual): ");
                String newDescription = scanner.nextLine().trim();
                if (!newDescription.isEmpty()) {
                    item.setDescription(newDescription);
                }

                // Actualizar el Item en la base de datos
                session.update(item);
                transaction.commit();
                System.out.println("Item actualizado correctamente.");
            } else {
                System.out.println("No se ha encontrado el item con ese ID.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void deleteItem(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.print("Introduce el ID del item a borrar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            // Obtener el Item por su ID
            Item item = session.get(Item.class, id);
            if (item != null) {
                // Borrar el Item de la base de datos
                session.delete(item);
                transaction.commit();
                System.out.println("Item eliminado correctamente.");
            } else {
                System.out.println("No se ha encontrado el item con ese ID.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void assignCategory(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.print("Introduce el ID del item al que deseas asignar categorías: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            // Obtener el Item por su ID
            Item item = session.get(Item.class, id);
            if (item != null) {
                // Obtener las categorías existentes del item
                List<Category> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }

                // Solicitar al usuario las nuevas categorías
                System.out.print("Introduce las categorías para el item (separadas por coma): ");
                String input = scanner.nextLine().trim();
                String[] categoryNames = input.split(",");

                // Crear objetos Category para las categorías ingresadas
                for (String categoryName : categoryNames) {
                    Category category = new Category(categoryName.trim());
                    categories.add(category);
                }

                // Asignar las categorías al item
                item.setCategories(categories);

                // Actualizar el Item en la base de datos
                session.update(item);
                transaction.commit();
                System.out.println("Categorías asignadas correctamente.");
            } else {
                System.out.println("No se ha encontrado el item con ese ID.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}

