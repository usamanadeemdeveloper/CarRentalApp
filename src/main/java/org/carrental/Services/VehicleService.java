package org.carrental.Services;

import org.carrental.Repository.VehicleDao;
import org.carrental.Model.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleService {
    private final VehicleDao dao = new VehicleDao();
    public String[][] searchByName(String name){
        List<Vehicle> vehicleList = dao.getByName(name);
        return transformToJTable(vehicleList,7);
    }
    public String[][] getAllVehiclesForJTable(){
        List<Vehicle> vehicleList = dao.getAll();
        return transformToJTable(vehicleList,7);
    }
    public String[][] getAllAvailableCarsJTable(){
        List<Vehicle> vehicleList = dao.getAllAvailableCars();
        return transformToJTable(vehicleList,7);
    }
    public String[][] getAllBookedTotalCarsForJTable(Date startDate, Date endDate){
        List<Vehicle> vehicleList = dao.getAllTotalCarsBooking(startDate,endDate);
        return transformToJTable(vehicleList,7);
    }
    private String[][] transformToJTable(List<Vehicle> vehicleList,int columnSize){
        String[][] data = new String[vehicleList.size()][columnSize];
        for (int i = 0; i < vehicleList.size(); i++){
            data[i][0] = vehicleList.get(i).getId().toString();
            data[i][1] = vehicleList.get(i).getVehicleName();
            data[i][2] = vehicleList.get(i).getModel().toString();
            data[i][3] = vehicleList.get(i).getBrand();
            data[i][4] = vehicleList.get(i).getColor();
            data[i][5] = vehicleList.get(i).getOwnerId();
            data[i][6] = vehicleList.get(i).getBookingStatus();
        }
        return data;
    }

    public List<String> getMostBookedCars() {
        List<Vehicle> vehicleList = dao.getMostBookedCars();
        List<String> carNames = new ArrayList<>();

        for (Vehicle vehicle : vehicleList) {
            String carName = vehicle.getId().toString() + " : " + vehicle.getVehicleName() +  " , " + " Total Bookings " + " : " + vehicle.getTotalBookings();
            carNames.add(carName);
        }

        return carNames;
    }

    public List<String> getLowestBookedCars() {
        List<Vehicle> vehicleList = dao.getLowestBookedCars();
        List<String> carNames = new ArrayList<>();

        for (Vehicle vehicle : vehicleList) {
            String carName = vehicle.getId().toString() + " : " + vehicle.getVehicleName() + " , " + " Total Bookings " + " : " + vehicle.getTotalBookings();
            carNames.add(carName);
        }

        return carNames;
    }

    public List<String> getHighestProfitGivenCars() {
        List<Vehicle> vehicleList = dao.getHighestProfitGivenCar();
        List<String> carNames = new ArrayList<>();

        for (Vehicle vehicle : vehicleList) {
            String carName = vehicle.getId().toString() + " : " + vehicle.getVehicleName() + " , "+ " Amount " + " : " + vehicle.getProfit();
            carNames.add(carName);
        }

        return carNames;
    }

    public void save(String name, Integer model, String brand, String color, String  dropDownOwner) {
        Vehicle vehicle = Vehicle.builder()
                .vehicleName(name)
                .model(model)
                .brand(brand)
                .color(color)
                .ownerId(dropDownOwner)
                .build();
        dao.insert(vehicle);
    }

}
