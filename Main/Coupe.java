
public class Coupe extends Car {

    private boolean hasSunroof;
    private boolean isSportsTrim;

    public Coupe(String make, String model, int year,
                 String fuelType, double dailyRentalRate,
                 int mileage, String lastMaintenanceDate,
                 int maintenanceIntervalMiles, double insurancePricePerDay,
                 double mpg, boolean hasSunroof, boolean isSportsTrim) {

        super(make, model, year, fuelType, dailyRentalRate,
              mileage, lastMaintenanceDate,
              maintenanceIntervalMiles, insurancePricePerDay,
              4, mpg, 2);

        this.hasSunroof    = hasSunroof;
        this.isSportsTrim  = isSportsTrim;
    }

    @Override
    public String getVehicleType() { return "Coupe"; }


    @Override
    public double calculateTotalCost(int days) {
        double base = super.calculateTotalCost(days);
        if (isSportsTrim) {
            base *= 1.10;   
        }
        return base;
    }

    public boolean hasSunroof()   { return hasSunroof; }
    public boolean isSportsTrim() { return isSportsTrim; }

    @Override
    public String toString() {
        return super.toString()
            + " | Sunroof: " + (hasSunroof ? "Yes" : "No")
            + " | Sports Trim: " + (isSportsTrim ? "Yes (+10%)" : "No");
    }
}
