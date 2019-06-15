package com.example.carrental.Model;

public class Cars {

    private String carName,carImageName,carMan,carAC_Status,carSeats,carMileage,carRentalPrice;

    public Cars(String carName, String carImageName, String carMan, String carAC_Status, String carSeats, String carMileage, String carRentalPrice) {
        this.carName = carName;
        this.carImageName = carImageName;
        this.carMan = carMan;
        this.carAC_Status = carAC_Status;
        this.carSeats = carSeats;
        this.carMileage = carMileage;
        this.carRentalPrice = carRentalPrice;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarImageName() {
        return carImageName;
    }

    public void setCarImageName(String carImageName) {
        this.carImageName = carImageName;
    }

    public String getCarMan() {
        return carMan;
    }

    public void setCarMan(String carMan) {
        this.carMan = carMan;
    }

    public String getCarAC_Status() {
        return carAC_Status;
    }

    public void setCarAC_Status(String carAC_Status) {
        this.carAC_Status = carAC_Status;
    }

    public String getCarSeats() {
        return carSeats;
    }

    public void setCarSeats(String carSeats) {
        this.carSeats = carSeats;
    }

    public String getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(String carMileage) {
        this.carMileage = carMileage;
    }

    public String getCarRentalPrice() {
        return carRentalPrice;
    }

    public void setCarRentalPrice(String carRentalPrice) {
        this.carRentalPrice = carRentalPrice;
    }
}
