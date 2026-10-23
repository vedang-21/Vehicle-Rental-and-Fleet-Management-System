# FleetEase: Vehicle Rental Management System

FleetEase is a console-based Vehicle Rental Management System designed as an Object-Oriented Programming (OOP) academic project in Java. Built using a clean single-file architecture, FleetEase demonstrates fundamental and advanced OOP concepts through a practical, real-world fleet and rental management use case.

---

## 📌 Features

### 1. Preloaded Demo Vehicles
The application automatically starts with ready-to-rent demo vehicles:
- **Two-Wheelers**:
  - `ID 101`: Honda Activa 6G (Rs. 350/day)
  - `ID 102`: Royal Enfield Classic 350 (Rs. 800/day)
  - `ID 103`: Yamaha R15 V4 (Rs. 650/day)
- **Four-Wheelers**:
  - `ID 201`: Maruti Suzuki Swift (Rs. 1200/day)
  - `ID 202`: Hyundai Creta (Rs. 2200/day)
  - `ID 203`: Mahindra Thar (Rs. 2800/day)

### 2. User / Customer Menu (Streamlined & Frictionless)
The user menu is designed to be direct and simple:
1. **See Available Vehicles**: Instantly browse all vehicles currently available for rent.
2. **Rent a Vehicle**: Shows available vehicles, prompts for vehicle ID, your name, 10-digit contact number, and rental days. Automatically calculates the cost and confirms the booking without requiring a separate registration ceremony.
3. **Return a Vehicle**: View currently active rentals, select the vehicle ID to return, settle the total cost, and immediately restore the vehicle's availability.
4. **Back to Main Menu**: Return to the main menu.

### 3. Admin Dashboard
Accessible via predefined credentials (`admin` / `admin123`):
- **Add Vehicles**: Register new Two-Wheelers and Four-Wheelers with custom rates and IDs.
- **View All Vehicles**: Inspect full fleet status (available and rented).
- **View Available Vehicles**: Filter and inspect only vehicles ready for rent.
- **View Customers**: Monitor all registered customer accounts.
- **View Rental Records**: Full audit log of all active and completed rentals.

---

## 🧬 OOP Concepts Demonstrated

| Concept | Implementation in FleetEase |
|---|---|
| **Classes & Objects** | Static inner classes `Vehicle`, `TwoWheeler`, `FourWheeler`, `Customer`, and `Rental` instantiated at runtime and managed in `ArrayList` collections. |
| **Encapsulation** | Private member variables across all models accessed and mutated through public getters and state methods (`markAsRented()`, `markAsReturned()`, `markReturned()`). |
| **Inheritance** | `TwoWheeler` and `FourWheeler` inherit common attributes and behaviors from the abstract class `Vehicle`. |
| **Abstraction** | `Vehicle` is declared `abstract` with abstract methods `getCategory()` and `calculateRentalCost(int days)`. |
| **Interfaces** | `Rentable` defines core contract methods (`calculateRentalCost`, `markAsRented`, `markAsReturned`) implemented by `Vehicle`. |
| **Compile-Time Polymorphism** | Method overloading with `displayVehicles()` and `displayVehicles(String category)`. |
| **Runtime Polymorphism** | Storing child objects in an `ArrayList<Vehicle>` and invoking overridden methods (`calculateRentalCost`, `displayDetails`, `getCategory`) resolved dynamically at runtime. |
| **Keywords** | Idiomatic use of `this`, `super`, and `@Override`. |
| **Exception Handling** | Built-in standard exceptions (`NumberFormatException`, `IllegalArgumentException`, `IllegalStateException`) thrown during validation and handled cleanly by menu loops without crashing. |

---

## 📁 Project Structure

```text
Vehicle-Rental-and-Fleet-Management-System/
├── .gitignore
├── Main.java     # Single-file architecture containing all classes & interface
└── README.md     # Documentation and guide
```

---

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK 8 or later).

### Compilation & Execution

1. **Navigate to the repository directory**:
   ```bash
   cd "Vehicle-Rental-and-Fleet-Management-System"
   ```

2. **Compile**:
   ```bash
   javac Main.java
   ```

3. **Run**:
   ```bash
   java Main
   ```

---

## 🔑 Default Credentials

- **Admin Username**: `admin`
- **Admin Password**: `admin123`
