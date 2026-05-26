public abstract class Car extends Vehicle {
 
    private int    numSeats;
    private double mpg;           
    private int    numDoors;
 
    public Car(String make, String model, int year,
               String fuelType, double dailyRentalRate,
               int mileage, String lastMaintenanceDate,
               int maintenanceIntervalMiles, double insurancePricePerDay,
               int numSeats, double mpg, int numDoors) {
 
        super(make, model, year, fuelType, dailyRentalRate,
              mileage, lastMaintenanceDate,
              maintenanceIntervalMiles, insurancePricePerDay);
 
        this.numSeats  = numSeats;
        this.mpg       = mpg;
        this.numDoors  = numDoors;
    }
 
    public int    getNumSeats() { return numSeats; }
    public double getMpg()      { return mpg; }
    public int    getNumDoors() { return numDoors; }
 
    @Override
    public String toString() {
        return super.toString()
            + String.format(" | Seats: %d | Doors: %d | MPG: %.1f", numSeats, numDoors, mpg);
    }
}
 
