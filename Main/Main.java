import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RentalSystem rs = new RentalSystem();
        seedFleet(rs);          

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllVehicles(rs);
                    break;
                case "2":
                    viewAvailable(rs);
                    break;
                case "3":
                    searchMenu(rs, sc);
                    break;
                case "4":
                    rentMenu(rs, sc);
                    break;
                case "5":
                    returnMenu(rs, sc);
                    break;
                case "6":
                    viewRates(rs);
                    break;
                case "7":
                    maintenanceMenu(rs, sc);
                    break;
                case "8":
                    insuranceMenu(rs, sc);
                    break;
                case "0":
                    System.out.println("\nThank you for using QuickRent. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("  ✗ Invalid option. Please try again.");
            }
        }
        sc.close();
    }


    private static void printMainMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      QuickRent — Vehicle Rental      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. View all vehicles                ║");
        System.out.println("║  2. View available vehicles          ║");
        System.out.println("║  3. Search vehicles                  ║");
        System.out.println("║  4. Rent a vehicle                   ║");
        System.out.println("║  5. Return a vehicle                 ║");
        System.out.println("║  6. View rental rates & cost         ║");
        System.out.println("║  7. Maintenance schedule             ║");
        System.out.println("║  8. Insurance prices                 ║");
        System.out.println("║  0. Exit                             ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("  Choice: ");
    }

    private static void viewAllVehicles(RentalSystem rs) {
        System.out.println("\n── All Vehicles in Fleet ──");
        RentalSystem.printVehicleList(rs.getAllVehicles());
    }

    private static void viewAvailable(RentalSystem rs) {
        System.out.println("\n── Available Vehicles ──");
        RentalSystem.printVehicleList(rs.getAvailableVehicles());
    }

    private static void searchMenu(RentalSystem rs, Scanner sc) {
        System.out.println("\n── Search Vehicles ──");
        System.out.println("  Vehicle types: Coupe, Sedan, SUV, Motorcycle, Truck");
        System.out.print("  Type (or press Enter to skip): ");
        String type = sc.nextLine().trim();

        System.out.println("  Fuel types: Gas, Electric, Hybrid");
        System.out.print("  Fuel type (or press Enter to skip): ");
        String fuel = sc.nextLine().trim();

        System.out.print("  Max daily rate $ (or press Enter to skip): ");
        String rateStr = sc.nextLine().trim();
        double maxRate = rateStr.isEmpty() ? Double.MAX_VALUE : parseDouble(rateStr);

        ArrayList<Vehicle> results = rs.search(type, fuel, maxRate);
        System.out.println("\n  Results (" + results.size() + " vehicle(s) found):");
        RentalSystem.printVehicleList(results);
    }

    private static void rentMenu(RentalSystem rs, Scanner sc) {
        ArrayList<Vehicle> avail = rs.getAvailableVehicles();
        System.out.println("\n── Rent a Vehicle ──");
        System.out.println("  Available vehicles:");
        RentalSystem.printVehicleList(avail);
        if (avail.isEmpty()) return;

        System.out.print("  Enter vehicle number: ");
        int idx = parseInt(sc.nextLine().trim()) - 1;
        if (idx < 0 || idx >= avail.size()) {
            System.out.println("  ✗ Invalid selection.");
            return;
        }

        System.out.print("  Enter number of rental days: ");
        int days = parseInt(sc.nextLine().trim());
        if (days <= 0) { System.out.println("  ✗ Days must be > 0."); return; }

        Vehicle v = avail.get(idx);
        boolean success = rs.rentVehicle(v);
        if (success) {
            double total = v.calculateTotalCost(days);
            System.out.printf("  ✓ Rented: %d %s %s for %d day(s).%n",
                v.getYear(), v.getMake(), v.getModel(), days);
            System.out.printf("  Estimated total: $%.2f (rate + insurance)%n", total);
            if (v instanceof Motorcycle) {
                System.out.println("  ⚠  A valid motorcycle license is required.");
            }
        } else {
            System.out.println("  ✗ Vehicle is not available.");
        }
    }

    private static void returnMenu(RentalSystem rs, Scanner sc) {
        ArrayList<Vehicle> rented = new ArrayList<Vehicle>();
        for (Vehicle v : rs.getAllVehicles()) {
            if (!v.isAvailable()) rented.add(v);
        }
        System.out.println("\n── Return a Vehicle ──");
        System.out.println("  Currently rented vehicles:");
        RentalSystem.printVehicleList(rented);
        if (rented.isEmpty()) return;

        System.out.print("  Enter vehicle number: ");
        int idx = parseInt(sc.nextLine().trim()) - 1;
        if (idx < 0 || idx >= rented.size()) {
            System.out.println("  ✗ Invalid selection.");
            return;
        }
        Vehicle v = rented.get(idx);
        rs.returnVehicle(v);
        System.out.printf("  ✓ Returned: %d %s %s%n",
            v.getYear(), v.getMake(), v.getModel());
    }

    private static void viewRates(RentalSystem rs) {
        System.out.println("\n── Rental Rates & Cost Estimator ──");
        ArrayList<Vehicle> all = rs.getAllVehicles();
        System.out.printf("  %-4s %-12s %-20s %-10s %-12s %-12s%n",
            "#", "Type", "Vehicle", "Fuel", "Rate/day", "Ins./day");
        System.out.println("  " + "─".repeat(74));
        for (int i = 0; i < all.size(); i++) {
            Vehicle v = all.get(i);
            String name = v.getYear() + " " + v.getMake() + " " + v.getModel();
            System.out.printf("  %-4d %-12s %-20s %-10s $%-11.2f $%-11.2f%n",
                i + 1, v.getVehicleType(), name, v.getFuelType(),
                v.getDailyRentalRate(), v.getInsurancePricePerDay());
        }
    }

    private static void maintenanceMenu(RentalSystem rs, Scanner sc) {
        System.out.println("\n── Maintenance Schedule ──");
        System.out.println("  1. Show all maintenance statuses");
        System.out.println("  2. Show only vehicles needing maintenance");
        System.out.print("  Choice: ");
        String c = sc.nextLine().trim();

        if (c.equals("1")) {
            for (Vehicle v : rs.getAllVehicles()) {
                System.out.printf("  %d %s %s — %s%n",
                    v.getYear(), v.getMake(), v.getModel(),
                    v.getMaintenanceStatus());
            }
        } else if (c.equals("2")) {
            ArrayList<Vehicle> due = rs.getMaintenanceDueVehicles();
            if (due.isEmpty()) {
                System.out.println("  ✓ No vehicles currently need maintenance.");
            } else {
                System.out.println("  Vehicles requiring maintenance:");
                for (Vehicle v : due) {
                    System.out.printf("  • %d %s %s%n",
                        v.getYear(), v.getMake(), v.getModel());
                }
            }
        }
    }

    private static void insuranceMenu(RentalSystem rs, Scanner sc) {
        System.out.println("\n── Insurance Prices ──");
        ArrayList<Vehicle> all = rs.getAllVehicles();
        System.out.printf("  %-4s %-20s %-12s %-16s %-16s%n",
            "#", "Vehicle", "Type", "Ins./day", "3-day est.");
        System.out.println("  " + "─".repeat(70));
        for (int i = 0; i < all.size(); i++) {
            Vehicle v = all.get(i);
            String name = v.getYear() + " " + v.getMake() + " " + v.getModel();
            System.out.printf("  %-4d %-20s %-12s $%-15.2f $%-15.2f%n",
                i + 1, name, v.getVehicleType(),
                v.getInsurancePricePerDay(),
                v.calculateTotalCost(3));
        }
    }

    private static void seedFleet(RentalSystem rs) {
        rs.addVehicle(new Sedan("Toyota",  "Camry",   2023, "Gas",      45.00,
            12000, "2024-01-15", 15000, 8.00, 32.0, true,  "5-star"));
        rs.addVehicle(new Sedan("Honda",   "Accord",  2022, "Hybrid",   50.00,
            8500,  "2024-03-10", 15000, 9.00, 44.0, false, "5-star"));
        rs.addVehicle(new Sedan("Tesla",   "Model 3", 2024, "Electric", 65.00,
            3000,  "2024-06-01", 20000, 10.00, 134.0, true, "5-star"));

        // Coupes
        rs.addVehicle(new Coupe("Ford",    "Mustang", 2023, "Gas",      70.00,
            5000, "2024-02-20", 10000, 12.00, 24.0, true,  true));
        rs.addVehicle(new Coupe("Chevrolet","Camaro", 2022, "Gas",      68.00,
            9000, "2023-11-05", 10000, 12.00, 22.0, false, true));

        // SUVs
        rs.addVehicle(new Suv("Ford",      "Explorer",   2023, "Gas",   60.00,
            14000, "2024-01-30", 12000, 11.00, 26.0, false, 87, false));
        rs.addVehicle(new Suv("Toyota",    "4Runner",    2023, "Gas",   75.00,
            7000,  "2024-04-12", 12000, 13.00, 17.0, true,  89, true));
        rs.addVehicle(new Suv("Tesla",     "Model Y",    2024, "Electric", 80.00,
            1500,  "2024-07-01", 25000, 12.00, 121.0, true, 76, false));

        rs.addVehicle(new Motorcycle("Harley-Davidson", "Sportster", 2022,
            "Gas", 55.00, 6000, "2024-03-01", 8000, 15.00,
            "Cruiser", 1200, false, 50.0));
        rs.addVehicle(new Motorcycle("Honda", "CBR600RR", 2023,
            "Gas", 60.00, 4000, "2024-05-15", 8000, 18.00,
            "Sport", 600, false, 42.0));
        rs.addVehicle(new Motorcycle("Ural",  "Gear Up",  2021,
            "Gas", 65.00, 8000, "2023-12-20", 8000, 20.00,
            "Cruiser", 750, true, 30.0));

        rs.addVehicle(new Truck("Ford",    "F-150",       2023, "Gas",   55.00,
            11000, "2024-02-14", 10000, 10.00, 1.0, "Standard", true,  2, 20.0));
        rs.addVehicle(new Truck("Ram",     "1500",        2022, "Hybrid",60.00,
            9500,  "2024-01-22", 10000, 10.00, 1.0, "Short",    false, 2, 24.0));
        rs.addVehicle(new Truck("Ford",    "F-350 Super", 2023, "Gas",   90.00,
            16000, "2024-05-10", 8000,  15.00, 3.0, "Long",     true,  4, 14.0));
    }


    private static int parseInt(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return 0; }
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return 0.0; }
    }
}