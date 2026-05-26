
public class Suv extends Car {

    private boolean hasAllWheelDrive;
    private int     cargoSpaceCubicFt;
    private boolean hasTowPackage;

    public Suv(String make, String model, int year,
               String fuelType, double dailyRentalRate,
               int mileage, String lastMaintenanceDate,
               int maintenanceIntervalMiles, double insurancePricePerDay,
               double mpg, boolean hasAllWheelDrive,
               int cargoSpaceCubicFt, boolean hasTowPackage) {

        super(make, model, year, fuelType, dailyRentalRate,
              mileage, lastMaintenanceDate,
              maintenanceIntervalMiles, insurancePricePerDay,
              7, mpg, 4);

        this.hasAllWheelDrive    = hasAllWheelDrive;
        this.cargoSpaceCubicFt   = cargoSpaceCubicFt;
        this.hasTowPackage       = hasTowPackage;
    }

    @Override
    public String getVehicleType() { return "SUV"; }

    @Override
    public double calculateTotalCost(int days) {
        double base = super.calculateTotalCost(days);
        if (hasTowPackage) {
            base += 5.00 * days;   // $5/day tow-package fee
        }
        return base;
    }

    public boolean hasAllWheelDrive()    { return hasAllWheelDrive; }
    public int     getCargoSpaceCubicFt(){ return cargoSpaceCubicFt; }
    public boolean hasTowPackage()       { return hasTowPackage; }

    @Override
    public String toString() {
        return super.toString()
            + " | AWD: " + (hasAllWheelDrive ? "Yes" : "No")
            + " | Cargo: " + cargoSpaceCubicFt + " cu ft"
            + " | Tow Package: " + (hasTowPackage ? "Yes (+$5/day)" : "No");
    }
}
