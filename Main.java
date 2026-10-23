import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // ==========================================
    // 1. INNER INTERFACE
    // ==========================================
    interface Rentable {
        double calculateRentalCost(int days);
        void markAsRented();
        void markAsReturned();
    }

    // ==========================================
    // 2. ABSTRACT INNER CLASS: Vehicle
    // ==========================================
    abstract static class Vehicle implements Rentable {
        private int vehicleId;
        private String brand;
        private String model;
        private double rentalRate;
        private boolean available;

        public Vehicle(int vehicleId, String brand, String model, double rentalRate) {
            this.vehicleId = vehicleId;
            this.brand = brand;
            this.model = model;
            this.rentalRate = rentalRate;
            this.available = true;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        public double getRentalRate() {
            return rentalRate;
        }

        public boolean isAvailable() {
            return available;
        }

        public abstract String getCategory();

        public abstract double calculateRentalCost(int days);

        @Override
        public void markAsRented() {
            if (!available) {
                throw new IllegalStateException("Vehicle is already rented / not available.");
            }
            this.available = false;
        }

        @Override
        public void markAsReturned() {
            if (available) {
                throw new IllegalStateException("Vehicle is not currently rented.");
            }
            this.available = true;
        }

        public void displayDetails() {
            System.out.println("ID: " + vehicleId 
                    + " | Brand: " + brand 
                    + " | Model: " + model
                    + " | Rate/Day: Rs. " + rentalRate 
                    + " | Available: " + (available ? "Yes" : "No"));
        }
    }

    // ==========================================
    // 3. CONCRETE CHILD CLASS: TwoWheeler
    // ==========================================
    static class TwoWheeler extends Vehicle {

        public TwoWheeler(int vehicleId, String brand, String model, double rentalRate) {
            super(vehicleId, brand, model, rentalRate);
        }

        @Override
        public String getCategory() {
            return "Two-Wheeler";
        }

        @Override
        public double calculateRentalCost(int days) {
            return getRentalRate() * days;
        }

        @Override
        public void displayDetails() {
            super.displayDetails();
            System.out.println("  Category: " + getCategory());
        }
    }

    // ==========================================
    // 4. CONCRETE CHILD CLASS: FourWheeler
    // ==========================================
    static class FourWheeler extends Vehicle {

        public FourWheeler(int vehicleId, String brand, String model, double rentalRate) {
            super(vehicleId, brand, model, rentalRate);
        }

        @Override
        public String getCategory() {
            return "Four-Wheeler";
        }

        @Override
        public double calculateRentalCost(int days) {
            return getRentalRate() * days;
        }

        @Override
        public void displayDetails() {
            super.displayDetails();
            System.out.println("  Category: " + getCategory());
        }
    }

    // ==========================================
    // 5. INNER CLASS: Customer
    // ==========================================
    static class Customer {
        private int customerId;
        private String name;
        private String contactNumber;

        public Customer(int customerId, String name, String contactNumber) {
            this.customerId = customerId;
            this.name = name;
            this.contactNumber = contactNumber;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getName() {
            return name;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void displayDetails() {
            System.out.println("Customer ID: " + customerId 
                    + " | Name: " + name 
                    + " | Contact: " + contactNumber);
        }
    }

    // ==========================================
    // 6. INNER CLASS: Rental
    // ==========================================
    static class Rental {
        private int customerId;
        private Vehicle vehicle;
        private int days;
        private double totalCost;
        private String status;

        public Rental(int customerId, Vehicle vehicle, int days) {
            this.customerId = customerId;
            this.vehicle = vehicle;
            this.days = days;
            this.totalCost = vehicle.calculateRentalCost(days);
            this.status = "Active";
        }

        public int getCustomerId() {
            return customerId;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public int getDays() {
            return days;
        }

        public double getTotalCost() {
            return totalCost;
        }

        public String getStatus() {
            return status;
        }

        public void markReturned() {
            this.status = "Returned";
        }

        public void displayDetails() {
            System.out.println("Customer ID: " + customerId 
                    + " | Vehicle: [" + vehicle.getBrand() + " " + vehicle.getModel() 
                    + " (ID: " + vehicle.getVehicleId() + ", " + vehicle.getCategory() + ")]"
                    + " | Days: " + days 
                    + " | Total Cost: Rs. " + totalCost 
                    + " | Status: " + status);
        }
    }

    // ==========================================
    // STATIC FIELDS
    // ==========================================
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Rental> rentals = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Customer loggedInCustomer = null;

    // ==========================================
    // DEMO DATA INITIALIZATION
    // ==========================================
    private static void loadDemoVehicles() {
        vehicles.add(new TwoWheeler(101, "Honda", "Activa 6G", 350.0));
        vehicles.add(new TwoWheeler(102, "Royal Enfield", "Classic 350", 800.0));
        vehicles.add(new TwoWheeler(103, "Yamaha", "R15 V4", 650.0));
        vehicles.add(new FourWheeler(201, "Maruti Suzuki", "Swift", 1200.0));
        vehicles.add(new FourWheeler(202, "Hyundai", "Creta", 2200.0));
        vehicles.add(new FourWheeler(203, "Mahindra", "Thar", 2800.0));
    }

    // ==========================================
    // HELPER INPUT METHOD
    // ==========================================
    public static int readInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return Integer.parseInt(input);
    }

    // ==========================================
    // MAIN ENTRY POINT
    // ==========================================
    public static void main(String[] args) {
        loadDemoVehicles();

        while (true) {
            System.out.println("\n=================================");
            System.out.println("    FLEETEASE RENTAL SYSTEM      ");
            System.out.println("=================================");
            System.out.println("1. User / Customer Menu");
            System.out.println("2. Admin Dashboard");
            System.out.println("3. Exit");

            try {
                int choice = readInt("Enter your choice (1-3): ");

                switch (choice) {
                    case 1:
                        customerMenu();
                        break;

                    case 2:
                        if (adminLogin()) {
                            adminMenu();
                        }
                        break;

                    case 3:
                        System.out.println("Thank you for using FleetEase. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Please select 1-3.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric choice.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // USER / CUSTOMER MENU (SIMPLIFIED & INTUITIVE)
    // ==========================================
    public static void customerMenu() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("            USER MENU            ");
            System.out.println("=================================");
            System.out.println("1. See Available Vehicles");
            System.out.println("2. Rent a Vehicle");
            System.out.println("3. Return a Vehicle");
            System.out.println("4. Back to Main Menu");

            try {
                int choice = readInt("Enter your choice (1-4): ");

                switch (choice) {
                    case 1:
                        viewAvailableVehicles();
                        break;

                    case 2:
                        rentVehicle();
                        break;

                    case 3:
                        returnVehicle();
                        break;

                    case 4:
                        return;

                    default:
                        System.out.println("Invalid choice. Please select 1-4.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Non-numeric input provided. Please enter a valid number.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // VEHICLE RENTAL & RETURN OPERATIONS
    // ==========================================
    public static void rentVehicle() {
        viewAvailableVehicles();

        int id = readInt("\nEnter Vehicle ID to Rent: ");
        Vehicle vehicle = findVehicleById(id);

        if (!vehicle.isAvailable()) {
            throw new IllegalStateException("Vehicle is already rented / not available.");
        }

        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        System.out.print("Enter Contact Number (10 digits): ");
        String contact = scanner.nextLine().trim();
        if (!contact.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be exactly 10 digits.");
        }

        // Reuse existing customer or register new one automatically
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getContactNumber().equals(contact)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            int newCustomerId = customers.size() + 1;
            customer = new Customer(newCustomerId, name, contact);
            customers.add(customer);
        }
        loggedInCustomer = customer;

        int days = readInt("Enter Number of Rental Days: ");
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be greater than 0.");
        }

        double cost = vehicle.calculateRentalCost(days);
        System.out.println("\n--- Rental Summary ---");
        System.out.println("Vehicle: " + vehicle.getBrand() + " " + vehicle.getModel() + " (" + vehicle.getCategory() + ")");
        System.out.println("Customer: " + customer.getName() + " (ID: " + customer.getCustomerId() + ")");
        System.out.println("Rental Duration: " + days + " day(s)");
        System.out.println("Total Cost: Rs. " + cost);

        System.out.print("\nDo you confirm this rental? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            vehicle.markAsRented();
            rentals.add(new Rental(customer.getCustomerId(), vehicle, days));
            System.out.println("Rental confirmed successfully! Happy Journey!");
        } else {
            System.out.println("Rental cancelled.");
        }
    }

    public static void returnVehicle() {
        boolean hasActive = false;
        System.out.println("\n--- Currently Rented Vehicles ---");

        for (Rental rental : rentals) {
            if (rental.getStatus().equals("Active")) {
                rental.displayDetails();
                hasActive = true;
            }
        }

        if (!hasActive) {
            System.out.println("No active rentals found to return.");
            return;
        }

        int id = readInt("\nEnter Vehicle ID to Return: ");
        Rental targetRental = null;

        for (Rental rental : rentals) {
            if (rental.getVehicle().getVehicleId() == id && rental.getStatus().equals("Active")) {
                targetRental = rental;
                break;
            }
        }

        if (targetRental == null) {
            throw new IllegalStateException("No active rental found for Vehicle ID " + id + ".");
        }

        System.out.println("\n--- Returning Vehicle ---");
        targetRental.displayDetails();
        System.out.println("Total Amount Settled: Rs. " + targetRental.getTotalCost());

        targetRental.getVehicle().markAsReturned();
        targetRental.markReturned();
        System.out.println("Vehicle returned successfully!");
    }

    // ==========================================
    // VEHICLE QUERIES
    // ==========================================
    public static void viewAvailableVehicles() {
        boolean found = false;
        System.out.println("\n--- Available Vehicles ---");

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                vehicle.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available vehicles currently.");
        }
    }

    public static void displayVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in the system.");
            return;
        }

        System.out.println("\n--- All Vehicles ---");
        for (Vehicle vehicle : vehicles) {
            vehicle.displayDetails();
        }
    }

    public static void displayVehicles(String category) {
        boolean found = false;
        System.out.println("\n--- Vehicles in Category: " + category + " ---");

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getCategory().equalsIgnoreCase(category)) {
                vehicle.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No vehicles found in category: " + category);
        }
    }

    public static Vehicle findVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == id) {
                return vehicle;
            }
        }
        throw new IllegalArgumentException("Vehicle with ID " + id + " not found.");
    }

    public static void viewMyRentals() {
        if (loggedInCustomer == null) {
            System.out.println("No active customer session.");
            return;
        }

        boolean found = false;
        System.out.println("\n--- My Rentals (Customer ID: " + loggedInCustomer.getCustomerId() + ") ---");

        for (Rental rental : rentals) {
            if (rental.getCustomerId() == loggedInCustomer.getCustomerId()) {
                rental.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("You have no rental records.");
        }
    }

    public static void searchByCategory() {
        int catChoice = readInt("Select Category (1. Two-Wheeler, 2. Four-Wheeler): ");
        String category;

        if (catChoice == 1) {
            category = "Two-Wheeler";
        } else if (catChoice == 2) {
            category = "Four-Wheeler";
        } else {
            throw new IllegalArgumentException("Invalid category choice. Choose 1 or 2.");
        }

        System.out.println("\n--- Available " + category + "s ---");
        boolean found = false;

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() && vehicle.getCategory().equalsIgnoreCase(category)) {
                vehicle.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available vehicles found in category: " + category);
        }
    }

    public static void recommendVehicles() {
        System.out.print("Enter maximum daily budget: ");
        double budget = Double.parseDouble(scanner.nextLine().trim());
        if (budget <= 0) {
            throw new IllegalArgumentException("Budget must be greater than 0.");
        }

        int catChoice = readInt("Select Preferred Category (1. Two-Wheeler, 2. Four-Wheeler): ");
        String category;

        if (catChoice == 1) {
            category = "Two-Wheeler";
        } else if (catChoice == 2) {
            category = "Four-Wheeler";
        } else {
            throw new IllegalArgumentException("Invalid category choice. Choose 1 or 2.");
        }

        boolean found = false;
        System.out.println("\n--- Recommended Vehicles ---");

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() 
                    && vehicle.getCategory().equalsIgnoreCase(category) 
                    && vehicle.getRentalRate() <= budget) {
                vehicle.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No vehicles match your budget and category.");
        }
    }

    // ==========================================
    // CUSTOMER MANAGEMENT
    // ==========================================
    public static void registerCustomer() {
        int id = readInt("Enter Customer ID: ");
        if (id <= 0) {
            throw new IllegalArgumentException("Customer ID must be a positive number.");
        }

        for (Customer customer : customers) {
            if (customer.getCustomerId() == id) {
                throw new IllegalArgumentException("Customer ID " + id + " is already registered.");
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        System.out.print("Enter Contact Number (10 digits): ");
        String contact = scanner.nextLine().trim();
        if (!contact.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be exactly 10 digits.");
        }

        customers.add(new Customer(id, name, contact));
        System.out.println("Customer registered successfully with ID: " + id);
    }

    public static void loginCustomer() {
        int id = readInt("Enter Customer ID: ");

        for (Customer customer : customers) {
            if (customer.getCustomerId() == id) {
                loggedInCustomer = customer;
                System.out.println("Login successful. Welcome, " + customer.getName() + "!");
                return;
            }
        }

        throw new IllegalArgumentException("Customer ID " + id + " not found. Please register first.");
    }

    // ==========================================
    // ADMIN OPERATIONS
    // ==========================================
    public static boolean adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine().trim();

        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Admin login successful!");
            return true;
        }

        System.out.println("Invalid admin credentials.");
        return false;
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("         ADMIN DASHBOARD         ");
            System.out.println("=================================");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. View Available Vehicles");
            System.out.println("4. View All Registered Customers");
            System.out.println("5. View All Rental Records");
            System.out.println("6. Logout");

            try {
                int choice = readInt("Enter your choice (1-6): ");

                switch (choice) {
                    case 1:
                        addVehicle();
                        break;

                    case 2:
                        displayVehicles();
                        break;

                    case 3:
                        viewAvailableVehicles();
                        break;

                    case 4:
                        viewCustomers();
                        break;

                    case 5:
                        viewRentals();
                        break;

                    case 6:
                        System.out.println("Admin logged out.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please select 1-6.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Non-numeric input provided. Please enter a valid number.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void addVehicle() {
        int id = readInt("Enter Vehicle ID: ");
        if (id <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be a positive number.");
        }

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == id) {
                throw new IllegalArgumentException("Vehicle with ID " + id + " already exists.");
            }
        }

        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine().trim();
        if (brand.isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty.");
        }

        System.out.print("Enter Model: ");
        String model = scanner.nextLine().trim();
        if (model.isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty.");
        }

        System.out.print("Enter Rental Rate per Day: ");
        double rate = Double.parseDouble(scanner.nextLine().trim());
        if (rate <= 0) {
            throw new IllegalArgumentException("Rental rate must be greater than 0.");
        }

        int type = readInt("Enter Category (1. Two-Wheeler, 2. Four-Wheeler): ");
        if (type == 1) {
            vehicles.add(new TwoWheeler(id, brand, model, rate));
            System.out.println("Two-Wheeler added successfully.");
        } else if (type == 2) {
            vehicles.add(new FourWheeler(id, brand, model, rate));
            System.out.println("Four-Wheeler added successfully.");
        } else {
            throw new IllegalArgumentException("Invalid category choice. Vehicle not added.");
        }
    }

    public static void viewCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No registered customers found.");
            return;
        }

        System.out.println("\n--- Registered Customers ---");
        for (Customer customer : customers) {
            customer.displayDetails();
        }
    }

    public static void viewRentals() {
        if (rentals.isEmpty()) {
            System.out.println("No rental records found.");
            return;
        }

        System.out.println("\n--- All Rental Records ---");
        for (Rental rental : rentals) {
            rental.displayDetails();
        }
    }
}
