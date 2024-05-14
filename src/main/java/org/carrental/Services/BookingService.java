package org.carrental.Services;

import org.carrental.Repository.BookingDao;
import org.carrental.Model.Booking;
import org.carrental.Model.Customer;
import org.carrental.Model.Vehicle;

import java.util.Date;
import java.util.List;

public class BookingService {
    BookingDao dao = new BookingDao();

    public String[][] searchByStatus(String status){
        List<Booking> bookingList = dao.getByStatus(status);
        return transformToJTable(bookingList,7);
    }


    public String[] transformToDropdownCustomers(List<Customer> customerList){
        String[] data = new String[customerList.size()];
        for (int i = 0; i < customerList.size(); i++) {
            data[i] = customerList.get(i).getId().toString();
            data[i] += "," + customerList.get(i).getName();

        }
        return data;
    }

    public String[] transformToDropdownVehicles(List<Vehicle> vehicleList){
        String[] data = new String[vehicleList.size()];
        for (int i = 0; i < vehicleList.size(); i++) {
            data[i] = vehicleList.get(i).getId().toString();
            data[i] += "," + vehicleList.get(i).getVehicleName();

        }
        return data;
    }
    public String[][] getAllCustomerForJTable(){

        List<Booking> bookingList = dao.getAll();
        return transformToJTable(bookingList,7);
    }
    private String[][] transformToJTable(List<Booking> bookingList,int columnSize){
        String[][] data = new String[bookingList.size()][columnSize];
        for (int i = 0; i < bookingList.size(); i++){
            data[i][0] = bookingList.get(i).getId().toString();
            data[i][1] = bookingList.get(i).getCustomerID();
            data[i][2] = bookingList.get(i).getVehicleId();
            data[i][3] = bookingList.get(i).getDatePicker().toString();
            data[i][4] = bookingList.get(i).getPrice().toString();
            data[i][5] = bookingList.get(i).getBookingStatus();
            data[i][6] = String.valueOf(bookingList.get(i).getCompleteBooking());
        }
        return data;
    }

    public void save(String dropdownCustomer, String dropDownVehicle, Double price, Date calender) {
        Booking booking = Booking.builder()
                .customerID(dropdownCustomer)
                .vehicleId(dropDownVehicle)
                .price(price)
                .datePicker(new java.sql.Date(calender.getTime()))
                .build();
        dao.insert(booking);
    }
}
