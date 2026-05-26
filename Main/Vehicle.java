
public abstract class Vehicle {

    private String make;
    private String model;
    private int    year;
    private String fuelType;       
    private double dailyRentalRate;   
    private boolean available;
    private int     mileage;          
    private String  lastMaintenanceDate; 
    private int     maintenanceIntervalMiles; 
    private double  insurancePricePerDay;

    public Vehicle(String make, String model, int year,
                   String fuelType, double dailyRentalRate,
                   int mileage, String lastMaintenanceDate,
                   int maintenanceIntervalMiles, double insurancePricePerDay) {
        this.make                     = make;
        this.model                    = model;
        this.year                     = year;
        this.fuelType                 = fuelType;
        this.dailyRentalRate          = dailyRentalRate;
        this.available                = true;   
        this.mileage                  = mileage;
        this.lastMaintenanceDate      = lastMaintenanceDate;
        this.maintenanceIntervalMiles = maintenanceIntervalMiles;
        this.insurancePricePerDay     = insurancePricePerDay;
    }

    public abstract String getVehicleType();


    public double calculateTotalCost(int days) {
        return (dailyRentalRate + insurancePricePerDay) * days;
    }

    public boolean rent() {
        if (available) {
            available = false;
            return true;
        }
        return false;
    }


    public void returnVehicle() {
        available = true;
    }


    public boolean isMaintenanceDue() {
        return mileage >= maintenanceIntervalMiles;
    }


    public String getMaintenanceStatus() {
        if (isMaintenanceDue()) {
            return "⚠  Maintenance DUE  (last: " + lastMaintenanceDate
                    + ", current mileage: " + mileage + " mi)";
        }
        int milesUntilDue = maintenanceIntervalMiles - mileage;
        return "✓  Next maintenance in " + milesUntilDue
                + " mi  (last: " + lastMaintenanceDate + ")";
    }


    public String getMake()                 { return make; }
    public String getModel()                { return model; }
    public int    getYear()                 { return year; }
    public String getFuelType()             { return fuelType; }
    public double getDailyRentalRate()      { return dailyRentalRate; }
    public boolean isAvailable()            { return available; }
    public int    getMileage()              { return mileage; }
    public String getLastMaintenanceDate()  { return lastMaintenanceDate; }
    public int    getMaintenanceIntervalMiles() { return maintenanceIntervalMiles; }
    public double getInsurancePricePerDay() { return insurancePricePerDay; }

    public void setDailyRentalRate(double rate)          { dailyRentalRate = rate; }
    public void setAvailable(boolean available)           { this.available = available; }
    public void setMileage(int mileage)                   { this.mileage = mileage; }
    public void setLastMaintenanceDate(String date)       { lastMaintenanceDate = date; }
    public void setInsurancePricePerDay(double price)     { insurancePricePerDay = price; }


    @Override
    public String toString() {
        return String.format(
            "[%s] %d %s %s | Fuel: %s | Rate: $%.2f/day | Insurance: $%.2f/day | %s | %s",
            getVehicleType(), year, make, model, fuelType,
            dailyRentalRate, insurancePricePerDay,
            available ? "AVAILABLE" : "RENTED",
            getMaintenanceStatus()
        );
    }
}
