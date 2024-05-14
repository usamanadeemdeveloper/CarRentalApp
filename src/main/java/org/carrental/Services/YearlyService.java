package org.carrental.Services;

import org.carrental.Repository.YearlyReportDao;
import org.carrental.Model.Booking;

import java.util.Date;
import java.util.List;

public class YearlyService {

    private final YearlyReportDao yearlyReportDao = new YearlyReportDao();


    public String[][] getAllYearlyBookingsForJTable(Date startDate, Date endDate,String ownerName){
        List<Booking> bookingList = yearlyReportDao.getAllYearlyReports(startDate,endDate,ownerName);
        return transformToJTableForOwner(bookingList,9);
    }

    public String[][] getAllYearlyBookedVehicleForJTable(Date startDate, Date endDate,String vehicleName){
        List<Booking> bookingList = yearlyReportDao.getAllYearlyVehicleReports(startDate,endDate,vehicleName);
        return transformToJTableForVehicle(bookingList,8);
    }


    private String[][] transformToJTableForOwner(List<Booking> bookingList,int columnSize){
        String[][] data = new String[bookingList.size()][columnSize];
        for (int i = 0; i < bookingList.size(); i++){
            data[i][0] = bookingList.get(i).getId().toString();
            data[i][1] = bookingList.get(i).getOwnerName();
            data[i][2] = bookingList.get(i).getVehicleName();
            data[i][3] = bookingList.get(i).getDatePicker().toString();
            data[i][4] = bookingList.get(i).getCompleteBooking().toString();
            data[i][5] = bookingList.get(i).getPrice().toString();
            data[i][6] = bookingList.get(i).getBookingStatus();
            data[i][7] = bookingList.get(i).getTotalAmount().toString();
            data[i][8] = String.valueOf(bookingList.get(i).getCommission());
        }
        return data;
    }

    private String[][] transformToJTableForVehicle(List<Booking> bookingList,int columnSize){
        String[][] data = new String[bookingList.size()][columnSize];
        for (int i = 0; i < bookingList.size(); i++){
            data[i][0] = bookingList.get(i).getId().toString();
            data[i][1] = bookingList.get(i).getVehicleName();
            data[i][2] = bookingList.get(i).getDatePicker().toString();
            data[i][3] = String.valueOf(bookingList.get(i).getCompleteBooking());
            data[i][4] = bookingList.get(i).getPrice().toString();
            data[i][5] = bookingList.get(i).getBookingStatus();
            data[i][6] = bookingList.get(i).getTotalAmount().toString();
            data[i][7] = String.valueOf(bookingList.get(i).getCommission());
        }
        return data;
    }
}
