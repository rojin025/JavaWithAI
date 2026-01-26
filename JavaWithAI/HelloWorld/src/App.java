import java.time.LocalDate;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        //Create a variable for an employee: name, age, gender, department, salary, isActive, and hire date
        String name = "John Doe";
        int age = 30;
        String gender = "Male";
        String department = "IT";
        double salary = 50000.00;
        boolean isActive = true;
        LocalDate hireDate = LocalDate.of(2024, 1, 1);

        //Print the employee's information
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Department: " + department);
        System.out.println("Salary: " + salary);
        System.out.println("Is Active: " + isActive);
        System.out.println("Hire Date: " + hireDate);

        //Create a List of products names
        List<String> productsNames = new ArrayList<>();
        productsNames.add("Product 1");
        productsNames.add("Product 2");
        productsNames.add("Product 3");
        productsNames.add("Product 4");
        productsNames.add("Product 5");
        
        //Map a product name to a prices
        Map<String, Double> productPrices = new HashMap<>();
        productPrices.put("Product 1", 10.00);
        productPrices.put("Product 2", 20.00);
        productPrices.put("Product 3", 30.00);
        productPrices.put("Product 4", 40.00);
        productPrices.put("Product 5", 50.00);

        //Create a set of categories
        Set<String> categories = new HashSet<>();
        categories.add("Category 1");
        categories.add("Category 2");
        categories.add("Category 3");
        categories.add("Category 4");
        categories.add("Category 5");
        
        //Print the products information
        System.out.println("Products Names: " + productsNames);
        System.out.println("Product Prices: " + productPrices);
        System.out.println("Categories: " + categories);

        String userInput = "123";
        try {
            int userInputInt = Integer.parseInt(userInput);
            System.out.println("User Input: " + userInputInt);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }

        String userInput2 = "123.45";
        try {
            double userInputDouble = Double.parseDouble(userInput2);
            System.out.println("User Input: " + userInputDouble);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }

        String userInput3 = "true";
        try {
            boolean userInputBoolean = Boolean.parseBoolean(userInput3);
            System.out.println("User Input: " + userInputBoolean);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
