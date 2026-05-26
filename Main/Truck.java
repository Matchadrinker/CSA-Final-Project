
public class Truck extends Vehicle {

    private double payloadCapacityTons;
    private String bedSize;           
    private boolean hasFourWheelDrive;
    private int    numAxles;
    private double mpg;

    public Truck(String make, String model, int year,
                 String fuelType, double dailyRentalRate,
                 int mileage, String lastMaintenanceDate,
                 int maintenanceIntervalMiles, double insurancePricePerDay,
                 double payloadCapacityTons, String bedSize,
                 boolean hasFourWheelDrive, int numAxles, double mpg) {

        super(make, model, year, fuelType, dailyRentalRate,
              mileage, lastMaintenanceDate,
              maintenanceIntervalMiles, insurancePricePerDay);

        this.payloadCapacityTons = payloadCapacityTons;
        this.bedSize             = bedSize;
        this.hasFourWheelDrive   = hasFourWheelDrive;
        this.numAxles            = numAxles;
        this.mpg                 = mpg;
    }

    @Override
    public String getVehicleType() { return "Truck"; }

    
    @Override
    public double calculateTotalCost(int days) {
        double base = super.calculateTotalCost(days);
        if (payloadCapacityTons >= 2.0) {
            base += 10.00 * days;   // commercial-use surcharge
        }
        return base;
    }

    public double  getPayloadCapacityTons() { return payloadCapacityTons; }
    public String  getBedSize()             { return bedSize; }
    public boolean hasFourWheelDrive()      { return hasFourWheelDrive; }
    public int     getNumAxles()            { return numAxles; }
    public double  getMpg()                 { return mpg; }

    @Override
    public String toString() {
        return super.toString()
            + String.format(" | Payload: %.1f tons | Bed: %s | 4WD: %s | Axles: %d | MPG: %.1f",
                payloadCapacityTons, bedSize,
                hasFourWheelDrive ? "Yes" : "No",
                numAxles, mpg);
    }
}
