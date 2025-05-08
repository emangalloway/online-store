package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split("\\|");
                String sku = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                Product Inventory = new Product(sku,productName,price);
                inventory.add(Inventory);
            }
            bufferedReader.close();

        }catch (Exception e){
            System.out.println("An error has occurred");
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("cart.csv", true));
            boolean found = false;
            //Display Inventory
            for (Product product : inventory) {
                System.out.println(product);
            }
            System.out.println(" ");
            System.out.println("Choose Item to add to cart? (Enter ID)");
            String sku = scanner.nextLine();

            //Search through inventory to find product by sku and add to cart.csv file

            for (Product product : inventory) {
                if (product.getSku().equalsIgnoreCase(sku)){
                    cart.add(product);//adding item to cart
                    bufferedWriter.write(product.toString());
                    bufferedWriter.close();
                    System.out.println("Successfully added to cart!");
                    found = true;
                    break;
                }
                if (found){
                    System.out.println("Product not found");
                }
            }
        }catch (Exception e){
            System.out.println("An error has occurred");
        }

    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {

            //Display Items in cart
            System.out.println("Items in your cart: ");
            for (Product product : cart) {
                System.out.println(product);
            }

            //Ask if they would like to remove items
            System.out.println(" ");
            System.out.println("Would you like to remove items (Enter ID or press Enter to skip)");
            String input = scanner.nextLine();

            //If statement to remove item from cart
            if (!input.isBlank()) {
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getSku().equalsIgnoreCase(input)) {
                        cart.remove(i);
                        System.out.println("Item successfully removed ");
                        break;
                    }
                }
            }else {
                System.out.println("Proceeding to checkout, No items removed");
                checkOut(cart,totalAmount);
            }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        Scanner scanner = new Scanner(System.in);
        for (Product product : cart) {
            System.out.println("Items in cart: "+product);
            totalAmount += product.getProductPrice();
            System.out.println("Amount due: $"+totalAmount);
        }
        System.out.println("Enter Amount to cover cost");
        String input = scanner.nextLine();
        double coverCostInput = Double.parseDouble(input);
        //System.out.println(totalAmount - coverCostInput);
        if (coverCostInput > totalAmount){
            double change = coverCostInput - totalAmount;
            System.out.printf("Successful payment, Your change is: '$%.2f\n'",change);
        } else if (coverCostInput == totalAmount) {
            System.out.println("Successful payment");
        }else{
            double remainingAmountDue = totalAmount-coverCostInput;
            System.out.printf("You still owe: $ $%.2f\n",remainingAmountDue);
        }

    }

    /**
     * I am having a hard time implementing this method in my display products method.
     * I coded that method with an integrated search by ID feature first without realizing that the purpose of the findProductById method would do the same thing.
     * @param id
     * @param inventory
     * @return
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        boolean found = true;
        for (Product product : inventory) {
            if (product.getSku().equalsIgnoreCase(id)){
                return product;
            }
            if (!found){
                System.out.println("Product not found=");
            }
        }return null;
        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding com.pluralsight.Product object. If
        // no product with the specified ID is found, the method should return
        // null.
    }
}

