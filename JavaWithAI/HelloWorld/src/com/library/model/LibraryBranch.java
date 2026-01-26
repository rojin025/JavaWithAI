package com.library.model;

import java.time.LocalTime;

public class LibraryBranch {
    private String name;
    private String address;
    private String phone;
    private String email;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int capacity;
    private int currentVisitors;

    public LibraryBranch(String name, String address, String phone, String email, LocalTime openTime, LocalTime closeTime, int capacity, int currentVisitors) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.capacity = capacity;
        this.currentVisitors = currentVisitors;
    }
    
    public String getName() {
        return name;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public String getAddress() {
        return address;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {  
        this.name = name;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) { 
        this.email = email;
    }

    //Method to chech if branch is open based on the current time and open and close times
    public boolean isOpen() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(openTime) && currentTime.isBefore(closeTime);
    }

    //Method to get the capacity and current visitors
    public int getCapacity() {
        return capacity;
    }

    public int getCurrentVisitors() {
        return currentVisitors;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrentVisitors(int currentVisitors) {
        this.currentVisitors = currentVisitors;
    }   

    @Override
    public String toString() {
        return "LibraryBranch{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", capacity='" + capacity + '\'' +
                ", currentVisitors='" + currentVisitors + '\'' +
                '}';
    }
}