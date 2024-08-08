class Car implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int year;
    private String make;
    private String model;
    private int vin;

    public Car(int year, String make, String model, int vin) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.vin = vin;
    }

    public int getYear() { return year; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getVin() { return vin; }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " (VIN: " + vin + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return year == car.year && vin == car.vin && make.equals(car.make) && model.equals(car.model);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(year, make, model, vin);
    }
}