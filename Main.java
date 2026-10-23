import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // --- Inner Interface ---
    interface Rentable {
        double calculateRentalCost(int days);
        void markAsRented();
        void markAsReturned();
    }

    // --- Abstract Inner Class ---
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

        public int getVehicleId() { return vehicleId; }
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public double getRentalRate() { return rentalRate; }
        public boolean isAvailable() { return available; }
        public abstract String getCategory();
        public abstract double calculateRentalCost(int days);

        @Override
        public void markAsRented() {
            if (!available) throw new IllegalStateException("Vehicle is already rented / not available.");
            this.available = false;
        }

        @Override
        public void markAsReturned() {
            if (available) throw new IllegalStateException("Vehicle is not currently rented.");
            this.available = true;
        }

        public void displayDetails() {
            System.out.println("ID: " + vehicleId + " | Brand: " + brand + " | Model: " + model
                    + " | Rate/Day: " + rentalRate + " | Available: " + (available ? "Yes" : "No"));
        }
    }

    // --- Concrete Inner Classes Extending Vehicle ---
    static class TwoWheeler extends Vehicle {
        public TwoWheeler(int vehicleId, String brand, String model, double rentalRate) {
            super(vehicleId, brand, model, rentalRate);
        }
        @Override public String getCategory() { return "Two-Wheeler"; }
        @Override public double calculateRentalCost(int days) { return getRentalRate() * days; }
        @Override public void displayDetails() {
            super.displayDetails();
            System.out.println("  Category: " + getCategory());
        }
    }

    static class FourWheeler extends Vehicle {
        public FourWheeler(int vehicleId, String brand, String model, double rentalRate) {
            super(vehicleId, brand, model, rentalRate);
        }
        @Override public String getCategory() { return "Four-Wheeler"; }
        @Override public double calculateRentalCost(int days) { return getRentalRate() * days; }
        @Override public void displayDetails() {
            super.displayDetails();
            System.out.println("  Category: " + getCategory());
        }
    }

    // --- Inner Class: Customer ---
    static class Customer {
        private int customerId;
        private String name;
        private String contactNumber;

        public Customer(int customerId, String name, String contactNumber) {
            this.customerId = customerId;
            this.name = name;
            this.contactNumber = contactNumber;
        }
        public int getCustomerId() { return customerId; }
        public String getName() { return name; }
        public String getContactNumber() { return contactNumber; }
        public void displayDetails() {
            System.out.println("Customer ID: " + customerId + " | Name: " + name + " | Contact: " + contactNumber);
        }
    }

    // --- Inner Class: Rental ---
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
        public int getCustomerId() { return customerId; }
        public Vehicle getVehicle() { return vehicle; }
        public int getDays() { return days; }
        public double getTotalCost() { return totalCost; }
        public String getStatus() { return status; }
        public void markReturned() { this.status = "Returned"; }
        public void displayDetails() {
            System.out.println("Customer ID: " + customerId + " | Vehicle: [" + vehicle.getBrand() + " " + vehicle.getModel()
                    + " (ID: " + vehicle.getVehicleId() + ", " + vehicle.getCategory() + ")] | Days: " + days
                    + " | Total Cost: " + totalCost + " | Status: " + status);
        }
    }

    // --- Static Fields ---
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Rental> rentals = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Customer loggedInCustomer = null;

    // --- Helper Method for Input ---
    public static int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    // --- Main Entry Point ---
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== FLEETEASE RENTAL SYSTEM ===");
            System.out.println("1. Admin Login\n2. Customer (Login/Register)\n3. Exit");
            try {
                int choice = readInt("Enter your choice (1-3): ");
                if (choice == 1) {
                    if (adminLogin()) adminMenu();
                } else if (choice == 2) {
                    customerMenu();
                } else if (choice == 3) {
                    System.out.println("Thank you for using FleetEase. Goodbye!");
                    return;
                } else {
                    System.out.println("Invalid choice. Please select 1-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric choice.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // --- Admin Operations ---
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
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. Add Vehicle\n2. View All Vehicles\n3. View Available Vehicles\n4. View All Registered Customers\n5. View All Rental Records\n6. Logout");
            try {
                int choice = readInt("Enter your choice (1-6): ");
                switch (choice) {
                    case 1: addVehicle(); break;
                    case 2: displayVehicles(); break;
                    case 3: viewAvailableVehicles(); break;
                    case 4: viewCustomers(); break;
                    case 5: viewRentals(); break;
                    case 6: System.out.println("Admin logged out."); return;
                    default: System.out.println("Invalid choice. Please select 1-6.");
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
        if (id <= 0) throw new IllegalArgumentException("Vehicle ID must be a positive number.");
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == id) throw new IllegalArgumentException("Vehicle with ID " + id + " already exists.");
        }
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine().trim();
        if (brand.isEmpty()) throw new IllegalArgumentException("Brand cannot be empty.");
        System.out.print("Enter Model: ");
        String model = scanner.nextLine().trim();
        if (model.isEmpty()) throw new IllegalArgumentException("Model cannot be empty.");
        System.out.print("Enter Rental Rate per Day: ");
        double rate = Double.parseDouble(scanner.nextLine().trim());
        if (rate <= 0) throw new IllegalArgumentException("Rental rate must be greater than 0.");

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
        if (customers.isEmpty()) { System.out.println("No registered customers found."); return; }
        System.out.println("\n--- Registered Customers ---");
        for (Customer c : customers) c.displayDetails();
    }

    public static void viewRentals() {
        if (rentals.isEmpty()) { System.out.println("No rental records found."); return; }
        System.out.println("\n--- All Rental Records ---");
        for (Rental r : rentals) r.displayDetails();
    }

    // --- Vehicle Query Methods (Overloaded) ---
    public static void displayVehicles() {
        if (vehicles.isEmpty()) { System.out.println("No vehicles found in the system."); return; }
        System.out.println("\n--- All Vehicles ---");
        for (Vehicle v : vehicles) v.displayDetails();
    }

    public static void displayVehicles(String category) {
        boolean found = false;
        System.out.println("\n--- Vehicles in Category: " + category + " ---");
        for (Vehicle v : vehicles) {
            if (v.getCategory().equalsIgnoreCase(category)) {
                v.displayDetails();
                found = true;
            }
        }
        if (!found) System.out.println("No vehicles found in category: " + category);
    }

    public static void viewAvailableVehicles() {
        boolean found = false;
        System.out.println("\n--- Available Vehicles ---");
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                v.displayDetails();
                found = true;
            }
        }
        if (!found) System.out.println("No available vehicles currently.");
    }

    public static Vehicle findVehicleById(int id) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == id) return v;
        }
        throw new IllegalArgumentException("Vehicle with ID " + id + " not found.");
    }

    // --- Customer Operations ---
    public static void registerCustomer() {
        int id = readInt("Enter Customer ID: ");
        if (id <= 0) throw new IllegalArgumentException("Customer ID must be a positive number.");
        for (Customer c : customers) {
            if (c.getCustomerId() == id) throw new IllegalArgumentException("Customer ID " + id + " is already registered.");
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        System.out.print("Enter Contact Number (10 digits): ");
        String contact = scanner.nextLine().trim();
        if (!contact.matches("\\d{10}")) throw new IllegalArgumentException("Contact number must be exactly 10 digits.");

        customers.add(new Customer(id, name, contact));
        System.out.println("Customer registered successfully with ID: " + id);
    }

    public static void loginCustomer() {
        int id = readInt("Enter Customer ID: ");
        for (Customer c : customers) {
            if (c.getCustomerId() == id) {
                loggedInCustomer = c;
                System.out.println("Login successful. Welcome, " + c.getName() + "!");
                return;
            }
        }
        throw new IllegalArgumentException("Customer ID " + id + " not found. Please register first.");
    }

    public static void searchByCategory() {
        int catChoice = readInt("Select Category (1. Two-Wheeler, 2. Four-Wheeler): ");
        String category = (catChoice == 1) ? "Two-Wheeler" : (catChoice == 2) ? "Four-Wheeler" : null;
        if (category == null) throw new IllegalArgumentException("Invalid category choice. Choose 1 or 2.");

        System.out.println("\n--- Available " + category + "s ---");
        boolean found = false;
        for (Vehicle v : vehicles) {
            if (v.isAvailable() && v.getCategory().equalsIgnoreCase(category)) {
                v.displayDetails();
                found = true;
            }
        }
        if (!found) System.out.println("No available vehicles found in category: " + category);
    }

    public static void recommendVehicles() {
        System.out.print("Enter maximum daily budget: ");
        double budget = Double.parseDouble(scanner.nextLine().trim());
        if (budget <= 0) throw new IllegalArgumentException("Budget must be greater than 0.");
        int catChoice = readInt("Select Preferred Category (1. Two-Wheeler, 2. Four-Wheeler): ");
        String category = (catChoice == 1) ? "Two-Wheeler" : (catChoice == 2) ? "Four-Wheeler" : null;
        if (category == null) throw new IllegalArgumentException("Invalid category choice. Choose 1 or 2.");

        boolean found = false;
        System.out.println("\n--- Recommended Vehicles ---");
        for (Vehicle v : vehicles) {
            if (v.isAvailable() && v.getCategory().equalsIgnoreCase(category) && v.getRentalRate() <= budget) {
                v.displayDetails();
                found = true;
            }
        }
        if (!found) System.out.println("No vehicles match your budget and category.");
    }

    public static void rentVehicle() {
        int id = readInt("Enter Vehicle ID to Rent: ");
        Vehicle v = findVehicleById(id);
        if (!v.isAvailable()) throw new IllegalStateException("Vehicle is already rented / not available.");
        int days = readInt("Enter Number of Rental Days: ");
        if (days <= 0) throw new IllegalArgumentException("Rental days must be greater than 0.");

        double cost = v.calculateRentalCost(days);
        System.out.println("Vehicle: " + v.getBrand() + " " + v.getModel() + " (" + v.getCategory() + ")");
        System.out.println("Total Rental Cost for " + days + " day(s): " + cost);
        System.out.print("Do you confirm this rental? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            v.markAsRented();
            rentals.add(new Rental(loggedInCustomer.getCustomerId(), v, days));
            System.out.println("Rental confirmed successfully!");
        } else {
            System.out.println("Rental cancelled.");
        }
    }

    public static void viewMyRentals() {
        boolean found = false;
        System.out.println("\n--- My Rentals (Customer ID: " + loggedInCustomer.getCustomerId() + ") ---");
        for (Rental r : rentals) {
            if (r.getCustomerId() == loggedInCustomer.getCustomerId()) {
                r.displayDetails();
                found = true;
            }
        }
        if (!found) System.out.println("You have no rental records.");
    }

    public static void returnVehicle() {
        boolean hasActive = false;
        System.out.println("\n--- Your Active Rentals ---");
        for (Rental r : rentals) {
            if (r.getCustomerId() == loggedInCustomer.getCustomerId() && r.getStatus().equals("Active")) {
                r.displayDetails();
                hasActive = true;
            }
        }
        if (!hasActive) {
            System.out.println("You have no active rentals to return.");
            return;
        }

        int id = readInt("Enter Vehicle ID to return: ");
        Rental targetRental = null;
        for (Rental r : rentals) {
            if (r.getCustomerId() == loggedInCustomer.getCustomerId()
                    && r.getVehicle().getVehicleId() == id
                    && r.getStatus().equals("Active")) {
                targetRental = r;
                break;
            }
        }
        if (targetRental == null) {
            throw new IllegalStateException("No active rental found for Vehicle ID " + id + " under your account.");
        }

        System.out.println("\n--- Returning Vehicle ---");
        targetRental.displayDetails();
        System.out.println("Total Cost to Settle: " + targetRental.getTotalCost());
        targetRental.getVehicle().markAsReturned();
        targetRental.markReturned();
        System.out.println("Vehicle returned successfully!");
    }

    public static void customerMenu() {
        while (true) {
            System.out.println("\n=== CUSTOMER DASHBOARD ===");
            System.out.println("Status: " + (loggedInCustomer != null
                    ? "Logged in as " + loggedInCustomer.getName() + " (ID: " + loggedInCustomer.getCustomerId() + ")"
                    : "Not logged in"));
            System.out.println("1. Register as a New Customer\n2. Login\n3. View Available Vehicles\n4. Search Vehicles by Category\n5. Get Smart Vehicle Recommendations\n6. Rent a Vehicle\n7. View My Rentals\n8. Return a Vehicle\n9. Logout");

            try {
                int choice = readInt("Enter your choice (1-9): ");
                switch (choice) {
                    case 1: registerCustomer(); break;
                    case 2: loginCustomer(); break;
                    case 3: viewAvailableVehicles(); break;
                    case 4: searchByCategory(); break;
                    case 5: recommendVehicles(); break;
                    case 6:
                        if (loggedInCustomer == null) System.out.println("Please login first to rent a vehicle.");
                        else rentVehicle();
                        break;
                    case 7:
                        if (loggedInCustomer == null) System.out.println("Please login first to view your rentals.");
                        else viewMyRentals();
                        break;
                    case 8:
                        if (loggedInCustomer == null) System.out.println("Please login first to return a vehicle.");
                        else returnVehicle();
                        break;
                    case 9:
                        loggedInCustomer = null;
                        System.out.println("Customer logged out.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 1-9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Non-numeric input provided. Please enter a valid number.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

