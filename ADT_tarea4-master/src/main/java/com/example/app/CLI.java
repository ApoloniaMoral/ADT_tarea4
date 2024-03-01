package com.example.app;

import com.example.HibernateUtil;
import com.example.model.Category;
import com.example.model.Item;
import com.example.app.ItemService;
import com.example.seed;

import java.util.List;
import java.util.Scanner;

public class CLI {

    private static ItemService itemService = new ItemService();

    public static void main(String[] args) {
        seed.seed();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createItem(scanner);
                    break;
                case 2:
                    readItem(scanner);
                    break;
                case 3:
                    searchItem(scanner);
                    break;
                case 4:
                    updateItem(scanner);
                    break;
                case 5:
                    deleteItem(scanner);
                    break;
                case 6:
                    assignCategory(scanner);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
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

    private static void createItem(Scanner scanner) {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            itemService.create(name, description);

            System.out.println("Item created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readItem(Scanner scanner) {
        try {
            System.out.print("Enter id: ");
            int id = scanner.nextInt();

            Item item = itemService.get(id);

            if (item != null) {
                System.out.println(item);
            } else {
                System.out.println("Item not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchItem(Scanner scanner) {
        try {
            System.out.print("Enter name to search: ");
            String name = scanner.nextLine();

            List<Item> items = itemService.search(name);

            if (items.isEmpty()) {
                System.out.println("No items found with that name");
            } else {
                System.out.println("Found items:");
                for (Item item : items) {
                    System.out.println(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateItem(Scanner scanner) {
        try {
            System.out.print("Enter item id: ");
            int id = scanner.nextInt();

            System.out.print("Enter new name (leave empty to not change): ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter new description (leave empty to not change): ");
            String description = scanner.nextLine().trim();

            itemService.update(id, name, description);

            System.out.println("Item updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteItem(Scanner scanner) {
        try {
            System.out.print("Enter item id: ");
            int id = scanner.nextInt();

            itemService.delete(id);

            System.out.println("Item deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void assignCategory(Scanner scanner) {
        try {
            System.out.print("Enter item id: ");
            int itemId = scanner.nextInt();

            System.out.print("Enter categories (comma separated): ");
            String categoriesInput = scanner.nextLine();

            String[] categoryNames = categoriesInput.split(",");

            itemService.setCategories(itemId, categoryNames);

            System.out.println("Categories assigned successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
